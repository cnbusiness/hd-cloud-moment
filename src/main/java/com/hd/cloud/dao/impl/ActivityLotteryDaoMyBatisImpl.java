package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityLottery;
import com.hd.cloud.dao.ActivityLotteryDao;
import com.hd.cloud.dao.mapper.ActivityLotteryMapper;

/**
 * 
 * @ClassName: ActivityLotteryDaoMyBatisImpl
 * @Description: 活动抽奖
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:02:36
 *
 */
@Repository
public class ActivityLotteryDaoMyBatisImpl implements ActivityLotteryDao {

	@Inject
	private ActivityLotteryMapper activityLotterMapper;

	@Override
	public List<ActivityLottery> getActivityLotteryById(int activityId, int pageIndex, int pageSize, int lotteryType) {
		return activityLotterMapper.getActivityLotteryById(activityId, pageIndex, pageSize, lotteryType);
	}

	@Override
	public int getActivityLotteryCount(int activityId, int lotteryType) {
		return activityLotterMapper.getActivityLotteryCount(activityId, lotteryType);
	}

	@Override
	public List<Long> getActivityLotteryUser(int activityId, int pageIndex, int pageSize) {
		return activityLotterMapper.getActivityLotteryUser(activityId, pageIndex, pageSize);
	}

	@Override
	public void addLotteryUser(ActivityLottery activityLottery) {
		activityLotterMapper.addLotteryUser(activityLottery);
	}

	@Override
	public int checkActivityLotteryUser(int activityId, long userId) {
		return activityLotterMapper.checkActivityLotteryUser(activityId, userId);
	}

}
