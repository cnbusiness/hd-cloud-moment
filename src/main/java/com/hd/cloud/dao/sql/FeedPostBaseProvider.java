package com.hd.cloud.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.TopicSectionPostListVo;

/**
 * 
 * @ClassName: FeedPostBaseProvider
 * @Description: 动态
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午12:09:14
 *
 */
public class FeedPostBaseProvider {

	/**
	 * 
	 * @Title: getFeedPostBaseById
	 * @param:
	 * @Description: 查询动态信息
	 * @return String
	 */
	public String getFeedPostBaseById(Map<String, Object> map) {
		String sql = new SQL() {
			{
				SELECT("feed_post_base_bb_seq,feed_post_entity_itype,topic_topic_section_bd_seq,post_base_content,post_base_seq,post_base_title,post_base_recommend,post_base_longitude,post_base_latitude,post_base_phone_area,pub_city_dict_sd_seq,post_base_types,post_base_friends,post_base_groups,post_user_itype,post_comment_itype,biz_mobiz_company_bd_seq,biz_mobiz_shop_bd_seq,mobiz_shop_status_itype,post_base_address,post_base_favorite_cnt,post_base_review_cnt,post_base_browse_cnt,post_base_store_cnt,post_base_share_cnt,user_user_base_sb_seq,post_base_app_itype,post_base_pic_url,post_base_display_itype,post_base_mask_reason,post_base_order,create_time");
				FROM("feed_post_base_bb");
				WHERE(" active_flag='y'  AND  feed_post_base_bb_seq=" + map.get("id"));
			}
		}.toString();
		return sql;
	}
	
	
	/**
	 * 
	* @Title: getFeedPostBaseListByTopicSectionPostListVo 
	* @param: 
	* @Description: 根据话题id查询动态列表 
	* @return String
	 */
	public String getFeedPostBaseListByTopicSectionPostListVo(TopicSectionPostListVo topicSectionPostListVo) {
		String sql = new SQL() {
			{
				SELECT("feed_post_base_bb_seq,feed_post_entity_itype,topic_topic_section_bd_seq,post_base_content,post_base_seq,post_base_title,post_base_recommend,post_base_longitude,post_base_latitude,post_base_phone_area,pub_city_dict_sd_seq,post_base_types,post_base_friends,post_base_groups,post_user_itype,post_comment_itype,biz_mobiz_company_bd_seq,biz_mobiz_shop_bd_seq,mobiz_shop_status_itype,post_base_address,post_base_favorite_cnt,post_base_review_cnt,post_base_browse_cnt,post_base_store_cnt,post_base_share_cnt,user_user_base_sb_seq,post_base_app_itype,post_base_pic_url,post_base_display_itype,post_base_mask_reason,post_base_order,create_time");
				FROM("feed_post_base_bb");
				WHERE(" active_flag='y'  AND  topic_topic_section_bd_seq=#{topicId} ");
				//设置排序
				if(topicSectionPostListVo.getSort()==1){
					ORDER_BY("create_time DESC LIMIT #{offset},#{pageSize}");
				}else{
					ORDER_BY("post_base_review_cnt DESC,create_time DESC LIMIT #{offset},#{pageSize}");	
				}
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
	public String save(FeedPostBase feedPostBase) {
		String sql = new SQL() {
			{
				INSERT_INTO("feed_post_base_bb");
				VALUES("feed_post_entity_itype", "#{type}");
				VALUES("topic_topic_section_bd_seq", "#{topicId}");
				VALUES("post_base_content", "#{content}");
				VALUES("post_base_seq", "#{forwardPostId}");
				VALUES("post_base_title", "#{title}");
				VALUES("post_base_recommend", "#{recommend}");
				VALUES("post_base_longitude", "#{latitude}");
				VALUES("post_base_latitude", "#{latitude}");
				VALUES("post_base_phone_area", "#{area}");
				VALUES("pub_city_dict_sd_seq", "#{cityId}");
				VALUES("post_base_types", "#{visibleType}");
				VALUES("post_base_friends", "#{visibleFriends}");
				VALUES("post_base_groups", "#{visibleGroups}");
				VALUES("post_user_itype", "#{userType}");
				VALUES("post_comment_itype", "#{isComment}");
				VALUES("biz_mobiz_company_bd_seq", "#{companyId}");
				VALUES("biz_mobiz_shop_bd_seq", "#{shopId}");
				VALUES("mobiz_shop_status_itype", "#{shopStatus}");
				VALUES("post_base_address", "#{address}");
				VALUES("post_base_favorite_cnt", "#{starCnt}");
				VALUES("post_base_review_cnt", "#{reviewCnt}");
				VALUES("post_base_browse_cnt", "#{viewCount}");
				VALUES("post_base_store_cnt", "#{favoriteCnt}");
				VALUES("post_base_share_cnt", "#{shareCnt}");
				VALUES("user_user_base_sb_seq", "#{userId}");
				VALUES("post_base_app_itype", "#{appType}");
				VALUES("post_base_pic_url", "#{picUrl}");
				VALUES("post_base_display_itype", "#{status}");
				VALUES("post_base_mask_reason", "#{maskReason}");
				VALUES("post_base_order", "#{sort}");

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
	public String update(FeedPostBase feedPostBase) {
		String sql = new SQL() {
			{
				UPDATE("feed_post_base_bb");

				if (StringUtils.isNotBlank(feedPostBase.getActiveFlag())) {
					SET("active_flag=#{activeFlag} ");
				}

				if (feedPostBase.getStarCnt() > 0) {
					SET("post_base_favorite_cnt=#{starCnt} ");
				}

				if (feedPostBase.getReviewCnt() > 0) {
					SET("post_base_review_cnt=#{reviewCnt} ");
				}

				if (feedPostBase.getViewCount() > 0) {
					SET("post_base_browse_cnt=#{viewCount} ");
				}

				if (feedPostBase.getFavoriteCnt() > 0) {
					SET("post_base_store_cnt=#{favoriteCnt} ");
				}

				if (feedPostBase.getShareCnt() > 0) {
					SET("post_base_share_cnt=#{shareCnt} ");
				}
				
				if (StringUtils.isNotBlank(feedPostBase.getPicUrl())) {
					SET("post_base_pic_url=#{picUrl} ");
				}
				
				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("feed_post_base_bb_seq = #{id} ");
			}
		}.toString();
		return sql;
	}

	/**
	 * 
	 * @Title: getFeedPostBaseList
	 * @param:
	 * @Description: 列表
	 * @return String
	 */
	public String getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo) {
		String sql = new SQL() {
			{
				SELECT("feed_post_base_bb_seq,feed_post_entity_itype,topic_topic_section_bd_seq,post_base_content,post_base_seq,post_base_title,post_base_recommend,post_base_longitude,post_base_latitude,post_base_phone_area,pub_city_dict_sd_seq,post_base_types,post_base_friends,post_base_groups,post_user_itype,post_comment_itype,biz_mobiz_company_bd_seq,biz_mobiz_shop_bd_seq,mobiz_shop_status_itype,post_base_address,post_base_favorite_cnt,post_base_review_cnt,post_base_browse_cnt,post_base_store_cnt,post_base_share_cnt,user_user_base_sb_seq,post_base_app_itype,post_base_pic_url,post_base_display_itype,post_base_mask_reason,post_base_order,create_time");
				FROM("feed_post_base_bb");
				WHERE(" active_flag='y'  AND  user_user_base_sb_seq=#{userId}");
				ORDER_BY(" feed_post_base_bb_seq desc limit #{offset},#{pageSize}");
			}
		}.toString();
		return sql;
	}
}
