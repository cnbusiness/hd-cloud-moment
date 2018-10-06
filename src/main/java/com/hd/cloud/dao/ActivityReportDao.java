package com.hd.cloud.dao;

import java.util.Date;

import com.hd.cloud.bo.ActivityReport;

/**
 * 
 * @ClassName: ActivityReportDao
 * @Description: 活动举报
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:01:04
 *
 */
public interface ActivityReportDao {

	/**
	 * 
	 * @Title: ReportActivity
	 * @param: ActivityReport
	 *             activityReport
	 * @Description: 举报活动
	 * @return
	 */
	void reportActivity(ActivityReport activityReport);

	/**
	 * 
	 * @Title: getActivityReportNumber
	 * @param: int
	 *             activityId
	 * @Description: 获取活动举报次数
	 * @return
	 */
	int getActivityReportNumber(int activityId, Date activityCreatTime);

	/**
	 * 
	 * @Title: checkActivityReport
	 * @param: int
	 *             activityId,long userId
	 * @Description: 检测该用户是否举报该活动
	 * @return
	 */
	int checkActivityReport(int activityId, long userId);
}
