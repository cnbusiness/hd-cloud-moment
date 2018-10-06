package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityJoin;
import com.hd.cloud.bo.ActivityJoinBo;
import com.hd.cloud.dao.ActivityJoinDao;
import com.hd.cloud.dao.mapper.ActivityJoinMapper;

/**
 * 
 * @ClassName: ActivityJoinDaoMyBatisImpl
 * @Description: 活动参加人数
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:59:54
 *
 */
@Repository
public class ActivityJoinDaoMyBatisImpl implements ActivityJoinDao {

	@Inject
	private ActivityJoinMapper activityJoinMapper;

	@Override
	public void addActivityJoin(ActivityJoin activityJoin) {
		activityJoinMapper.addActivityJoin(activityJoin);
	}

	@Override
	public void updateActivityJoin(ActivityJoin activityJoin) {
		activityJoinMapper.updateActivityJoin(activityJoin);
	}

	@Override
	public ActivityJoin getActivityJoinById(int activityId, long userId) {
		return activityJoinMapper.getActivityJoinById(activityId, userId);
	}

	@Override
	public int checkUserSignUp(int activityId, long userId) {
		return activityJoinMapper.checkUserSignUp(activityId, userId);
	}

	@Override
	public List<ActivityJoinBo> getActivityJoinList(int activityId, int pageIndex, int pageSize) {
		return activityJoinMapper.getActivityJoinList(activityId, pageIndex, pageSize);
	}

	@Override
	public List<Long> getActivityJoinUserList(int activityId) {
		return activityJoinMapper.getActivityJoinUserList(activityId);
	}

}
