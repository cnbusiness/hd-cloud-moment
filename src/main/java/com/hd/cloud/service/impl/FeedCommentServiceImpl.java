package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.bo.FeedCommentBo;
import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.MomentCount;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.FeedCommentDao;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.dao.FeedPostResDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.model.SimplePushModel;
import com.hd.cloud.service.FeedCommentService;
import com.hd.cloud.service.PushConsumerService;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.RedisKeyUtils;
import com.hd.cloud.vo.FeedCommentVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CommonConstantUtil;

@Service
public class FeedCommentServiceImpl implements FeedCommentService {

	@Inject
	private FeedCommentDao feedCommentDao;

	@Autowired
	private AccountClient accountClient;

	@Inject
	private FeedPostInfoDao feedPostInfoDao;

	@Inject
	private FeedPostBaseDao feedPostBaseDao;

	@Inject
	private FeedPostResDao feedPostResDao;

	@Inject
	private FeedPostBaseCache feedPostBaseCache;
	
	@Inject
	private PushConsumerService pushConsumerService;

	@Override
	public List<FeedCommentBo> getFeedCommentlist(FeedCommentVo feedCommentVo) {
		List<FeedComment> list = feedCommentDao.getFeedCommentlist(feedCommentVo);
		
		List<FeedCommentBo> commentBos = Lists.newArrayList();
		List<UserProfile> userBoList = Lists.newArrayList();
		for (FeedComment feedComment : list) {
			// 获取作者和被评论人
			if (feedComment.getUserId() > 0) {
				UserProfile userProfile = accountClient.getUserProfileByUserId(feedComment.getUserId());
				userBoList.add(userProfile);
			}
			if (feedComment.getCommentUserId() > 0) {
				UserProfile userProfile = accountClient.getUserProfileByUserId(feedComment.getCommentUserId());
				userBoList.add(userProfile);
			}
		}
		FeedCommentBo feedCommentBo = null;
		for (FeedComment feedComment : list) {
			feedCommentBo = FeedCommentBo.builder().id(feedComment.getId()).comment(feedComment.getComment())
					.postId(feedComment.getPostId()).commentUserId(feedComment.getCommentUserId())
					.userId(feedComment.getUserId()).createTime(feedComment.getCreateTime()).build();
			for (UserProfile userProfile : userBoList) {
				if (userProfile!=null && userProfile.getUserId() == feedCommentBo.getUserId()) {
					feedCommentBo.setPublishUser(userProfile);
				}
				if (feedCommentBo.getCommentUserId() > 0) {
					if (userProfile.getUserId() == feedCommentBo.getCommentUserId()) {
						feedCommentBo.setReplyUser(userProfile);
					}
				}
			}
			commentBos.add(feedCommentBo);
		}
		return commentBos;
	}

	@Override
	public BoUtil save(FeedComment comment) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		if (StringUtils.isBlank(comment.getComment())) {
			boUtil.setCode(ErrorCode.COMMENT_EMPTY);
			boUtil.setMsg(" content cannot be null");
			return boUtil;
		}
		if (comment.getPostId() <= 0) {
			boUtil.setCode(ErrorCode.POSTID_EMPTY);
			boUtil.setMsg(" post id cannot be null");
			return boUtil;
		}
		// 查询动态是否存在
		FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(comment.getPostId());
		if (feedPostBase == null) {
			boUtil.setCode(ErrorCode.POST_EXISTSING_ERROR);
			boUtil.setMsg(" post no existsing");
			return boUtil;
		}
		int result = feedCommentDao.save(comment);
		if (result > 0) {
			// 更新评论数量
			feedPostBaseCache.updateMomentCountByHashKey(comment.getPostId(), RedisKeyUtils.REVIEWCNT, 1);
			MomentCount momentCount = feedPostBaseCache.getMomentCount(comment.getPostId());
			feedPostBase.setReviewCnt(momentCount.getReviewCnt());
			feedPostBaseDao.update(feedPostBase);
			
			//更新话题评论数量
			if(feedPostBase.getTopicId()>0) {
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.REVIEWCNT, 1);
			}
			

			//获取用户详情
			UserProfile userProfile= accountClient.getUserProfileByUserId(comment.getCreateBy());
			String content="";
			// 保存消息中心 推送
			boolean flag = false; // true 代表需要推送 false 代表不需要推送
			if (comment.getCommentUserId() == 0 && feedPostBase.getUserId() != comment.getUserId()) {// 评论别人的动态
				flag = true;
				content=userProfile.getNickName()+"评论了你的动态,快去看看吧";
			}
			if (comment.getCommentUserId() > 0 && comment.getCommentUserId() != comment.getUserId()) {// 回复别人需要推送
				flag = true;
				content=userProfile.getNickName()+"回复了你,快去看看吧";
			}
			if (flag) {
				long replyUserId = comment.getCommentUserId();
				// 消息
				FeedPostInfo feedPostInfo = FeedPostInfo.builder().type(1).content(comment.getComment())
						.userId(replyUserId > 0 ? replyUserId : feedPostBase.getUserId()).build();
				String indexPicture = "";
				if (!StringUtils.isBlank(feedPostBase.getContent())) {
					if (feedPostBase.getContent().length() > 50) {
						feedPostInfo.setShortText(feedPostBase.getContent().substring(0, 50));
					} else {
						feedPostInfo.setShortText(feedPostBase.getContent());
					}
				}
				// 如果动态是转发的 获取第一张图片
				if (feedPostBase.getForwardPostId() > 0) {
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
				feedPostInfo
						.setInfoType(replyUserId > 0 ? ConstantUtil.OPERATION_REPLY : ConstantUtil.OPERATION_COMMONT);
				feedPostInfo.setCreateBy(comment.getCreateBy());
				feedPostInfo.setActiveFlag(CommonConstantUtil.ACTIVE_FLAG_Y);
				feedPostInfo.setPostId(comment.getPostId());
				feedPostInfo.setAvatar(indexPicture);
				feedPostInfo.setFromUserId(feedPostBase.getCreateBy());
				feedPostInfo.setStatus(ConstantUtil.READ_TYPE_UNREAD);
				feedPostInfoDao.save(feedPostInfo);

				// 评论推送消息
				SimplePushModel simplePushModel = SimplePushModel.builder().fromUserId(comment.getCreateBy())
						.toUserId(feedPostInfo.getUserId())
						.toAppType(1).isRemind(false).content(content).build();
				pushConsumerService.simplePush(simplePushModel);
				
			}

			boUtil = BoUtil.getDefaultTrueBo();
			boUtil.setMsg("comment  success");
		} else {
			boUtil.setMsg("comment fail");
		}
		return boUtil;
	}

	@Override
	public int update(FeedComment comment) {
		// 删除评论
		int result = feedCommentDao.update(comment);
		if (result > 0) {
			// 查询动态是否存在
			FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(comment.getPostId());
			// 更新评论数量
			feedPostBaseCache.updateMomentCountByHashKey(comment.getPostId(), RedisKeyUtils.REVIEWCNT, -1);
			//获取最新数据同步
			MomentCount momentCount = feedPostBaseCache.getMomentCount(comment.getPostId());
			feedPostBase.setReviewCnt(momentCount.getReviewCnt());
			feedPostBaseDao.update(feedPostBase);
			
			//更新话题评论数量
			if(feedPostBase.getTopicId()>0) {
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.REVIEWCNT, -1);
			}
		}
		return result;
	}

}
