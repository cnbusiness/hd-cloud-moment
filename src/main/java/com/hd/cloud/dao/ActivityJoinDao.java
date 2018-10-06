package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityJoin;
import com.hd.cloud.bo.ActivityJoinBo;

/**
 * 
 * @ClassName: ActivityJoinDao
 * @Description: 活动参加人数
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:58:54
 *
 */
public interface ActivityJoinDao {

	/**
	 * 
	 * @Title: addActivityJoin
	 * @param: ActivityJoin
	 *             activityJoin
	 * @Description: 新增活动参加人
	 * @return
	 */
	void addActivityJoin(ActivityJoin activityJoin);

	/**
	 * 
	 * @Title: updateActivityJoin
	 * @param: ActivityJoin
	 *             activityJoin
	 * @Description: 报名/取消报名
	 * @return
	 */
	void updateActivityJoin(ActivityJoin activityJoin);

	/**
	 * 
	 * @Title: getActivityJoinById
	 * @param: int
	 *             activityId,long userId
	 * @Description: 获取报名详情
	 * @return ActivityJoin
	 */
	ActivityJoin getActivityJoinById(int activityId, long userId);

	/**
	 * 
	 * @Title: checkUserSignUp
	 * @param: int
	 *             activityId,long userId
	 * @Description: 验证用户是否报名该活动
	 * @return int
	 */
	int checkUserSignUp(int activityId, long userId);

	/**
	 * 
	 * @Title: getActivityJoinList
	 * @param: int
	 *             activityId,int pageIndex,int pageSize
	 * @Description: 获取活动已报名列表
	 * @return List<ActivityJoinBo>
	 */
	List<ActivityJoinBo> getActivityJoinList(int activityId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getActivityJoinUserList
	 * @param: int
	 *             activityId
	 * @Description: 获取活动已报名人Id
	 * @return List<Long>
	 */
	List<Long> getActivityJoinUserList(int activityId);
}
