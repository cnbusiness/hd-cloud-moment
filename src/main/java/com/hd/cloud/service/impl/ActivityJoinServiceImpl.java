package com.hd.cloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityCount;
import com.hd.cloud.bo.ActivityJoin;
import com.hd.cloud.bo.ActivityJoinBo;
import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.ActivityBaseDao;
import com.hd.cloud.dao.ActivityCountDao;
import com.hd.cloud.dao.ActivityJoinDao;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.model.SimplePushModel;
import com.hd.cloud.service.ActivityJoinService;
import com.hd.cloud.service.PushConsumerService;
import com.hd.cloud.util.ConstantActivityUtil;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.vo.ActivityCountUpdateVo;
import com.hd.cloud.vo.ActivitySignUpVo;
import com.hd.cloud.vo.UserProfileVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CommonConstantUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityJoinServiceImpl
 * @Description: 活动报名人
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午4:39:59
 *
 */
@Slf4j
@Service
public class ActivityJoinServiceImpl implements ActivityJoinService {

	@Inject
	private ActivityJoinDao activityJoinDao;

	@Inject
	private ActivityBaseDao activityBaseDao;

	@Inject
	private ActivityCountDao activityCountDao;
	
	@Inject
	private FeedPostInfoDao feedPostInfoDao;

	@Autowired
	private AccountClient accountClient;

	@Inject
	private RedisTemplate<String, String> redisTemplate;
	
	@Inject
	private PushConsumerService pushConsumerService;

	@Override
	@Transactional
	public BoUtil signUpActivity(ActivitySignUpVo activitySignUpVo, long userId) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		log.debug("@activitySignUpVo{},@loginUserId", activitySignUpVo, userId);
		int activityId = activitySignUpVo.getActivityId();
		log.debug("********************检测活动是否存在开始********************");
		ActivityBase activityBase = activityBaseDao.getActivityBaseById(activityId);
		log.debug("********************检测活动是否存在结束********************", activityBase);
		if (activityBase == null) {// 验证活动是否存在
			boUtil.setCode(ErrorCode.ACTIVITY_NOT_FOUND);
			boUtil.setMsg("Activity does not exist");
			return boUtil;
		}
		if (activityBase.getFinishTime().before(new Date())) {// 验证活动是否已经结束，结束即截止报名
			boUtil.setCode(ErrorCode.ACTIVITY_HAD_END);
			boUtil.setMsg("Activity is over");
			return boUtil;
		}
		log.debug("********************获取活动动态信息开始********************");
		ActivityCount activityCount = activityCountDao.getActivityCountById(activityId);
		log.debug("********************获取活动动态信息结束********************", activityCount);
		if (activityCount != null) {
			if (activityCount.getJoinCount() >= activityBase.getPersonNumber()) {// 验证报名人数是否已满
				boUtil.setCode(ErrorCode.SIGNUP_COUNT_TO_FULL);
				boUtil.setMsg("Event registration number is full");
				return boUtil;
			}
		} else {
			boUtil.setCode(ErrorCode.ACTIVITY_NOT_FOUND);
			boUtil.setMsg("Activity does not exist");
			return boUtil;
		}
		log.debug("********************根据活动Id和用户Id获取活动详情开始********************");
		ActivityJoin actJoin = activityJoinDao.getActivityJoinById(activityId, userId);
		log.debug("********************根据活动Id和用户Id获取活动详情结束********************", actJoin);
		if (actJoin != null && actJoin.getPersonType() == (short) 1
				&& actJoin.getActiveFlag() == CommonConstantUtil.ACTIVE_FLAG_Y.charAt(0)) {// 验证用户是否已报名该活动
			boUtil.setCode(ErrorCode.YOU_HAD_SIGNUP_ACTIVITY);
			boUtil.setMsg("You have signed up for this event");
			return boUtil;
		}
		ActivityJoin activityJoin = ActivityJoin.builder().activityId(activityId).userId(userId)
				.phone(activitySignUpVo.getPhone()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y.charAt(0))
				.alaisName(activitySignUpVo.getExplain()).personType((short) 1).creater(userId).updater(userId).build();

		ActivityCountUpdateVo activityCountUpdateVo = ActivityCountUpdateVo.builder().activityId(activityId)
				.type(ConstantActivityUtil.JOIN_COUNT_ADD).build();
		log.debug("********************活动报名人数+1开始********************", activityCountUpdateVo);
		activityCountDao.updateActivityCount(activityCountUpdateVo);
		log.debug("********************活动报名人数+1结束********************");
		if (actJoin == null) {// 用户从未报名过该活动
			log.debug("********************新增活动报名开始********************", activityJoin);
			activityJoinDao.addActivityJoin(activityJoin);
			log.debug("********************新增活动报名结束********************");
		} else if (actJoin.getActiveFlag() != CommonConstantUtil.ACTIVE_FLAG_Y.charAt(0)
				|| actJoin.getPersonType() == (short) 2) {// 以前取消报名或者报名记录被删除
			log.debug("********************修改活动报名开始********************", activityJoin);
			activityJoinDao.updateActivityJoin(activityJoin);
			log.debug("********************修改活动报名结束********************");
		}

		if (activityBase.getAppType() == (short) 1 || activityBase.getAppType() == (short) 3) {// 报名魔线活动
			if (userId != activityBase.getOrganizer()) {// 报名参加别人发布的活动才推送
				// 发送推送消息告诉用户有人报名活动
				FeedPostInfo feedPostInfo = FeedPostInfo.builder().type(3)
						.content("《" + activityBase.getTheme() + "》").postId(activityId).userId(activityBase.getCreater())
						.avatar(activityBase.getLogoUrl()).createBy(userId).fromUserId(userId).infoType(ConstantUtil.OPERATION_JOIN)
						.activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).status(ConstantUtil.READ_TYPE_UNREAD)
						.build();
				feedPostInfoDao.save(feedPostInfo);
				//获取用户详情
				UserProfile userProfile= accountClient.getUserProfileByUserId(userId);
				
				// 推送消息
				String content=userProfile.getNickName()+"报名了活动,快去看看吧";
				SimplePushModel simplePushModel = SimplePushModel.builder().fromUserId(userId)
						.toUserId(feedPostInfo.getUserId())
						.toAppType(1).isRemind(false).content(content).build();
				pushConsumerService.simplePush(simplePushModel);
			}
		} else {
			// 报名魔商活动 成为店铺粉丝
		}

		String joinNumber = redisTemplate.opsForValue().get("activityId:" + activityId);
		int count = 0;
		if (!("".equals(joinNumber) || null == joinNumber)) {
			count = Integer.parseInt(joinNumber);
			count += 1;
		} else {
			count = 1;
		}
		redisTemplate.opsForValue().set("activityId:" + activityId, count + "");
		return BoUtil.getDefaultTrueBo();
	}

	@Override
	@Transactional
	public BoUtil cancelSignUpActivity(int activityId, long userId) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		log.debug("@activityId{},@loginUserId", activityId, userId);
		log.debug("********************检测活动是否存在开始********************");
		ActivityBase activityBase = activityBaseDao.getActivityBaseById(activityId);
		log.debug("********************检测活动是否存在结束********************", activityBase);
		if (activityBase == null) {// 验证活动是否存在
			boUtil.setCode(ErrorCode.ACTIVITY_NOT_FOUND);
			boUtil.setMsg("Activity does not exist");
			return boUtil;
		}
		if (activityBase.getFinishTime().before(new Date())) {// 验证活动是否已经结束，结束即截止取消报名
			boUtil.setCode(ErrorCode.ACTIVITY_HAD_END);
			boUtil.setMsg("Activity is over");
			return boUtil;
		}
		log.debug("********************根据活动Id和用户Id获取活动详情开始********************");
		ActivityJoin actJoin = activityJoinDao.getActivityJoinById(activityId, userId);
		log.debug("********************根据活动Id和用户Id获取活动详情结束********************", actJoin);
		if (actJoin == null || actJoin.getPersonType() == (short) 2
				|| actJoin.getActiveFlag() != CommonConstantUtil.ACTIVE_FLAG_Y.charAt(0)) {// 验证用户是否已报名该活动
			boUtil.setCode(ErrorCode.YOU_HAD_NOT_SIGNUP_ACTIVITY);
			boUtil.setMsg("You have not registered for the event");
			return boUtil;
		} else {
			ActivityJoin activityJoin = ActivityJoin.builder().activityId(activityId).userId(userId)
					.phone(actJoin.getPhone()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y.charAt(0))
					.alaisName(actJoin.getAlaisName()).personType((short) 2).creater(userId).updater(userId).build();
			log.debug("********************取消活动报名开始********************", activityJoin);
			activityJoinDao.updateActivityJoin(activityJoin);
			log.debug("********************取消活动报名结束********************");
		}

		ActivityCountUpdateVo activityCountUpdateVo = ActivityCountUpdateVo.builder().activityId(activityId)
				.type(ConstantActivityUtil.JOIN_COUNT_REDUCE).build();
		log.debug("********************活动报名人数-1开始********************", activityCountUpdateVo);
		activityCountDao.updateActivityCount(activityCountUpdateVo);
		log.debug("********************活动报名人数-1结束********************");

		String joinNumber = redisTemplate.opsForValue().get("activityId:" + activityId);
		if (!("".equals(joinNumber) || null == joinNumber)) {
			int count = Integer.parseInt(joinNumber);
			count -= 1;
			redisTemplate.opsForValue().set("activityId:" + activityId, count + "");
		}
		return BoUtil.getDefaultTrueBo();
	}

	@Override
	public List<ActivityJoinBo> getActivityJoinList(int activityId, int pageIndex, int pageSize, int type,
			long userId) {
		log.debug("@activityId{},@pageIndex{},@pageSize{},@type{},@userId{}", activityId, pageIndex, pageSize, type,
				userId);
		List<ActivityJoinBo> activityJoinList = activityJoinDao.getActivityJoinList(activityId, pageIndex, pageSize);
		log.debug("********************检测活动是否存在开始********************");
		ActivityBase activityBase = activityBaseDao.getActivityBaseById(activityId);
		log.debug("********************检测活动是否存在结束********************");
		List<Long> userIds = Lists.newArrayList();
		for (ActivityJoinBo activityJoinBo : activityJoinList) {
			userIds.add(Long.parseLong(activityJoinBo.getUserId() + ""));
		}

		if (userIds.size() > 0) {
			log.debug("********************thrift获取用户信息开始********************");
			UserProfileVo userProfileVo=UserProfileVo.builder().userId(userId).userIds(StringUtils.join(userIds.toArray(), ",")).build();
			List<UserProfile> userList = accountClient.getUserProfileList(userProfileVo);
			log.debug("********************thrift获取用户信息结束********************", userList);

			if (type == 1) {// 魔线端报名人列表
				if (activityBase.getOrganizer() == userId) {// 创建者自己查看
					for (ActivityJoinBo activityJoinBo : activityJoinList) {
						for (UserProfile userSyncModel : userList) {
							if (activityJoinBo.getUserId() == userSyncModel.getUserId()) {
								activityJoinBo.setNickName(userSyncModel.getNickName());
								activityJoinBo.setAvatar(userSyncModel.getAvatar());
								activityJoinBo.setSex(userSyncModel.getSex());
								activityJoinBo.setBirthday(userSyncModel.getBirthday());
							}
						}
					}
				} else {// 其他人查看
					for (ActivityJoinBo activityJoinBo : activityJoinList) {
						for (UserProfile userSyncModel : userList) {
							if (activityJoinBo.getUserId() == userSyncModel.getUserId()) {
								activityJoinBo.setPhone("");
								activityJoinBo.setExplain("");
								activityJoinBo.setNickName(userSyncModel.getNickName());
								activityJoinBo.setAvatar(userSyncModel.getAvatar());
								activityJoinBo.setSex(userSyncModel.getSex());
								activityJoinBo.setBirthday(userSyncModel.getBirthday());
							}
						}
					}
				}
			}
		}
		return activityJoinList;
	}

}
