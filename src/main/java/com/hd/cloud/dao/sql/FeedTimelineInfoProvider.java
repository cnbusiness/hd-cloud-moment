package com.hd.cloud.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FeedTimelineInfo;

public class FeedTimelineInfoProvider {

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return String
	 */
	public String save(FeedTimelineInfo feedTimelineInfo) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_timeline_info_br");
				VALUES("feed_post_base_bb_seq", "#{postId}");
				VALUES("user_user_base_sb_seq", "#{userId}");

				VALUES("create_time", "now()");
				VALUES("create_by", "#{createBy}");
				VALUES("active_flag", "#{activeFlag}");
			}
		}.toString();
		return sql;
	}
}
