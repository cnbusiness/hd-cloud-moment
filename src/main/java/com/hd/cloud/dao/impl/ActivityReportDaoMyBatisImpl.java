package com.hd.cloud.dao.impl;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityReport;
import com.hd.cloud.dao.ActivityReportDao;
import com.hd.cloud.dao.mapper.ActivityReportMapper;

/**
 * 
 * @ClassName: ActivityReportDaoMyBatisImpl
 * @Description: 活动举报
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:02:43
 *
 */
@Repository
public class ActivityReportDaoMyBatisImpl implements ActivityReportDao {

	@Inject
	private ActivityReportMapper activityReportMapper;

	@Override
	public void reportActivity(ActivityReport activityExpose) {
		activityReportMapper.reportActivity(activityExpose);
	}

	@Override
	public int getActivityReportNumber(int activityId, Date activityCreatTime) {
		return activityReportMapper.getActivityReportNumber(activityId, activityCreatTime);
	}

	@Override
	public int checkActivityReport(int activityId, long userId) {
		return activityReportMapper.checkActivityReport(activityId, userId);
	}

}
