package com.hd.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.vo.FavoritesVo;

public class FavoritesProvider {

	/**
	 * 
	 * @Title: getFavoritesList
	 * @param:
	 * @Description: 获取收藏列表
	 * @return String
	 */
	public String getFavoritesList(FavoritesVo favoritesVo) {
		String sql = new SQL() {
			{
				SELECT("feed_favorites_info_br_seq,feed_post_base_bb_seq,feed_favorites_tag_br_seqs,user_user_base_sb_seq,create_time");
				FROM("feed_favorites_info_br");
				WHERE(" active_flag='y'  AND  user_user_base_sb_seq=#{userId}");
				ORDER_BY(" feed_favorites_info_br_seq desc limit #{offset},#{pageSize}");
			}
		}.toString();
		return sql;
	}

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return String
	 */
	public String save(FavoritesBo favoritesBo) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_favorites_info_br");
				VALUES("feed_post_base_bb_seq", "#{postId}");
				VALUES("feed_favorites_tag_br_seqs", "#{tags}");
				VALUES("user_user_base_sb_seq", "#{userId}");

				VALUES("create_time", "now()");
				VALUES("create_by", "#{createBy}");
				VALUES("active_flag", "#{activeFlag}");
			}
		}.toString();
		return sql;
	}

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑
	 * @return String
	 */
	public String update(FavoritesBo favoritesBo) {
		String sql = new SQL() {
			{
				UPDATE("feed_favorites_info_br");

				if (StringUtils.isNotBlank(favoritesBo.getActiveFlag())) {
					SET("active_flag=#{activeFlag} ");
				}

				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("feed_favorites_info_br_seq = #{id} ");
			}
		}.toString();
		return sql;
	}
}
