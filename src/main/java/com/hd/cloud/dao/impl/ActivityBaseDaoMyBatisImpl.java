package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.ActivityListBo;
import com.hd.cloud.bo.ActivityManageBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.HotActivityListBo;
import com.hd.cloud.dao.ActivityBaseDao;
import com.hd.cloud.dao.mapper.ActivityBaseMapper;

/**
 * 
 * @ClassName: ActivityBaseDaoMyBatisImpl
 * @Description: 活动基础信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:35:04
 *
 */
@Repository
public class ActivityBaseDaoMyBatisImpl implements ActivityBaseDao {

	@Inject
	private ActivityBaseMapper activityBaseMapper;

	@Override
	public void addActivity(ActivityBase activityBase) {
		activityBaseMapper.addActivity(activityBase);
	}

	@Override
	public ActivityBase getActivityBaseById(int activityId) {
		return activityBaseMapper.getActivityBaseById(activityId);
	}

	@Override
	public Integer checkActivityRequire(int activityId) {
		return activityBaseMapper.checkActivityRequire(activityId);
	}

	@Override
	public List<ActivityListBo> getActivityList(int pageIndex, int pageSize, int activityTypeId, int activityTime,
			int type, int cityId) {
		return activityBaseMapper.getActivityList(pageIndex, pageSize, activityTypeId, activityTime, type, cityId);
	}

	@Override
	public ActivityListBo getActivity(int activityId, int appType, int shopId) {
		return activityBaseMapper.getActivity(activityId, appType, shopId);
	}

	@Override
	public void updateActivityBase(ActivityBase activityBase) {
		activityBaseMapper.updateActivityBase(activityBase);
	}

	@Override
	public List<AllPushActivity> getAllPushActivity(long userId, int pageIndex, int pageSize) {
		return activityBaseMapper.getAllPushActivity(userId, pageIndex, pageSize);
	}

	@Override
	public List<AllJoinActivity> getAllJoinActivity(long userId, int pageIndex, int pageSize) {
		return activityBaseMapper.getAllJoinActivity(userId, pageIndex, pageSize);
	}

	@Override
	public List<ActivityManageBo> getActivityManageDoing(int shopId, int pageIndex, int pageSize) {
		return activityBaseMapper.getActivityManageDoing(shopId, pageIndex, pageSize);
	}

	@Override
	public List<ActivityManageBo> getActivityManageEnding(int shopId, int pageIndex, int pageSize) {
		return activityBaseMapper.getActivityManageEnding(shopId, pageIndex, pageSize);
	}

	@Override
	public int getActivityCount(int cityId) {
		return activityBaseMapper.getActivityCount(cityId);
	}

	@Override
	public List<HotActivityListBo> getHotActivity(int cityId, int begin, int end) {
		return activityBaseMapper.getHotActivity(cityId, begin, end);
	}

	@Override
	public List<Long> getAllActivitiesByShopIds(List<Long> shopIdList, int authType) {
		return activityBaseMapper.getAllActivitiesByShopIds(shopIdList, authType);
	}

	@Override
	public void updateQualificationById(int activityId, int authType) {
		activityBaseMapper.updateQualificationById(activityId, authType);
	}

	@Override
	public List<HotActivityListBo> getPopularityActivity() {
		return activityBaseMapper.getPopularityActivity();
	}

	@Override
	public void updateShopScreeningById(int activityId, int screening) {
		activityBaseMapper.updateShopScreeningById(activityId, screening);
	}

	@Override
	public ActivityInfoBo getActivityById(int activityId) {
		return activityBaseMapper.getActivityById(activityId);
	}
}