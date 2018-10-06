package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityCount;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.vo.ActivityCountUpdateVo;

/**
 * 
 * @ClassName: ActivityCountDao
 * @Description: 活动统计信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:57:36
 *
 */
public interface ActivityCountDao {

	/**
	 * 
	 * @Title: addActivityCount
	 * @param: ActivityCount
	 *             activityCount
	 * @Description: 新增活动统计信息
	 * @return
	 */
	void addActivityCount(ActivityCount activityCount);

	/**
	 * 
	 * @Title: updateActivityCount
	 * @param: ActivityCountUpdateVo
	 *             activityCountUpdateVo
	 * @Description: 修改活动统计数量
	 * @return
	 */
	void updateActivityCount(ActivityCountUpdateVo activityCountUpdateVo);

	/**
	 * 
	 * @Title: getActivityById
	 * @param: int
	 *             activityId
	 * @Description: 获取活动统计数量信息
	 * @return
	 */
	ActivityCount getActivityCountById(int activityId);

	/**
	 * 
	 * @Title: getActivityCountByIds
	 * @param: int[]
	 *             activityIds
	 * @Description: 批量查询活动统计信息
	 * @return List<AllJoinActivity>
	 */
	List<AllJoinActivity> getActivityCountByIds(int[] activityIds);
}
