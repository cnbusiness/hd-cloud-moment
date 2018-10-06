package com.hd.cloud.dao.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hd.cloud.bo.ActivityReport;

/**
 * 
 * @ClassName: ActivityReportMapper
 * @Description: 活动举报
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:01:57
 *
 */
@Mapper
public interface ActivityReportMapper {

	/**
	 * @Title: ReportActivity
	 * @Description: 举报活动
	 * @Table party_activity_report_br 活动举报表
	 */
	@Insert("insert into party_activity_report_br "
			+ " (user_user_base_sb_seq,party_activity_base_bd_seq,activity_report_content,activity_report_type,activity_report_source_itype,"
			+ "activity_report_deal_itype,activity_report_agree_itype,activity_report_feedback,pub_report_do_bd_seq,activity_report_internal_code,"
			+ "create_by,create_time,update_by,update_time,active_flag) values (#{userId},#{activityId},#{reportContent},#{reportTypeIds},"
			+ "#{sourceType},#{dealType},#{agreeType},#{feedBack},#{reportDoId},#{innerCode},#{creater},now(),#{updater},now(),'y')")
	void reportActivity(ActivityReport activityExpose);

	/**
	 * @Title: getActivityReportNumber
	 * @Description: 获取活动举报次数
	 * @Table party_activity_report_br 活动举报表
	 */
	@Select("SELECT count(1) FROM party_activity_report_br WHERE active_flag = 'y' AND party_activity_base_bd_seq=#{activityId} "
			+ " AND create_time between DATE_FORMAT(date_add(now(), INTERVAL - 30 DAY),'%Y-%m-%d') AND now() "
			+ " group by floor(DATEDIFF(NOW(), #{activityCreatTime})/30)")
	int getActivityReportNumber(@Param("activityId") int activityId,
			@Param("activityCreatTime") Date activityCreatTime);

	/**
	 * @Title: checkActivityReport
	 * @Description: 检测该用户是否已举报该活动
	 * @Table party_activity_report_br 活动举报表
	 */
	@Select("select count(1) from party_activity_report_br where active_flag='y' and party_activity_base_bd_seq=#{activityId} and user_user_base_sb_seq=#{userId}")
	int checkActivityReport(@Param("activityId") int activityId, @Param("userId") long userId);
}
