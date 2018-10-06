package com.hd.cloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityComment;
import com.hd.cloud.bo.ActivityCommentBo;
import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.ActivityBaseDao;
import com.hd.cloud.dao.ActivityCommentDao;
import com.hd.cloud.dao.ActivityCountDao;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.model.SimplePushModel;
import com.hd.cloud.service.ActivityCommentService;
import com.hd.cloud.service.PushConsumerService;
import com.hd.cloud.util.ConstantActivityUtil;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.vo.ActivityCommentVo;
import com.hd.cloud.vo.ActivityCountUpdateVo;
import com.hd.cloud.vo.UserProfileVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CommonConstantUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityCommentServiceImpl
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:18:32
 *
 */

@Slf4j
@Service
public class ActivityCommentServiceImpl implements ActivityCommentService {

	@Inject
	private ActivityCommentDao activityCommentDao;

	@Inject
	private ActivityCountDao activityCountDao;

	@Inject
	private ActivityBaseDao activityBaseDao;

	@Inject
	private FeedPostInfoDao feedPostInfoDao;

	@Inject
	private PushConsumerService pushConsumerService;

	@Autowired
	private AccountClient accountClient;

	@Override
	@Transactional
	public BoUtil addActivityComment(ActivityCommentVo activityCommentVo, long userId) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		log.debug("@activityCommentVo{},@loginUserId{}", activityCommentVo, userId);
		int activityId = activityCommentVo.getActivityId();
		long commenter = activityCommentVo.getCommenter();
		log.debug("********************检测活动是否存在开始********************");
		ActivityBase activityBase = activityBaseDao.getActivityBaseById(activityId);
		log.debug("********************检测活动是否存在结束********************", activityBase);
		if (activityBase == null) {// 验证活动是否存在
			boUtil.setCode(ErrorCode.ACTIVITY_NOT_FOUND);
			boUtil.setMsg("Activity does not exist");
			return boUtil;
		}
		ActivityComment activityComment = ActivityComment.builder().activityId(activityId).commenter(commenter)
				.parentId(activityCommentVo.getParentId()).content(activityCommentVo.getContent())
				.appType((short) activityCommentVo.getType()).commentType((short) activityCommentVo.getCommentType())
				.commentUrls(activityCommentVo.getCommentUrls()).commentStatus(1).creater(userId).updater(userId)
				.build();
		log.debug("********************新增活动评论开始********************", activityComment);
		activityCommentDao.addActivityComment(activityComment);
		log.debug("********************新增活动评论开始********************");

		ActivityCountUpdateVo activityCountUpdateVo = ActivityCountUpdateVo.builder().activityId(activityId)
				.pictureUrl(activityCommentVo.getCommentUrls()).type(ConstantActivityUtil.COMMENT_COUNT).build();
		log.debug("********************活动评论量增加开始********************", activityCountUpdateVo);
		activityCountDao.updateActivityCount(activityCountUpdateVo);
		log.debug("********************活动评论量增加结束********************");

		// 发送推送消息告诉用户有人评论
		if (activityBase.getAppType() == (short) 1 || activityBase.getAppType() == (short) 3) {// 用户活动才推送
			if (activityCommentVo.getType() == 1 && activityCommentVo.getCommentType() != 3) {// 用户端评论 &&
				boolean flag = false; // true 需要推送 false 不需要推送
				if (activityCommentVo.getCommentType() == 1 && activityBase.getOrganizer() != userId) {// 评论别人发布的活动
					flag = true;
				}
				if (activityCommentVo.getCommentType() == 2 && activityCommentVo.getParentId() != userId) {// 对别人评论
					flag = true;
				}
				if (flag == true) {
					// 消息
					FeedPostInfo feedPostInfo = FeedPostInfo.builder().type(3)
							.content("《" + activityBase.getTheme() + "》").postId(activityId)
							.avatar(activityBase.getLogoUrl()).createBy(userId).fromUserId(userId)
							.activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).status(ConstantUtil.READ_TYPE_UNREAD).build();
					UserProfile userProfile = accountClient.getUserProfileByUserId(userId);
					String content = "";
					if (activityCommentVo.getCommentType() == 1) {// 对活动评论，推送给活动创建人
						feedPostInfo.setUserId(activityBase.getOrganizer());
						feedPostInfo.setInfoType(1);
						content = userProfile.getNickName() + "评论了你发布的活动,快去看看吧";
					} else if (activityCommentVo.getCommentType() == 2) {// 对用户评论，推送给该用户
						feedPostInfo.setUserId(activityCommentVo.getParentId());
						feedPostInfo.setInfoType(3);
						content = userProfile.getNickName() + "回复了你,快去看看吧";
					}
					feedPostInfoDao.save(feedPostInfo);
					// 推送消息
					SimplePushModel simplePushModel = SimplePushModel.builder().fromUserId(userId)
							.toUserId(feedPostInfo.getUserId()).toAppType(1).isRemind(false).content(content).build();
					pushConsumerService.simplePush(simplePushModel);

				}
			}
		} else {// 商家活动
			if (activityCommentVo.getType() == 1 && activityCommentVo.getCommentType() != 2) {// 用户端评论 &&
				// 获取评论者的用户名
				String commentName = "";
				UserProfile userProfile = accountClient.getUserProfileByUserId(commenter);
				if (userProfile != null) {
					commentName = userProfile.getNickName();
				}
				// 用户名 评论了活动<活动名称>：用户评论内容
				// 用户名 在活动<活动名称>中回复了您：用户评论内容
				String msg = commentName;
				if (activityCommentVo.getCommentType() == 1) {// 评论
					msg += " 评论了活动 <" + activityBase.getTheme() + ">:";
				}
				if (activityCommentVo.getCommentType() == 3) {// 回复
					msg += " 在活动 <" + activityBase.getTheme() + ">中回复了您:";
				}
				String content = activityCommentVo.getContent();
				String commentUrls = activityCommentVo.getCommentUrls();
				if (!StringUtils.isBlank(content)) {
					msg += content;
					if (!StringUtils.isBlank(commentUrls)) {
						msg += " + [图片]";
					}
				} else {
					if (!StringUtils.isBlank(commentUrls)) {
						msg += " [图片]";
					}
				}

				int[] roles = { 1, 2, 3, 4 };
				// 默认推送给店铺所有人 TODO

			}
		}
		return BoUtil.getDefaultTrueBo();
	}

	@Override
	public List<ActivityCommentBo> getAllComments(int type, int activityId, int pageIndex, int pageSize) {
		log.debug("@activityId{},@type{},@pageIndex{},@pageSize{}", activityId, type, pageIndex, pageSize);
		log.debug("********************获取活动评论列表开始********************");
		List<ActivityComment> commentList = activityCommentDao.getAllComments(type, activityId, pageIndex, pageSize);
		log.debug("********************获取活动评论列表结束********************", commentList);

		List<Long> userIds = Lists.newArrayList();// 用户Id数组
		Set<Integer> shopIds = new HashSet<Integer>();// 店铺Id数组
		List<ActivityCommentBo> activityCommentList = new ArrayList<ActivityCommentBo>();// 返回的评论列表

		for (ActivityComment activityComment : commentList) {
			if (activityComment.getAppType() == (short) 1) {// 用户评论
				userIds.add(Long.parseLong(activityComment.getCommenter() + ""));
			}
			if (activityComment.getAppType() == (short) 2) {// 商家评论
				shopIds.add(Integer.parseInt(activityComment.getCommenter() + ""));
			}
			if (activityComment.getCommentType() == (short) 3) {// 对店铺评论
				shopIds.add(Integer.parseInt(activityComment.getParentId() + ""));
			}
			if (activityComment.getCommentType() == (short) 2) {// 对用户评论
				userIds.add(Long.parseLong(activityComment.getParentId() + ""));
			}
		}
		List<UserProfile> userList = null;
		if (userIds.size() > 0) {
			log.debug("********************thrift调用用户信息开始********************", userIds);
			UserProfileVo userProfileVo = UserProfileVo.builder().userId(0)
					.userIds(StringUtils.join(userIds.toArray(), ",")).build();
			userList = accountClient.getUserProfileList(userProfileVo);
			log.debug("********************thrift调用用户信息结束********************");
		}

		for (ActivityComment activityComment : commentList) {
			ActivityCommentBo commontBo = new ActivityCommentBo();
			commontBo.setActivityId(activityComment.getActivityId());
			commontBo.setContent(activityComment.getContent());
			commontBo.setPictureUrl(activityComment.getCommentUrls());
			commontBo.setCommentTime(new Date(activityComment.getCreateTime().getTime()));
			commontBo.setCommenterId(activityComment.getCommenter());
			commontBo.setReplyId(activityComment.getParentId());
			if (activityComment.getAppType() == (short) 1) {// 评论者为用户
				for (UserProfile userModel : userList) {
					if (activityComment.getCommenter() == userModel.getUserId()) {
						commontBo.setAvatar(userModel.getAvatar());
						commontBo.setCommenterName(userModel.getNickName());
						commontBo.setCommentType(1);
					}
				}
			}

			if (activityComment.getCommentType() == (short) 2) {// 回复者为用户
				for (UserProfile userModel : userList) {
					if (activityComment.getParentId() == userModel.getUserId()) {
						commontBo.setReplyName(userModel.getNickName());
						commontBo.setReplyId(2);
					}
				}
			} else {// 回复活动
				commontBo.setReplyId(1);
			}
			activityCommentList.add(commontBo);
		}

		return activityCommentList;
	}

}
