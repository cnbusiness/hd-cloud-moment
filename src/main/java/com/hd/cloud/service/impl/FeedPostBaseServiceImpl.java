package com.hd.cloud.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.FeedCommentBo;
import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.FeedPostRes;
import com.hd.cloud.bo.FeedTimelineInfo;
import com.hd.cloud.bo.ForwardBo;
import com.hd.cloud.bo.FriendBo;
import com.hd.cloud.bo.MomentCount;
import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.dao.FeedPostResDao;
import com.hd.cloud.dao.FeedTimelineInfoDao;
import com.hd.cloud.dao.TopicSectionDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.model.SimplePushModel;
import com.hd.cloud.service.FeedCommentService;
import com.hd.cloud.service.FeedPostBaseService;
import com.hd.cloud.service.PushConsumerService;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.RedisKeyUtils;
import com.hd.cloud.vo.AddMomentVo;
import com.hd.cloud.vo.FansDynamicVo;
import com.hd.cloud.vo.FeedCommentVo;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.StarUserListVo;
import com.hd.cloud.vo.TopicSectionPostListVo;
import com.hd.cloud.vo.UserProfileVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CommonConstantUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: FeedPostBaseServiceImpl
 * @Description: 动态管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午2:38:30
 *
 */
@Slf4j
@Service
public class FeedPostBaseServiceImpl implements FeedPostBaseService {

	@Inject
	private FeedPostBaseDao feedPostBaseDao;

	@Inject
	private FeedPostBaseCache feedPostBaseCache;

	@Autowired
	private AccountClient accountClient;

	@Inject
	private FeedPostResDao feedPostResDao;

	@Inject
	private FeedPostInfoDao feedPostInfoDao;

	@Inject
	private FeedCommentService feedCommentService;

	@Inject
	private TopicSectionDao topicSectionDao;

	@Inject
	private FeedTimelineInfoDao feedTimelineInfoDao;

	@Inject
	private PushConsumerService pushConsumerService;

	@Override
	public FeedPostBase getFeedPostBaseById(int id) {
		// 浏览数+1
		feedPostBaseCache.updateMomentCountByHashKey(id, RedisKeyUtils.VIEWCOUNT, 1);
		FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(id);
		// 获取最新的点赞数 评论 浏览数
		MomentCount momentCount = feedPostBaseCache.getMomentCount(id);
		feedPostBase.setMomentCount(momentCount);
		log.info("#######momentCount:{}", momentCount);
		if (momentCount != null) {
			feedPostBase.setStarCnt(momentCount.getStarCount());
			feedPostBase.setViewCount(momentCount.getViewCount());
			feedPostBase.setForwardCount(momentCount.getForwardCount());
			feedPostBase.setFavoriteCnt(momentCount.getFavoritesCnt());
		}
		if (feedPostBase.getTopicId() > 0) {
			// 更新话题浏览量
			feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.VIEWCOUNT, 1);
		}
		// 获取用户信息
		UserProfile user = accountClient.getUserProfileByUserId(feedPostBase.getUserId());
		feedPostBase.setUser(user);
		// 动态图片
		List<FeedPostRes> postResourceBo = feedPostResDao.getFeedPostResListByPostId(id);
		feedPostBase.setPostResourceBo(postResourceBo);
		return feedPostBase;
	}

	@Override
	public int update(FeedPostBase feedPostBase) {
		int result = feedPostBaseDao.update(feedPostBase);
		if (result > 0) {
			// 清空点赞
			feedPostBaseCache.deleteStar(feedPostBase.getId());
			// 清空缓存
			feedPostBaseCache.deleteMomentCount(feedPostBase.getId());
			// 删除动态消息
			feedPostInfoDao.deleteByPostId(feedPostBase.getId());

			List<FriendBo> list = accountClient.getAllFriendsByUserId(feedPostBase.getUserId());
			List<Long> userIds = Lists.newArrayList();
			// 加入自己
			userIds.add(feedPostBase.getUserId());
			for (FriendBo friendBo : list) {
				// 刪除缓存(可查看动态的)
				feedPostBaseCache.deleteUserTimeline(friendBo.getUserId(), feedPostBase.getId());
			}

			feedPostBase = feedPostBaseDao.getFeedPostBaseById(feedPostBase.getId());
			if (feedPostBase!=null && feedPostBase.getTopicId() > 0) {
				// 更新话题下动态的数量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.DYNAMICCOUNT, -1);
			}
		}
		return result;
	}

	@Override
	public List<FeedPostBaseBo> getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo) {
		List<FeedPostBaseBo> postList = Lists.newArrayList();
		List<FeedPostBaseBo> feedPostBases = feedPostBaseDao.getFeedPostBaseList(feedPostBaseVo);
		// 查看别人的动态列表
		if (feedPostBaseVo.getUserId() != feedPostBaseVo.getLoginUserId()) {
			for (FeedPostBaseBo feedPostBaseBo : feedPostBases) {
				if (feedPostBaseBo.getVisibleType() != 1) {
					String friends = feedPostBaseBo.getVisibleFriends();
					if (!StringUtils.isBlank(friends)) {
						String[] friendArray = friends.split(",");
						for (int i = 0; i < friendArray.length; i++) {
							if (Long.parseLong(friendArray[i]) == feedPostBaseVo.getLoginUserId()) {
								postList.add(feedPostBaseBo);
							}
						}
					}
				} else {
					postList.add(feedPostBaseBo);
				}
			}
		} else {
			postList = feedPostBases;
		}

		for (FeedPostBaseBo feedPostBaseBo : postList) {
			// 浏览数+1
			feedPostBaseCache.updateMomentCountByHashKey(feedPostBaseBo.getId(), RedisKeyUtils.VIEWCOUNT, 1);
			if (feedPostBaseBo.getTopicId() > 0) {
				// 更新话题浏览量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBaseBo.getTopicId(), RedisKeyUtils.VIEWCOUNT, 1);
			}
			// 如果是转发动态 处理
			if (feedPostBaseBo.getForwardPostId() > 0) {
				FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(feedPostBaseBo.getForwardPostId());
				if (feedPostBase != null) {
					ForwardBo forwardBo = ForwardBo.builder().content(feedPostBase.getContent())
							.activeFlag(feedPostBase.getActiveFlag()).build();
					UserProfile user = accountClient.getUserProfileByUserId(feedPostBase.getUserId());
					if (user != null) {
						forwardBo.setUser(user);
					}
					List<FeedPostRes> postResourceBo = feedPostResDao
							.getFeedPostResListByPostId(feedPostBaseBo.getForwardPostId());
					forwardBo.setPostResourceBo(postResourceBo);
					feedPostBaseBo.setForward(forwardBo);
				}
			}
			// 获取前三条评论
			FeedCommentVo feedCommentVo = FeedCommentVo.builder().postId(feedPostBaseBo.getId()).offset(0).pageSize(3)
					.build();
			List<FeedCommentBo> commentBos = feedCommentService.getFeedCommentlist(feedCommentVo);
			feedPostBaseBo.setCommentBos(commentBos);
			// 获取用户信息
			UserProfile user = accountClient.getUserProfileByUserId(feedPostBaseBo.getUserId());
			feedPostBaseBo.setUser(user);
			// 获取点赞数 喜欢数 评论数
			MomentCount momentCount = feedPostBaseCache.getMomentCount(feedPostBaseBo.getId());
			feedPostBaseBo.setMomentCount(momentCount);

			// 动态图片
			List<FeedPostRes> postResourceBo = feedPostResDao.getFeedPostResListByPostId(feedPostBaseBo.getId());
			feedPostBaseBo.setPostResourceBo(postResourceBo);

			// 是否喜欢
			feedPostBaseBo.setStar(feedPostBaseCache.hasStar(feedPostBaseBo.getId(), feedPostBaseVo.getUserId()));
		}
		return postList;
	}

	@Override
	public BoUtil publishMoment(AddMomentVo addMomentVo) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		if (addMomentVo.getContent().length() > 500) {
			boUtil.setCode(ErrorCode.MOMENT_LIMIT_ERROR);
			boUtil.setMsg(" The number of dynamic words cannot exceed 500");
			return boUtil;
		}
		FeedPostBase feedPostBase = FeedPostBase.builder().type(addMomentVo.getType()).topicId(addMomentVo.getTopicId())
				.content(addMomentVo.getContent()).forwardPostId(addMomentVo.getForwardPostId())
				.title(addMomentVo.getTitle()).longitude(addMomentVo.getLongitude()).latitude(addMomentVo.getLatitude())
				.area(addMomentVo.getArea()).visibleType(addMomentVo.getVisibleType()).userType(1).appType(1)
				.address(addMomentVo.getAddress()).userId(addMomentVo.getUserId()).appType(1).status(1)
				.createBy(addMomentVo.getUserId()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
		// 话题
		if (addMomentVo.getType() == ConstantUtil.TYPE_TOPIC) {
			if (addMomentVo.getTopicId() != 0) {
				TopicSection topicSection = topicSectionDao.getTopicSectionById(addMomentVo.getTopicId());
				if (topicSection != null) {
					feedPostBase.setTopicId(addMomentVo.getTopicId());
					feedPostBase.setType(ConstantUtil.TYPE_TOPIC);
				} else {
					feedPostBase.setType(ConstantUtil.TYPE_POST);
				}
			} else {
				if (StringUtils.isNotBlank(addMomentVo.getTopicName())) {
					TopicSection topicSection = topicSectionDao.getTopicSectionByName(addMomentVo.getTopicName());
					if (topicSection != null) {
						feedPostBase.setTopicId(addMomentVo.getTopicId());
						feedPostBase.setType(ConstantUtil.TYPE_TOPIC);
					} else {
						// 新增一个话题
						topicSection = TopicSection.builder().name(addMomentVo.getTopicName()).isCheck(1).isDisplay(2)
								.sourceType(1).createBy(addMomentVo.getUserId()).countryCode("CN")
								.activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
						topicSectionDao.save(topicSection);
						// 设置话题ID
						feedPostBase.setTopicId(topicSection.getId());
						feedPostBase.setType(ConstantUtil.TYPE_TOPIC);
					}
				}
			}

			// 如果是话题 更新话题动态数量
			feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.DYNAMICCOUNT, 1);
		}
		// 分享范围-好友
		if (addMomentVo.getVisibleType() == 2) {
			String visibleFriends = "";
			// 获取好友
			List<FriendBo> list = accountClient.getAllFriendsByUserId(addMomentVo.getUserId());
			List<Long> userIds = Lists.newArrayList();
			// 加入自己
			userIds.add(addMomentVo.getUserId());
			for (FriendBo friendBo : list) {
				userIds.add(friendBo.getUserId());
			}
			visibleFriends = StringUtils.join(userIds.toArray(), ",");
			feedPostBase.setVisibleFriends(visibleFriends);
		}
		// 分享范围-指定组员
		if (addMomentVo.getVisibleGroupids() != null && addMomentVo.getVisibleType() == 3) {
			if (addMomentVo.getVisibleGroupids().length > 0) {
				Long[] gids = addMomentVo.getVisibleGroupids();
				StringBuilder gidStr = new StringBuilder();
				for (int i = 0; i < gids.length; i++) {
					gidStr.append(gids[i]);
					if (i < gids.length - 1) {
						gidStr.append(",");
					}
				}
				feedPostBase.setVisibleGroups(gidStr.toString());
			}

			String visibleFriends = "";
			// 获取好友
			List<FriendBo> list = accountClient.getAllFriendsByUserId(addMomentVo.getUserId());
			List<Long> userIds = Lists.newArrayList();
			// 加入自己
			userIds.add(addMomentVo.getUserId());
			for (FriendBo friendBo : list) {
				userIds.add(friendBo.getUserId());
			}
			visibleFriends = StringUtils.join(userIds.toArray(), ",");
			feedPostBase.setVisibleFriends(visibleFriends);
		}
		// 分享范围-指定好友好友
		if (addMomentVo.getVisibleFriendids() != null && addMomentVo.getVisibleType() == 4) {
			if (addMomentVo.getVisibleFriendids().length > 0) {
				Long[] fids = addMomentVo.getVisibleFriendids();
				StringBuilder fidStr = new StringBuilder();
				for (int i = 0; i < fids.length; i++) {
					fidStr.append(fids[i]);
					if (i < fids.length - 1) {
						fidStr.append(",");
					}
				}
				fidStr.append("," + addMomentVo.getUserId());// 将自己加入到可分享范围内
				feedPostBase.setVisibleFriends(fidStr.toString());
			}
		}
		int result = feedPostBaseDao.save(feedPostBase);
		if (result > 0) {
			int type = addMomentVo.getType();
			List<Long> ids = Lists.newArrayList();
			// 1.公开，2.好友，3.指定组员，4.指定好友
			switch (type) {
			case 1:
				// 获取所有关注者的用户id
				List<FriendBo> friendList = accountClient.getAllFriendsByUserId(addMomentVo.getUserId());
				for (FriendBo friendBo : friendList) {
					ids.add(friendBo.getUserId());
				}
				break;
			case 2:
				// 获取好友
				List<FriendBo> list = accountClient.getAllFriendsByUserId(addMomentVo.getUserId());
				for (FriendBo friendBo : list) {
					ids.add(friendBo.getUserId());
				}
				break;
			case 3:
				// TODO 需要尧杰的接口
				break;
			case 4:
				ids = new ArrayList<Long>(Arrays.asList(addMomentVo.getVisibleFriendids()));
				break;
			default:
				break;
			}
			ids.add(addMomentVo.getUserId());
			log.info("######################可以看到动态的用户ids:{}", ids);
			// 设置查看范围
			FeedTimelineInfo feedTimelineInfo = FeedTimelineInfo.builder().postId(feedPostBase.getId())
					.createBy(addMomentVo.getUserId()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
			if (null != ids && !ids.isEmpty()) {
				for (Long id : ids) {
					// 写入用户时间轴
					feedPostBaseCache.addUserTimeline(id, feedPostBase.getId());
					feedTimelineInfo.setUserId(id);
					feedTimelineInfoDao.save(feedTimelineInfo);
				}
			}

			// 首图片
			String picUrl = "";
			// 如果有上传附件 就保存
			List<FeedPostRes> postRes = addMomentVo.getFeedPostRes();
			if (postRes != null && postRes.size() > 0) {
				picUrl = postRes.get(0).getPath();
				for (FeedPostRes feedPostRes : postRes) {
					feedPostRes.setCreateBy(addMomentVo.getUserId());
					feedPostRes.setActiveFlag(CommonConstantUtil.ACTIVE_FLAG_Y);
					feedPostRes.setPostId(feedPostBase.getId());
					feedPostResDao.save(feedPostRes);
				}
			}
			feedPostBase.setPicUrl(picUrl);
			feedPostBase.setUpdateBy(addMomentVo.getUserId());
			// 编辑
			feedPostBaseDao.update(feedPostBase);

			// 保存推送消息
			if (addMomentVo.getForwardPostId() > 0) {
				// 获得转发动态的作者
				FeedPostBase postForward = feedPostBaseDao.getFeedPostBaseById(addMomentVo.getForwardPostId());
				if (postForward != null && postForward.getUserId() != addMomentVo.getUserId()) {
					FeedPostInfo feedPostInfo = FeedPostInfo.builder().postId(feedPostBase.getId()).type(1)
							.infoType(ConstantUtil.OPERATION_FORWARD).fromUserId(addMomentVo.getUserId())
							.content(addMomentVo.getContent()).status(2).userId(postForward.getUserId())
							.createBy(addMomentVo.getUserId()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();

					if (!StringUtils.isBlank(postForward.getContent())) {
						if (postForward.getContent().length() > 50) {
							feedPostInfo.setShortText(postForward.getContent().substring(0, 50));
						} else {
							feedPostInfo.setShortText(postForward.getContent());
						}
					}
					String indexPicture = feedPostResDao.getIndexPicture(addMomentVo.getForwardPostId());
					feedPostInfo.setAvatar(indexPicture);
					feedPostInfoDao.save(feedPostInfo);

					// 转发推送消息
					UserProfile userProfile = accountClient.getUserProfileByUserId(addMomentVo.getUserId());
					// 评论推送消息
					String content = userProfile.getNickName() + "转发了你的动态";
					SimplePushModel simplePushModel = SimplePushModel.builder().fromUserId(addMomentVo.getUserId())
							.toUserId(feedPostInfo.getUserId()).toAppType(1).isRemind(false).content(content).build();
					pushConsumerService.simplePush(simplePushModel);
				}
			}

			boUtil = BoUtil.getDefaultTrueBo();
			boUtil.setMsg("publish moment success!");
			return boUtil;
		} else {
			boUtil.setMsg("publish moment fail!");
			return boUtil;
		}

	}

	@Override
	public BoUtil saveStar(int postId, long userId) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(postId);
		if (feedPostBase == null) {
			boUtil.setCode(ErrorCode.POST_EXISTSING_ERROR);
			boUtil.setMsg("dynamic does not exist");
			return boUtil;
		}
		// 判断是否喜欢过
		boolean bool = feedPostBaseCache.hasStar(postId, userId);
		if (bool) {
			boUtil.setCode(ErrorCode.EXIST_STAR);
			boUtil.setMsg("you have star this msg");
			return boUtil;
		} else {
			// 更新
			feedPostBaseCache.star(postId, userId);
			if (feedPostBase.getTopicId() > 0) {
				// 更新话题点赞量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.STARCOUNT, 1);
			}
			// 更新数据库喜欢数量
			MomentCount momentCount = feedPostBaseCache.getMomentCount(postId);
			feedPostBase.setUpdateBy(userId);
			feedPostBase.setStarCnt(momentCount.getStarCount());
			feedPostBaseDao.update(feedPostBase);
			// 如果点赞别人的动态
			boolean flag = false;
			if (feedPostBase.getUserId() != userId) {// 点赞别人的动态
				flag = true;
			}
			// 消息推送
			if (flag == true) {
				FeedPostInfo feedPostInfo = FeedPostInfo.builder().postId(postId).type(1).infoType(2).fromUserId(userId)
						.status(2).userId(feedPostBase.getUserId()).createBy(userId)
						.activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
				String indexPicture = "";
				if (!StringUtils.isBlank(feedPostBase.getContent())) {
					if (feedPostBase.getContent().length() > 50) {
						feedPostInfo.setShortText(feedPostBase.getContent().substring(0, 50));
					} else {
						feedPostInfo.setShortText(feedPostBase.getContent());
					}
				}
				if (feedPostBase.getForwardPostId() > 0) {// 点赞转发动态
					if (StringUtils.isBlank(feedPostBase.getContent())) {
						indexPicture = feedPostResDao.getIndexPicture(feedPostBase.getForwardPostId());
						if (StringUtils.isBlank(indexPicture)) {
							FeedPostBase formerPost = feedPostBaseDao
									.getFeedPostBaseById(feedPostBase.getForwardPostId());
							if (null != formerPost) {
								feedPostInfo.setShortText(formerPost.getContent());
							}
						}
					}
				} else {
					indexPicture = feedPostResDao.getIndexPicture(feedPostBase.getId());
				}
				feedPostInfo.setAvatar(indexPicture);
				feedPostInfoDao.save(feedPostInfo);

				// 点赞推送通知
				UserProfile userProfile = accountClient.getUserProfileByUserId(userId);
				// 评论推送消息
				String content = userProfile.getNickName() + "喜欢了你的动态,快去看看吧";
				SimplePushModel simplePushModel = SimplePushModel.builder().fromUserId(userId)
						.toUserId(feedPostInfo.getUserId()).toAppType(1).isRemind(false).content(content).build();
				pushConsumerService.simplePush(simplePushModel);

			}
			boUtil = BoUtil.getDefaultTrueBo();
			return boUtil;
		}
	}

	@Override
	public BoUtil unStar(int postId, long userId) {
		// 判断是否喜欢过
		boolean bool = feedPostBaseCache.hasStar(postId, userId);
		if (bool) {
			FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(postId);
			if (feedPostBase == null) {
				BoUtil boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.POST_EXISTSING_ERROR);
				boUtil.setMsg("dynamic does not exist");
				return boUtil;
			}
			feedPostBaseCache.unStar(postId, userId);
			if (feedPostBase.getTopicId() > 0) {
				// 更新话题点赞量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.STARCOUNT, -1);
			}
			// 更新数据库喜欢数量
			MomentCount momentCount = feedPostBaseCache.getMomentCount(postId);
			;
			feedPostBase.setStarCnt(momentCount.getStarCount());
			feedPostBase.setUpdateBy(userId);
			feedPostBaseDao.update(feedPostBase);

			return BoUtil.getDefaultTrueBo();
		} else {
			BoUtil boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.NOT_START_ERROR);
			boUtil.setMsg(" not star error");
			return boUtil;
		}
	}

	@Override
	public List<UserProfile> getStarUserList(StarUserListVo starUserListVo) {
		List<UserProfile> userProfiles = null;
		List<Long> list = feedPostBaseCache.getPostStarUserIdByPostId(starUserListVo.getPostId(),
				starUserListVo.getOffset(), starUserListVo.getPageSize());
		if (list != null && list.size() > 0) {
			String userIds = StringUtils.join(list.toArray(), ",");
			UserProfileVo userProfileVo = UserProfileVo.builder().userId(starUserListVo.getUserId()).userIds(userIds)
					.build();
			userProfiles = accountClient.getUserProfileList(userProfileVo);
		}
		return userProfiles;
	}

	@Override
	public List<FeedPostBaseBo> getFeedPostBaseListByTopicSectionPostListVo(
			TopicSectionPostListVo topicSectionPostListVo) {
		List<FeedPostBaseBo> list = feedPostBaseDao.getFeedPostBaseListByTopicSectionPostListVo(topicSectionPostListVo);
		for (FeedPostBaseBo feedPostBaseBo : list) {
			// 浏览数+1
			feedPostBaseCache.updateMomentCountByHashKey(feedPostBaseBo.getId(), RedisKeyUtils.VIEWCOUNT, 1);
			if (feedPostBaseBo.getTopicId() > 0) {
				// 更新话题浏览量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBaseBo.getTopicId(), RedisKeyUtils.VIEWCOUNT, 1);
			}
			// 如果是转发动态 处理
			if (feedPostBaseBo.getForwardPostId() > 0) {
				FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(feedPostBaseBo.getForwardPostId());
				if (feedPostBase != null) {
					ForwardBo forwardBo = ForwardBo.builder().content(feedPostBase.getContent())
							.activeFlag(feedPostBase.getActiveFlag()).build();
					UserProfile user = accountClient.getUserProfileByUserId(feedPostBase.getUserId());
					if (user != null) {
						forwardBo.setUser(user);
					}
					List<FeedPostRes> postResourceBo = feedPostResDao
							.getFeedPostResListByPostId(feedPostBaseBo.getForwardPostId());
					forwardBo.setPostResourceBo(postResourceBo);
					feedPostBaseBo.setForward(forwardBo);
				}
			}
			// 获取前三条评论
			FeedCommentVo feedCommentVo = FeedCommentVo.builder().postId(feedPostBaseBo.getId()).offset(0).pageSize(3)
					.build();
			List<FeedCommentBo> commentBos = feedCommentService.getFeedCommentlist(feedCommentVo);
			feedPostBaseBo.setCommentBos(commentBos);
			// 获取用户信息
			UserProfile user = accountClient.getUserProfileByUserId(feedPostBaseBo.getUserId());
			feedPostBaseBo.setUser(user);
			// 获取点赞数 喜欢数 评论数
			MomentCount momentCount = feedPostBaseCache.getMomentCount(feedPostBaseBo.getId());
			feedPostBaseBo.setMomentCount(momentCount);

			// 动态图片
			List<FeedPostRes> postResourceBo = feedPostResDao.getFeedPostResListByPostId(feedPostBaseBo.getId());
			feedPostBaseBo.setPostResourceBo(postResourceBo);
			log.info(" postResourceBo : {} ", postResourceBo);
			// 是否喜欢
			feedPostBaseBo
					.setStar(feedPostBaseCache.hasStar(feedPostBaseBo.getId(), topicSectionPostListVo.getUserId()));
		}
		return list;
	}

	@Override
	public List<FeedPostBaseBo> getFansDynamicsList(FansDynamicVo fansDynamicVo) {
		List<Integer> list = feedPostBaseCache.getUserTimeline(fansDynamicVo.getUserId(),
				fansDynamicVo.getOffset() == 0 ? fansDynamicVo.getOffset() : fansDynamicVo.getOffset() + 1,
				fansDynamicVo.getOffset() + fansDynamicVo.getPageSize());
		if (list.size() == 0) {
			return null;
		}
		List<FeedPostBaseBo> postBaseBos = feedPostBaseDao.getFeedPostBaseListByIds(list);
		for (FeedPostBaseBo feedPostBaseBo : postBaseBos) {
			// 浏览数+1
			feedPostBaseCache.updateMomentCountByHashKey(feedPostBaseBo.getId(), RedisKeyUtils.VIEWCOUNT, 1);
			if (feedPostBaseBo.getTopicId() > 0) {
				// 更新话题浏览量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBaseBo.getTopicId(), RedisKeyUtils.VIEWCOUNT, 1);
			}
			// 如果是转发动态 处理
			if (feedPostBaseBo.getForwardPostId() > 0) {
				FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(feedPostBaseBo.getForwardPostId());
				if (feedPostBase != null) {
					ForwardBo forwardBo = ForwardBo.builder().content(feedPostBase.getContent())
							.activeFlag(feedPostBase.getActiveFlag()).build();
					UserProfile user = accountClient.getUserProfileByUserId(feedPostBase.getUserId());
					if (user != null) {
						forwardBo.setUser(user);
					}
					List<FeedPostRes> postResourceBo = feedPostResDao
							.getFeedPostResListByPostId(feedPostBaseBo.getForwardPostId());
					forwardBo.setPostResourceBo(postResourceBo);
					feedPostBaseBo.setForward(forwardBo);
				}
			}
			// 获取前三条评论
			FeedCommentVo feedCommentVo = FeedCommentVo.builder().postId(feedPostBaseBo.getId()).offset(0).pageSize(3)
					.build();
			List<FeedCommentBo> commentBos = feedCommentService.getFeedCommentlist(feedCommentVo);
			feedPostBaseBo.setCommentBos(commentBos);
			// 获取用户信息
			UserProfile user = accountClient.getUserProfileByUserId(feedPostBaseBo.getUserId());
			feedPostBaseBo.setUser(user);
			// 获取点赞数 喜欢数 评论数
			MomentCount momentCount = feedPostBaseCache.getMomentCount(feedPostBaseBo.getId());
			feedPostBaseBo.setMomentCount(momentCount);

			// 动态图片
			List<FeedPostRes> postResourceBo = feedPostResDao.getFeedPostResListByPostId(feedPostBaseBo.getId());
			log.info(" postResourceBo : {} ", postResourceBo);
			feedPostBaseBo.setPostResourceBo(postResourceBo);

			// 是否喜欢
			feedPostBaseBo.setStar(feedPostBaseCache.hasStar(feedPostBaseBo.getId(), fansDynamicVo.getUserId()));
		}
		return postBaseBos;
	}

}
