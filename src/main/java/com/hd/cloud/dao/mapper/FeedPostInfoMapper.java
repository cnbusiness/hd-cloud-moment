package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.PostInfoBo;
import com.hd.cloud.dao.sql.FeedPostInfoProvider;
import com.hd.cloud.vo.PostInfoVo;

@Mapper
public interface FeedPostInfoMapper {

	/**
	 * 
	 * @Title: getPostInfoList
	 * @Description: 查询消息中心列表
	 * @Table 2.3.6feed_post_info_br（消息中心信息表）
	 */
	@SelectProvider(type = FeedPostInfoProvider.class, method = "getPostInfoList")
	@Results(value = {
			@Result(property = "id", column = "feed_post_info_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "type", column = "post_info_entity_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "content", column = "post_info_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "postavatar", column = "post_info_avatar", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "infoType", column = "post_info_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "fromUserId", column = "user_base_seq_from", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "status", column = "post_info_read_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "shortText", column = "post_info_short_text", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP) })
	List<PostInfoBo> getPostInfoList(PostInfoVo postInfoVo);

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.6feed_post_info_br（消息中心信息表）
	 */
	@InsertProvider(type = FeedPostInfoProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FeedPostInfo feedPostInfo);

	/**
	 * 
	 * @Title: update
	 * @Description: 编辑
	 * @Table 2.3.6feed_post_info_br（消息中心信息表）
	 */
	@InsertProvider(type = FeedPostInfoProvider.class, method = "update")
	int update(FeedPostInfo feedPostInfo);
	
	
	/**
	 * 
	 * @Title: deleteByPostId
	 * @Description: 删除动态消息
	 * @Table 2.3.6feed_post_info_br（消息中心信息表）
	 */
	@Update(" UPDATE feed_post_info_br SET active_flag='d' WHERE post_info_entity_itype=1 AND active_flag='y'  AND feed_post_base_bb_seq=#{postId} ")
	int  deleteByPostId(@Param("postId") int postId);
}
