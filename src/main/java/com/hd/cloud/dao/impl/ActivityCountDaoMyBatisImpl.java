package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityCount;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.dao.ActivityCountDao;
import com.hd.cloud.dao.mapper.ActivityCountMapper;
import com.hd.cloud.vo.ActivityCountUpdateVo;

/**
 * 
 * @ClassName: ActivityCountDaoMyBatisImpl
 * @Description: 活动统计信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:58:30
 *
 */
@Repository
public class ActivityCountDaoMyBatisImpl implements ActivityCountDao {

	@Inject
	private ActivityCountMapper activityCountMapper;

	@Override
	public void addActivityCount(ActivityCount activityCount) {
		activityCountMapper.addActivityCount(activityCount);
	}

	@Override
	public void updateActivityCount(ActivityCountUpdateVo activityCountUpdateVo) {
		activityCountMapper.updateActivityCount(activityCountUpdateVo);
	}

	@Override
	public ActivityCount getActivityCountById(int activityId) {
		return activityCountMapper.getActivityCountById(activityId);
	}

	@Override
	public List<AllJoinActivity> getActivityCountByIds(int[] activityIds) {
		return activityCountMapper.getActivityCountByIds(activityIds);
	}

}
