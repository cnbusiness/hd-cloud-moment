package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityComment;
import com.hd.cloud.dao.sql.ActivityCommentSqlProvider;

/**
 * 
 * @ClassName: ActivityCommentMapper
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:56:43
 *
 */
@Mapper
public interface ActivityCommentMapper {

	/**
	 * @Title: addActivityComment
	 * @Description: 新增活动评论
	 * @Table party_activity_comment_bt 活动评论表
	 */
	@Insert("insert into party_activity_comment_bt "
			+ " (party_activity_base_bd_seq,user_user_entity_seq,activity_comment_app_itype,activity_comment_parent_seq,activity_comment_content,activity_comment_itype,activity_comment_urls,activity_comment_status_itype,create_by,create_time,update_by,update_time,active_flag) "
			+ " values (#{activityId},#{commenter},#{appType},#{parentId},#{content},#{commentType},#{commentUrls},#{commentStatus},#{creater},now(),#{updater},now(),'y')")
	@SelectKey(keyProperty = "id", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void addActivityComment(ActivityComment activityComment);

	/**
	 * @Title: getAllComments
	 * @Description: 获取活动评论列表
	 * @Table party_activity_comment_bt 活动评论表
	 */
	@SelectProvider(type = ActivityCommentSqlProvider.class, method = "getCommentList")
	@Results(value = {
			@Result(property = "id", column = "party_activity_comment_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "commenter", column = "user_user_entity_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "parentId", column = "activity_comment_parent_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "content", column = "activity_comment_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "commentType", column = "activity_comment_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "commentUrls", column = "activity_comment_urls", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "appType", column = "activity_comment_app_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	List<ActivityComment> getAllComments(@Param("type") int type, @Param("activityId") int activityId,
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
