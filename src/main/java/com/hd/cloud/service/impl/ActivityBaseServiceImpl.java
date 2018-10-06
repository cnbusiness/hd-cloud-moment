package com.hd.cloud.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.ActivityAddress;
import com.hd.cloud.bo.ActivityAddressBo;
import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityCount;
import com.hd.cloud.bo.ActivityHomeListBo;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.ActivityListBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.HotActivityBo;
import com.hd.cloud.bo.HotActivityListBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.ActivityAddressDao;
import com.hd.cloud.dao.ActivityBaseDao;
import com.hd.cloud.dao.ActivityCountDao;
import com.hd.cloud.dao.ActivityJoinDao;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.service.ActivityBaseService;
import com.hd.cloud.util.ConstantActivityUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.vo.MXActivityVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CommonConstantUtil;
import com.hlb.cloud.util.MapUtil;
import com.hlb.cloud.util.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityBaseServiceImpl
 * @Description: 活动基础信息
 * @author yao.jie@moxiangroup.com
 * @Company moxian
 * @date 2015年7月17日 上午10:09
 *
 */

@Slf4j
@Service
public class ActivityBaseServiceImpl implements ActivityBaseService {

	@Inject
	private ActivityBaseDao activityBaseDao;

	@Inject
	private ActivityAddressDao activityAddressDao;

	@Inject
	private ActivityCountDao activityCountDao;

	@Inject
	private ActivityJoinDao activityJoinDao;

	@Inject
	private FeedPostBaseDao feedPostBaseDao;

	@Autowired
	private AccountClient accountClient;

	@Inject
	private RedisTemplate<String, String> redisTemplate;

	@Override
	@Transactional
	public String addActivity(MXActivityVo activityVo, long userId) {
		log.debug("@activityVo{},@userId{}", activityVo, userId);
		SimpleDateFormat dateFormart = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 时间格式
		String random = System.currentTimeMillis() + RandomUtil.createRandCode(2) + "";// 活动内码规则未定，暂时用随机数代替
		long sTime = activityVo.getStartTime() * 1000l;
		long eTime = activityVo.getEndTime() * 1000l;
		String pictureUrls = activityVo.getPictureUrls();
		String[] pictures = pictureUrls.split("\\|");
		int cityId = activityVo.getCityId();

		ActivityBase activityBase = ActivityBase.builder().innerCode(random).theme(activityVo.getTheme())
				.detail(activityVo.getDetail()).personNumber(5000).shopShield(ConstantActivityUtil.SHOPSCREENING_NORMAL)
				.authType(ConstantActivityUtil.QUALIFICATION_PASS).activityTypeId(activityVo.getActivityTypeId())
				.isNeedPhone((short) activityVo.getIsNeedPhone()).appType((short) 1).organizer(userId).status((short) 0)
				.pictureUrls(pictureUrls).logoUrl(pictures[0]).couponId(0).cityId(cityId).isNeedNotice((short) 2)
				.delayHour(0).hasShake((short) 2).creater(userId).updater(userId).build();

		try {
			Date startDate = dateFormart.parse(dateFormart.format(sTime));
			Date endDate = dateFormart.parse(dateFormart.format(eTime));
			String startTime = timeFormat.format(sTime);
			String endTime = timeFormat.format(eTime);
			activityBase.setStartDate(startDate);
			activityBase.setEndDate(endDate);
			activityBase.setStartTime(startTime);
			activityBase.setEndTime(endTime);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		log.debug("********************创建活动基础信息开始********************", activityBase);
		activityBaseDao.addActivity(activityBase);
		log.debug("********************创建活动基础信息结束********************");
		int activityId = activityBase.getId();

		ActivityAddress activityAddress = ActivityAddress.builder().activityId(activityId)
				.latitude(activityVo.getLatitude()).longitude(activityVo.getLongitude())
				.address(activityVo.getAddress()).creater(userId).updater(userId).build();
		log.debug("********************创建活动地址开始********************", activityAddress);
		activityAddressDao.addActivityAddress(activityAddress);
		log.debug("********************创建活动地址结束********************");

		ActivityCount activityCount = ActivityCount.builder().activityId(activityId).forwardCount(0).commentPicCount(0)
				.commentCount(0).favoriteCount(0).joinCount(0).viewCount(0).shareCount(0).creater(userId)
				.updater(userId).build();
		log.debug("********************创建活动统计信息开始********************", activityCount);
		activityCountDao.addActivityCount(activityCount);
		log.debug("********************创建活动统计信息结束********************");

		int hour = (int) (((sTime / 1000l) - System.currentTimeMillis() / 1000l) / 3600);
		if (hour >= 2) {
			redisTemplate.opsForList().leftPush("activity:time", activityId + "|" + sTime / 1000l + "|" + 1);
		}

		FeedPostBase feedPostBase = FeedPostBase.builder().type(3).longitude(activityVo.getLongitude()).content(activityVo.getTheme())
				.latitude(activityVo.getLatitude()).topicId(activityId).cityId(activityVo.getCityId()).visibleType(1)
				.userType(1).picUrl(pictures[0]).address(activityVo.getAddress()).userId(userId).appType(1).status(1)
				.createBy(userId).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
		feedPostBaseDao.save(feedPostBase);

		return activityId + "|" + 0;
	}

	@Override
	public Integer checkActivityRequire(int activityId) {
		return activityBaseDao.checkActivityRequire(activityId);
	}

	@Override
	public ActivityHomeListBo getActivityList(int pageIndex, int pageSize, int activityTypeId, int activityTime,
			int type, int cityId, double longitude, double latitude, long userId) {
		log.debug("@pageIndex{},@activityTypeId{},@activityTime{},@type{},@cityId{},@longitude{},@latitude{}",
				pageIndex, activityTypeId, activityTime, type, cityId, longitude, latitude);
		List<ActivityListBo> activityList = new ArrayList<ActivityListBo>();

		if (type != 0) {// 2.人气最高 3.最新上线 4.将要开始
			log.debug("********************获取城市活动开始********************");
			activityList = activityBaseDao.getActivityList(pageIndex, pageSize, activityTypeId, activityTime, type,
					cityId);
			log.debug("********************获取城市活动结束********************", activityList);

			List<Integer> activityIdList = new ArrayList<Integer>();
			for (ActivityListBo activityListBo : activityList) {
				log.debug("********************获取用户是否报名活动关系开始********************");
				int joinResult = activityJoinDao.checkUserSignUp(activityListBo.getActivityId(), userId);
				log.debug("********************获取用户是否报名活动关系结束********************");
				activityListBo.setJoin(joinResult > 0 ? true : false);
				activityIdList.add(activityListBo.getActivityId());
			}

			if (activityIdList.size() > 0) {
				log.debug("********************批量获取活动地点开始********************");
				List<ActivityAddressBo> addressList = activityAddressDao.getActivityAddress(activityIdList);
				log.debug("********************批量获取活动地点结束********************", addressList);

				List<ActivityAddressBo> tmpList = null;
				for (Integer activityIds : activityIdList) {
					tmpList = new ArrayList<ActivityAddressBo>();
					for (ActivityAddressBo activityAddressBo : addressList) {// 获取一个活动的所有地点
						if (activityIds == activityAddressBo.getActivityId()) {
							tmpList.add(activityAddressBo);
						}
					}
					// 排序(找到最近的一个)
					for (int i = tmpList.size() - 1; i > 0; i--) {
						ActivityAddressBo lastAddr = tmpList.get(i);
						ActivityAddressBo preAddr = tmpList.get(i - 1);
						// 计算距离
						if (i == tmpList.size() - 1) {
							lastAddr.setDistance(MapUtil.GetDistance(longitude, latitude, lastAddr.getLongitude(),
									lastAddr.getLatitude()) / 1000.0);
							preAddr.setDistance(MapUtil.GetDistance(longitude, latitude, preAddr.getLongitude(),
									preAddr.getLatitude()) / 1000.0);
						} else {
							preAddr.setDistance(MapUtil.GetDistance(longitude, latitude, preAddr.getLongitude(),
									preAddr.getLatitude()) / 1000.0);
						}
						// 如果距离近，就交换位置
						if (preAddr.getDistance() > lastAddr.getDistance()) {
							tmpList.set(i, preAddr);
							tmpList.set(i - 1, lastAddr);
						}
					}

					for (ActivityListBo activityListBo : activityList) {// 将最近的活动地填充到返回的BO
						ActivityAddressBo activityAddress = tmpList.get(0);
						if (activityListBo.getActivityId() == activityAddress.getActivityId()) {
							activityListBo.setShopId(activityAddress.getShopId());
							activityListBo.setAddress(activityAddress.getAddress());
							activityListBo.setLongitude(activityAddress.getLongitude());
							activityListBo.setLatitude(activityAddress.getLatitude());

							if (tmpList.size() > 1) {
								activityListBo.setDistance(activityAddress.getDistance());
							} else {
								activityListBo.setDistance(MapUtil.GetDistance(longitude, latitude,
										activityAddress.getLongitude(), activityAddress.getLatitude()) / 1000.0);
							}
						}
					}
				}
			}
		}
		ActivityHomeListBo activityHome = ActivityHomeListBo.builder().activityListBo(activityList).build();
		return activityHome;
	}

	@Override
	public BoUtil endActivity(int activityId, long userId, int type, int shopId) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		log.debug("@activityId{},@loginUserId,@type{},@shopId{}", activityId, userId, type, shopId);
		log.debug("********************检测活动是否存在开始********************");
		ActivityBase activityBase = activityBaseDao.getActivityBaseById(activityId);
		log.debug("********************检测活动是否存在结束********************");
		if (activityBase == null) {// 验证活动是否存在
			boUtil.setCode(ErrorCode.ACTIVITY_NOT_FOUND);
			boUtil.setMsg("Activity does not exist");
			return boUtil;
		}
		if (activityBase.getFinishTime().before(new Date())) {// 验证活动是否已经结束
			boUtil.setCode(ErrorCode.ACTIVITY_HAD_END);
			boUtil.setMsg("Activity is over");
			return boUtil;
		}
		if (type == 1) {// 魔线结束活动
			if (activityBase.getOrganizer() != userId) {// 验证用户是否为创建者
				boUtil.setCode(ErrorCode.YOU_ARE_NOT_CREATER);
				boUtil.setMsg("You are not the creator of the event and have no right to modify");
				return boUtil;
			}
		}
		ActivityBase activity = ActivityBase.builder().status((short) 1).id(activityId).updater(userId).build();

		log.debug("********************结束活动开始********************", activity);
		activityBaseDao.updateActivityBase(activity);
		log.debug("********************结束活动结束********************");
		return BoUtil.getDefaultTrueBo();
	}

	@Override
	public List<AllPushActivity> getAllPushActivity(long userId, int pageIndex, int pageSize) {
		List<AllPushActivity> pushActivityList = activityBaseDao.getAllPushActivity(userId, pageIndex, pageSize);

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date time = null;
		try {
			time = format.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (AllPushActivity allPushActivity : pushActivityList) {
			// 0.正在进行 1.活动取消 2.后台屏蔽 3.即将开始 4.成功举办
			if (allPushActivity.getStatus() == 0) {
				if (allPushActivity.getBeginTime().before(date) && allPushActivity.getFinishTime().after(date)) {// 正在进行
					allPushActivity.setStatus(0);
				} else if (allPushActivity.getFinishTime().before(date)) {// 成功举办
					allPushActivity.setStatus(4);
				} else if (allPushActivity.getBeginTime().after(date)
						&& allPushActivity.getFinishTime().after(allPushActivity.getBeginTime())) {// 即将开始
					allPushActivity.setStatus(3);
				}
			} else if (allPushActivity.getStatus() == 1) {
				allPushActivity.setStatus(4);
			}
			allPushActivity.setCurrentTime(time);
		}
		return pushActivityList;
	}

	@Override
	public List<AllJoinActivity> getAllJoinActivity(long userId, int pageIndex, int pageSize) {

		log.debug("********************获取我参加的活动开始********************");
		List<AllJoinActivity> activityList = activityBaseDao.getAllJoinActivity(userId, pageIndex, pageSize);
		log.debug("********************获取我参加的活动结束********************");
		int[] activityIds = new int[activityList.size()];
		for (int i = 0; i < activityList.size(); i++) {
			activityIds[i] = activityList.get(i).getActivityId();
		}

		if (activityIds.length > 0) {
			log.debug("********************批量查询活动统计信息开始********************");
			List<AllJoinActivity> activityCountList = activityCountDao.getActivityCountByIds(activityIds);
			log.debug("********************批量查询活动统计信息结束********************");

			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date time = null;
			try {
				time = format.parse(format.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (AllJoinActivity joinActivity : activityList) {
				for (AllJoinActivity activityCount : activityCountList) {
					if (joinActivity.getActivityId() == activityCount.getActivityId()) {
						joinActivity.setViewCount(activityCount.getViewCount());
						joinActivity.setJoinCount(activityCount.getJoinCount());
					}
				}
				// 0.正在进行 1.活动取消 2.后台屏蔽 3.即将开始 4.成功举办
				if (joinActivity.getStatus() == 0) {
					if (joinActivity.getBeginTime().before(date) && joinActivity.getFinishTime().after(date)) {// 正在进行
						joinActivity.setStatus(0);
					} else if (joinActivity.getFinishTime().before(date)) {// 成功举办
						joinActivity.setStatus(4);
					} else if (joinActivity.getBeginTime().after(date)
							&& joinActivity.getFinishTime().after(joinActivity.getBeginTime())) {// 即将开始
						joinActivity.setStatus(3);
					}
				} else if (joinActivity.getStatus() == 1) {
					joinActivity.setStatus(4);
				}
				joinActivity.setCurrentTime(time);
			}
		}
		return activityList;
	}

	@Override
	public HotActivityBo getHotActivity(int cityId, int begin, int end, double longitude, double latitude,
			long loginUserId) {

		List<HotActivityListBo> hotActivityList = Lists.newArrayList();
		if (0 == cityId || 0.0 == latitude || 0.0 == longitude) {// 手机端没定位到时
			log.debug("*************************获取人气最高的活动开始**************************");
			List<HotActivityListBo> popularActivityList = activityBaseDao.getPopularityActivity();
			log.debug("*************************获取人气最高的活动结束**************************");
			hotActivityList = popularActivityList;
		} else {
			log.debug("*************************获取城市热门活动开始**************************");
			List<HotActivityListBo> hotActiList = activityBaseDao.getHotActivity(cityId, begin, end);
			log.debug("*************************获取城市热门活动结束**************************");

			if (null != hotActiList && !hotActiList.isEmpty()) {
				hotActivityList = hotActiList;
				for (HotActivityListBo hotActivityListBo : hotActivityList) {
					int activityId = hotActivityListBo.getActivityId();
					String join = redisTemplate.opsForValue().get("activityId:" + activityId);
					if (!StringUtils.isBlank(join)) {
						hotActivityListBo.setJoinNumber(Integer.parseInt(join));
					}

					log.debug("********************获取用户是否报名活动关系开始********************");
					int joinResult = activityJoinDao.checkUserSignUp(activityId, loginUserId);
					log.debug("********************获取用户是否报名活动关系结束********************");
					hotActivityListBo.setJoin(joinResult > 0 ? true : false);

					log.debug("********************批量获取活动地点开始********************");
					List<ActivityAddressBo> addressList = activityAddressDao.getActivityAddrById(activityId);
					log.debug("********************批量获取活动地点结束********************", addressList);
					if (addressList.size() > 1) {
						for (int i = addressList.size() - 1; i > 0; i--) {
							ActivityAddressBo lastAddr = addressList.get(i);
							ActivityAddressBo preAddr = addressList.get(i - 1);
							// 计算距离
							if (i == addressList.size() - 1) {
								lastAddr.setDistance(MapUtil.GetDistance(longitude, latitude, lastAddr.getLongitude(),
										lastAddr.getLatitude()) / 1000.0);
								preAddr.setDistance(MapUtil.GetDistance(longitude, latitude, preAddr.getLongitude(),
										preAddr.getLatitude()) / 1000.0);
							} else {
								preAddr.setDistance(MapUtil.GetDistance(longitude, latitude, preAddr.getLongitude(),
										preAddr.getLatitude()) / 1000.0);
							}
							// 如果距离近，就交换位置
							if (preAddr.getDistance() > lastAddr.getDistance()) {
								addressList.set(i, preAddr);
								addressList.set(i - 1, lastAddr);
							}
						}
					}
					ActivityAddressBo activityAddressBo = addressList.get(0);
					hotActivityListBo.setAddress(activityAddressBo.getAddress());
					hotActivityListBo.setShopId(activityAddressBo.getShopId());
					hotActivityListBo.setDistance(MapUtil.GetDistance(longitude, latitude,
							activityAddressBo.getLongitude(), activityAddressBo.getLatitude()) / 1000.0);
				}
			}
		}
		log.debug("*************************获取城市活动总量开始**************************");
		int count = activityBaseDao.getActivityCount(cityId);
		log.debug("*************************获取城市活动总量结束**************************");

		// 去除重复的活动，不改变排列顺序
		List<HotActivityListBo> activity = Lists.newArrayList();
		Set<Integer> set = new HashSet<Integer>();
		for (Iterator<HotActivityListBo> iter = hotActivityList.iterator(); iter.hasNext();) {
			HotActivityListBo element = (HotActivityListBo) iter.next();
			if (set.add(element.getActivityId())) {
				activity.add(element);
			}
		}
		set.clear();

		HotActivityBo hotActivity = HotActivityBo.builder().hotActivityList(activity).count(count).build();
		return hotActivity;
	}

	@Override
	public ActivityInfoBo getActivityById(int activityId) {
		log.info("activityId : {}", activityId);
		ActivityInfoBo activityInfoBo = activityBaseDao.getActivityById(activityId);
		if (activityInfoBo != null) {
			// 获取已报名人数
			String joinNumber = redisTemplate.opsForValue().get("activityId:" + activityId);
			if (!StringUtils.isBlank(joinNumber)) {
				activityInfoBo.setJoinNumber(Integer.parseInt(joinNumber));
			}
			long creater = activityInfoBo.getCreateBy();
			if (activityInfoBo.getActivityOrganizerType() == 1 || activityInfoBo.getActivityOrganizerType() == 3) {
				UserProfile userProfile = accountClient.getUserProfileByUserId(creater);
				if (userProfile != null) {
					activityInfoBo.setCreateLogo(userProfile.getAvatar());
					activityInfoBo.setCreateName(userProfile.getNickName());
				}
			} else {
				// 魔商活动，查询创建店铺信息 TODO
			}
		}
		return activityInfoBo;
	}
}
