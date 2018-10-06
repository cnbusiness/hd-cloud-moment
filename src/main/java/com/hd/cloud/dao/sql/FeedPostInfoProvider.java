package com.hd.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.vo.PostInfoVo;

public class FeedPostInfoProvider {

	/**
	 * 
	 * @Title: getPostInfoList
	 * @param:
	 * @Description: 查询列表
	 * @return String
	 */
	public String getPostInfoList(PostInfoVo postInfoVo) {
		String sql = new SQL() {
			{
				SELECT("feed_post_info_br_seq,post_info_entity_itype,post_info_content,feed_post_base_bb_seq,post_info_avatar,post_info_type,user_user_base_sb_seq,user_base_seq_from,post_info_read_itype,post_info_short_text,create_time");
				FROM("feed_post_info_br");
				WHERE(" active_flag='y' ");
				if (postInfoVo.getUserId() > 0) {
					AND();
					WHERE(" user_user_base_sb_seq=#{userId} ");
				}
				if (postInfoVo.getPostId() > 0) {
					AND();
					WHERE(" feed_post_base_bb_seq=#{postId} ");
				}
				if(postInfoVo.getStatus()>0) {
					AND();
					WHERE(" post_info_read_itype=#{status} ");
				}
				
				if(postInfoVo.getInfoType()>0) {
					AND();
					WHERE(" post_info_type=#{infoType} ");
				}
				ORDER_BY(" feed_post_info_br_seq desc limit #{offset},#{pageSize}");
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
	public String save(FeedPostInfo feedPostInfo) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_post_info_br");
				VALUES("post_info_entity_itype", "#{type}");
				VALUES("post_info_content", "#{content}");
				VALUES("feed_post_base_bb_seq", "#{postId}");
				VALUES("post_info_avatar", "#{avatar}");
				VALUES("post_info_type", "#{infoType}");
				VALUES("user_user_base_sb_seq", "#{userId}");
				VALUES("user_base_seq_from", "#{fromUserId}");
				VALUES("post_info_read_itype", "#{status}");
				VALUES("post_info_short_text", "#{shortText}");

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
	public String update(FeedPostInfo feedPostInfo) {
		String sql = new SQL() {
			{
				UPDATE("feed_post_info_br");

				if (StringUtils.isNotBlank(feedPostInfo.getActiveFlag())) {
					SET("active_flag=#{activeFlag} ");
				}
				
				if(feedPostInfo.getStatus()>0) {
					SET("post_info_read_itype=#{status} ");
				}

				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("feed_post_info_br_seq = #{id} ");
			}
		}.toString();
		return sql;
	}
}
