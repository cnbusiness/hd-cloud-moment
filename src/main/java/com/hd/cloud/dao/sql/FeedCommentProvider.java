package com.hd.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.vo.FeedCommentVo;

/**
 * 
 * @ClassName: FeedCommentProvider
 * @Description: 评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午4:23:21
 *
 */
public class FeedCommentProvider {

	/**
	 * 
	 * @Title: getFeedCommentlist
	 * @param:
	 * @Description: 列表
	 * @return String
	 */
	public String getFeedCommentlist(FeedCommentVo feedCommentVo) {
		String sql = new SQL() {
			{
				SELECT("feed_comment_info_br_seq,comment_info_content,feed_post_base_bb_seq,comment_info_seq,user_user_base_sb_seq,comment_info_status_itype,create_time");
				FROM("feed_comment_info_br");
				WHERE(" active_flag='y'  AND  feed_post_base_bb_seq=#{postId}");
				ORDER_BY(" feed_comment_info_br_seq desc limit #{offset},#{pageSize}");
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
	public String save(FeedComment comment) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_comment_info_br");
				VALUES("comment_info_content", "#{comment}");
				VALUES("feed_post_base_bb_seq", "#{postId}");
				VALUES("comment_info_seq", "#{commentUserId}");
				VALUES("user_user_base_sb_seq", "#{userId}");
				VALUES("comment_info_status_itype", "#{status}");

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
	public String update(FeedComment comment) {
		String sql = new SQL() {
			{
				UPDATE("feed_comment_info_br");

				if (StringUtils.isNotBlank(comment.getActiveFlag())) {
					SET("active_flag=#{activeFlag} ");
				}

				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("feed_comment_info_br_seq = #{id} ");
			}
		}.toString();
		return sql;
	}

}
