package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityLottery;

/**
 * 
 * @ClassName: ActivityLotteryDao
 * @Description: 活动抽奖
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:00:39
 *
 */
public interface ActivityLotteryDao {

	/**
	 * 
	 * @Title: getActivityLotteryById
	 * @param: int
	 *             activityId
	 * @Description: 获取活动中奖名单
	 * @return List<ActivityLottery>
	 */
	List<ActivityLottery> getActivityLotteryById(int activityId, int pageIndex, int pageSize, int lotteryType);

	/**
	 * 
	 * @Title: getActivityLotteryCount
	 * @param: int
	 *             activityId
	 * @Description: 获取活动中奖数量
	 * @return int
	 */
	int getActivityLotteryCount(int activityId, int lotteryType);

	/**
	 * 
	 * @Title: getActivityLotteryUser
	 * @param: int
	 *             activityId
	 * @Description: 活动摇一摇中奖的用户
	 * @return List<Long>
	 */
	List<Long> getActivityLotteryUser(int activityId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: addLotteryUser
	 * @param: ActivityLottery
	 *             activityLottery
	 * @Description: 记录中奖用户
	 * @return void
	 */
	void addLotteryUser(ActivityLottery activityLottery);

	/**
	 * 
	 * @Title: checkActivityLotteryUser
	 * @param: int
	 *             activityId,long userId
	 * @Description: 检测该用户是否已中奖该活动
	 * @return int
	 */
	int checkActivityLotteryUser(int activityId, long userId);
}
