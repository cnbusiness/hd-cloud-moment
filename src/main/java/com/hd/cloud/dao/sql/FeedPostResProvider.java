package com.hd.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FeedPostRes;

public class FeedPostResProvider {

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return String
	 */
	public String save(FeedPostRes feedPostRes) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_post_resource_br");
				VALUES("post_resource_path", "#{path}");
				VALUES("feed_post_base_bb_seq", "#{postId}");
				VALUES("post_resource_avatar", "#{isAvatar}");
				VALUES("post_base_mx_seq", "#{createBy}");

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
	public String update(FeedPostRes feedPostRes) {
		String sql = new SQL() {
			{
				UPDATE("feed_post_resource_br");

				if (StringUtils.isNotBlank(feedPostRes.getActiveFlag())) {
					SET("active_flag=#{activeFlag} ");
				}

				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("feed_post_resource_br_seq = #{id} ");
			}
		}.toString();
		return sql;
	}
}
