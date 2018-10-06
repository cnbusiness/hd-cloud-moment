package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.ActivityListBo;
import com.hd.cloud.bo.ActivityManageBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.HotActivityListBo;

/**
 * 
 * @ClassName: ActivityBaseDao
 * @Description: 活动基础信息管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:28:25
 *
 */
public interface ActivityBaseDao {

	/**
	 * 
	 * @Title: addActivity
	 * @param: ActivityBase
	 *             activityBase
	 * @Description: 新增活动基础信息
	 * @return
	 */
	void addActivity(ActivityBase activityBase);

	/**
	 * 
	 * @Title: getActivityBaseById
	 * @param: int
	 *             activityId
	 * @Description: 根据活动Id获取活动基础信息
	 * @return ActivityBase
	 */
	ActivityBase getActivityBaseById(int activityId);

	/**
	 * 
	 * @Title: checkActivityRequire
	 * @param: int
	 *             activityId
	 * @Description: 检测活动报名是否需要填写手机号码
	 * @return Integer
	 */
	Integer checkActivityRequire(int activityId);

	/**
	 * 
	 * @Title: getActivityList
	 * @param: int
	 *             activityId
	 * @Description: 获取活动首页列表
	 * @return List<ActivityListBo>
	 */
	List<ActivityListBo> getActivityList(int pageIndex, int pageSize, int activityTypeId, int activityTime, int type,
			int cityId);

	/**
	 * 
	 * @Title: getActivity
	 * @param: int
	 *             activityId,int appType,int shopId
	 * @Description: 获取活动首页列表(mongo定位)
	 * @return List<ActivityListBo>
	 */
	ActivityListBo getActivity(int activityId, int appType, int shopId);

	/**
	 * 
	 * @Title: updateActivityBase
	 * @param: ActivityBase
	 *             activityBase
	 * @Description: 修改活动
	 * @return
	 */
	void updateActivityBase(ActivityBase activityBase);

	/**
	 * 
	 * @Title: getAllPushActivity
	 * @param: long
	 *             userId
	 * @Description: 获取我发布的所有活动
	 * @return List<AllPushActivity>
	 */
	List<AllPushActivity> getAllPushActivity(long userId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getAllJoinActivity
	 * @param: long
	 *             userId
	 * @Description: 获取我参加的所有活动
	 * @return List<AllJoinActivity>
	 */
	List<AllJoinActivity> getAllJoinActivity(long userId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getActivityManageDoing
	 * @param: int
	 *             shopId,int pageIndex,int pageSize
	 * @Description: 活动管理，获取正在进行的活动
	 * @return List<ActivityManageBo>
	 */
	List<ActivityManageBo> getActivityManageDoing(int shopId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getActivityManageEnding
	 * @param: int
	 *             shopId,int pageIndex,int pageSize
	 * @Description: 活动管理，获取已经结束的活动
	 * @return List<ActivityManageBo>
	 */
	List<ActivityManageBo> getActivityManageEnding(int shopId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getActivityCount
	 * @param: int
	 *             cityId
	 * @Description: 获取城市未结束活动的总量
	 * @return int
	 */
	int getActivityCount(int cityId);

	/**
	 * 
	 * @Title: getHotActivity
	 * @param: int
	 *             cityId,int begin,int end
	 * @Description: 获取城市热门活动
	 * @return int
	 */
	List<HotActivityListBo> getHotActivity(int cityId, int begin, int end);

	/**
	 * 
	 * @Title: getAllActivitiesByShopIds
	 * @param: List<Long>
	 *             shopIdList,int authType
	 * @Description: 获取店铺下所有未结束活动
	 * @return List<Long>
	 */
	List<Long> getAllActivitiesByShopIds(List<Long> shopIdList, int authType);

	/**
	 * 
	 * @Title: updateQualificationById
	 * @param: int
	 *             activityId,int authType
	 * @Description: 更改资质认证状态
	 * @return void
	 */
	void updateQualificationById(int activityId, int authType);

	/**
	 * 
	 * @Title: getPopularityActivity
	 * @param:
	 * @Description: 获取人气最高的活动
	 * @return List<HotActivityListBo>
	 */
	List<HotActivityListBo> getPopularityActivity();

	/**
	 * 
	 * @Title: updateShopScreeningById
	 * @param: int
	 *             activityId,int screening
	 * @Description: 更改店铺屏蔽状态
	 * @return void
	 */
	void updateShopScreeningById(int activityId, int screening);

	/**
	 * 获取活动信息
	 * 
	 * @param activityId
	 * @return
	 */
	ActivityInfoBo getActivityById(int activityId);
}