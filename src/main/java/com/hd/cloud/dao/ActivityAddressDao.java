package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityAddress;
import com.hd.cloud.bo.ActivityAddressBo;

/**
 * 
 * @ClassName: ActivityAddressDao
 * @Description: 活动地址
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:48:21
 *
 */
public interface ActivityAddressDao {

	/**
	 * 
	 * @Title: addActivityAddress
	 * @param: ActivityAddress
	 *             activityAddress
	 * @Description: 新增活动地址
	 * @return
	 */
	void addActivityAddress(ActivityAddress activityAddress);

	/**
	 * 
	 * @Title: getActivityAddressById
	 * @param: int
	 *             activityId,int shopId
	 * @Description: 获取活动地址信息
	 * @return ActivityAddress
	 */
	ActivityAddress getActivityAddressById(int activityId, int shopId);

	/**
	 * 
	 * @Title: getActivityShopCount
	 * @param: int
	 *             activityId
	 * @Description: 获取活动店铺数量
	 * @return ActivityAddress
	 */
	List<Integer> getActivityShopCount(int activityId);

	/**
	 * 
	 * @Title: getActivityAddress
	 * @param: int[]
	 *             activityId
	 * @Description: 获取活动地点
	 * @return List<ActivityAddress>
	 */
	List<ActivityAddressBo> getActivityAddress(List<Integer> activityIds);

	/**
	 * 
	 * @Title: updateActivityAddress
	 * @param: ActivityAddress
	 *             activityAddress
	 * @Description: 修改活动地点
	 * @return void
	 */
	void updateActivityAddress(ActivityAddress activityAddress);

	/**
	 * 
	 * @Title: getShopActivityCount
	 * @param: int
	 *             shopId
	 * @Description: 获取店铺活动数量
	 * @return int
	 */
	int getShopActivityCount(int shopId);

	/**
	 * 
	 * @Title: getActivityAddressById
	 * @param: activityId
	 * @Description: 获取活动地点
	 * @return List<ActivityAddress>
	 */
	List<ActivityAddressBo> getActivityAddrById(int activityId);

	/**
	 * 
	 * @Title: getActivityByShopId
	 * @param: long
	 *             shopId,int screening
	 * @Description: 获取店铺未结束活动
	 * @return void
	 */
	List<Long> getActivityByShopId(long shopId, int screening);
}
