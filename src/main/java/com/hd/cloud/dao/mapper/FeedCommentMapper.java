package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.dao.sql.FeedCommentProvider;
import com.hd.cloud.vo.FeedCommentVo;

@Mapper
public interface FeedCommentMapper {

	/**
	 * 
	 * @Title: getFeedCommentlist
	 * @Description: 查询评论列表
	 * @Table 2.3.3feed_comment_info_br（评论信息表）
	 */
	@SelectProvider(type = FeedCommentProvider.class, method = "getFeedCommentlist")
	@Results(value = {
			@Result(property = "id", column = "feed_comment_info_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "comment", column = "comment_info_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "commentUserId", column = "comment_info_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "status", column = "comment_info_status_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP)
	})
	List<FeedComment> getFeedCommentlist(FeedCommentVo feedCommentVo);

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.3feed_comment_info_br（评论信息表）
	 */
	@InsertProvider(type = FeedCommentProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FeedComment comment);

	/**
	 * 
	 * @Title: update
	 * @Description: 保存
	 * @Table 2.3.3feed_comment_info_br（评论信息表）
	 */
	@UpdateProvider(type = FeedCommentProvider.class, method = "update")
	int update(FeedComment feedComment);
}
