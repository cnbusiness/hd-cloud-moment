package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityJoin;
import com.hd.cloud.bo.ActivityJoinBo;

/**
 * 
 * @ClassName: ActivityJoinMapper
 * @Description: 活动参加人数
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:59:24
 *
 */
@Mapper
public interface ActivityJoinMapper {

	/**
	 * @Title: addActivityJoin
	 * @Description: 新增活动参加人
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Insert("insert into party_activity_join_br "
			+ " (party_activity_base_bd_seq,user_user_base_sb_seq,activity_join_phone,activity_join_alais_name,activity_join_present_itype,create_by,create_time,update_by,update_time,active_flag) "
			+ " values(#{activityId},#{userId},#{phone},#{alaisName},#{personType},#{creater},now(),#{updater},now(),'y')")
	@SelectKey(keyProperty = "id", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void addActivityJoin(ActivityJoin activityJoin);

	/**
	 * @Title: updateActivityJoin
	 * @Description: 报名/取消报名
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Update("update party_activity_join_br set activity_join_phone=#{phone},activity_join_alais_name=#{alaisName},activity_join_present_itype=#{personType},active_flag=#{activeFlag},update_time=now()"
			+ " where party_activity_base_bd_seq=#{activityId} and user_user_base_sb_seq=#{userId}")
	void updateActivityJoin(ActivityJoin activityJoin);

	/**
	 * @Title: getActivityJoinById
	 * @Description: 获取报名表详情
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Select("select * from party_activity_join_br where party_activity_base_bd_seq=#{activityId} and user_user_base_sb_seq=#{userId} and active_flag='y'")
	@Results(value = {
			@Result(property = "id", column = "party_activity_join_br_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "phone", column = "activity_join_phone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "alaisName", column = "activity_join_alais_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "personType", column = "activity_join_present_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	ActivityJoin getActivityJoinById(@Param("activityId") int activityId, @Param("userId") long userId);

	/**
	 * @Title: checkUserSignUp
	 * @Description: 验证用户是否报名该活动
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Select("select COUNT(1) from party_activity_join_br where party_activity_base_bd_seq=#{activityId} and user_user_base_sb_seq=#{userId} "
			+ " and activity_join_present_itype=1 and active_flag='y'")
	int checkUserSignUp(@Param("activityId") int activityId, @Param("userId") long userId);

	/**
	 * @Title: getActivityJoinList
	 * @Description: 获取活动已报名人数列表
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Select("SELECT	party_activity_base_bd_seq,user_user_base_sb_seq,activity_join_phone,activity_join_alais_name,update_time FROM "
			+ " party_activity_join_br WHERE party_activity_base_bd_seq = #{activityId} AND activity_join_present_itype = 1 AND active_flag = 'y' "
			+ " order by update_time DESC limit #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "phone", column = "activity_join_phone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "explain", column = "activity_join_alais_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<ActivityJoinBo> getActivityJoinList(@Param("activityId") int activityId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: getActivityJoinUserList
	 * @Description: 获取活动已报名人Id
	 * @Table party_activity_join_br 活动参加人信息表
	 */
	@Select("SELECT	user_user_base_sb_seq FROM party_activity_join_br WHERE party_activity_base_bd_seq = #{activityId} "
			+ "AND activity_join_present_itype = 1 AND active_flag = 'y'")
	List<Long> getActivityJoinUserList(@Param("activityId") int activityId);
}
