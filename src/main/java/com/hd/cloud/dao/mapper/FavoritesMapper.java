package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.dao.sql.FavoritesProvider;
import com.hd.cloud.vo.FavoritesVo;

@Mapper
public interface FavoritesMapper {

	/**
	 * 
	 * @Title: getFavoritesList
	 * @Description: 查询收藏列表
	 * @Table 2.3.7feed_favorites_info_br（用户收藏信息表）
	 */
	@SelectProvider(type = FavoritesProvider.class, method = "getFavoritesList")
	@Results(value = {
			@Result(property = "id", column = "feed_favorites_info_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "tags", column = "feed_favorites_tag_br_seqs", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP) })
	List<FavoritesBo> getFavoritesList(FavoritesVo favoritesVo);
	
	/**
	 * 
	 * @Title: findPostExistsByUser
	 * @Description: 查询用户是否收藏
	 * @Table 2.3.7feed_favorites_info_br（用户收藏信息表）
	 */
	@Select(" SELECT feed_favorites_info_br_seq,feed_post_base_bb_seq,feed_favorites_tag_br_seqs,user_user_base_sb_seq,create_time FROM feed_favorites_info_br WHERE active_flag='y' AND user_user_base_sb_seq=#{userId}  AND  feed_post_base_bb_seq=#{postId} limit 0,1")
	@Results(value = {
			@Result(property = "id", column = "feed_favorites_info_br_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "postId", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "tags", column = "feed_favorites_tag_br_seqs", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP) })
	FavoritesBo findPostExistsByUser(@Param("userId") long userId,@Param("postId") int postId);
	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.7feed_favorites_info_br（用户收藏信息表）
	 */
	@InsertProvider(type = FavoritesProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FavoritesBo favoritesBo);
	
	/**
	 * 
	 * @Title: update
	 * @Description: 编辑
	 * @Table 2.3.7feed_favorites_info_br（用户收藏信息表）
	 */
	@UpdateProvider(type = FavoritesProvider.class, method = "update")
	int update(FavoritesBo favoritesBo);
}
