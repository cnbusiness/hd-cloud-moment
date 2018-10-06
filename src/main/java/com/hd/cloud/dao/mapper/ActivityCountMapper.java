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
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityCount;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.dao.sql.ActivityCountSqlProvider;
import com.hd.cloud.vo.ActivityCountUpdateVo;

/**
 * 
 * @ClassName: ActivityCountMapper
 * @Description: 活动统计信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:58:11
 *
 */
@Mapper
public interface ActivityCountMapper {

	/**
	 * @Title: addActivityCount
	 * @Description: 新增活动统计信息
	 * @Table party_activity_count_br 活动统计信息表
	 */
	@Insert("insert into party_activity_count_br "
			+ " (party_activity_base_bd_seq,activity_count_forward_cnt,activity_count_comment_cnt,activity_count_com_pic_cnt,activity_count_favorite_cnt,activity_count_view_cnt,"
			+ " activity_count_share_cnt,activity_count_join_cnt,create_by,create_time,update_by,update_time,active_flag) "
			+ " values (#{activityId},#{forwardCount},#{commentCount},#{commentPicCount},#{favoriteCount},#{viewCount},#{shareCount},#{joinCount},#{creater},now(),#{updater},now(),'y')")
	@SelectKey(keyProperty = "id", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void addActivityCount(ActivityCount activityCount);

	/**
	 * @Title: updateActivityCount
	 * @Description: 修改活动统计信息
	 * @Table party_activity_count_br 活动统计信息表
	 */
	@UpdateProvider(type = ActivityCountSqlProvider.class, method = "update")
	void updateActivityCount(ActivityCountUpdateVo activityCountUpdateVo);

	/**
	 * @Title: getActivityById
	 * @Description: 获取活动统计信息详情
	 * @Table party_activity_count_br 活动统计信息表
	 */
	@Select("select * from party_activity_count_br where party_activity_base_bd_seq=#{activityId} and  active_flag='y'")
	@Results(value = {
			@Result(property = "id", column = "party_activity_count_br _seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "forwardCount", column = "activity_count_forward_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "commentCount", column = "activity_count_comment_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "commentPicCount", column = "activity_count_com_pic_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "favoriteCount", column = "activity_count_favorite_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "viewCount", column = "activity_count_view_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinCount", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "shareCount", column = "activity_count_share_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	ActivityCount getActivityCountById(@Param("activityId") int activityId);

	/**
	 * @Title: getActivityCountByIds
	 * @Description: 批量查询活动统计信息
	 * @Table party_activity_count_br 活动统计信息表
	 */
	@Select("<script> select party_activity_base_bd_seq,activity_count_join_cnt,activity_count_view_cnt from party_activity_count_br where active_flag='y' and party_activity_base_bd_seq "
			+ " in <foreach item='item' collection='activityIds' open ='(' separator=',' close=')'>#{item}</foreach> </script>")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinCount", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "viewCount", column = "activity_count_view_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<AllJoinActivity> getActivityCountByIds(@Param("activityIds") int[] activityIds);
}
