package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.FeedPostRes;
import com.hd.cloud.dao.sql.FeedPostResProvider;

@Mapper
public interface FeedPostResMapper {

	/**
	 * 
	 * @Title: getFeedPostResById
	 * @Description: 查询动态资源详情
	 * @Table 2.3.4feed_post_resource_br（动态资源信息表）
	 */
	@Select(" SELECT feed_post_resource_br_seq,post_resource_path,feed_post_base_bb_seq,post_resource_avatar FROM feed_post_resource_br where active_flag='y' AND feed_post_resource_br_seq=#{id} ")
	@Results(value = {
			@Result(property = "id", column = "feed_post_resource_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "path", column = "post_resource_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isAvatar", column = "post_resource_avatar", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	FeedPostRes getFeedPostResById(@Param("id") int id);

	/**
	 * 
	 * @Title: getFeedPostResListByPostId
	 * @Description: 根据动态id查询资源列表
	 * @Table 2.3.4feed_post_resource_br（动态资源信息表）
	 */
	@Select(" SELECT feed_post_resource_br_seq,post_resource_path,feed_post_base_bb_seq,post_resource_avatar FROM feed_post_resource_br where active_flag='y' AND feed_post_base_bb_seq=#{postId} ")
	@Results(value = {
			@Result(property = "id", column = "feed_post_resource_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "path", column = "post_resource_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isAvatar", column = "post_resource_avatar", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	List<FeedPostRes> getFeedPostResListByPostId(@Param("postId") int postId);


	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.4feed_post_resource_br（动态资源信息表）
	 */
	@InsertProvider(type = FeedPostResProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FeedPostRes feedPostRes);
	
	
	/**
	 * 
	 * @Title: update
	 * @Description: 编辑
	 * @Table 2.3.4feed_post_resource_br（动态资源信息表）
	 */
	@UpdateProvider(type = FeedPostResProvider.class, method = "update")
	int update(FeedPostRes feedPostRes);
	
	
	/**
	 * 
	 * @Title: getIndexPicture
	 * @Description: 根据动态id查询首页图片
	 * @Table 2.3.4feed_post_resource_br（动态资源信息表）
	 */
	@Select("select post_resource_path FROM feed_post_resource_br WHERE post_resource_avatar=1 AND active_flag='y' AND feed_post_base_bb_seq=#{postId}")
	String getIndexPicture(@Param("postId") int postId);

}
