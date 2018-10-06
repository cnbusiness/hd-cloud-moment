package com.hd.cloud.dao.mapper;
/**
 * 
  * @ClassName: FeedPostBaseMapper
  * @Description: 动态
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月9日 下午12:08:49
  *
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.dao.sql.FeedPostBaseProvider;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.TopicSectionPostListVo;

@Mapper
public interface FeedPostBaseMapper {

	/**
	 * 
	 * @Title: getFeedPostBaseById
	 * @Description: 查询动态详情
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@SelectProvider(type = FeedPostBaseProvider.class, method = "getFeedPostBaseById")
	@Results(value = {
			@Result(property = "id", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "type", column = "feed_post_entity_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicId", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "content", column = "post_base_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "forwardPostId", column = "post_base_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "title", column = "post_base_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recommend", column = "post_base_recommend", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "longitude", column = "post_base_longitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "latitude", column = "post_base_latitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "area", column = "post_base_phone_area", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "cityId", column = "pub_city_dict_sd_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "visibleType", column = "post_base_types", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "visibleFriends", column = "post_base_friends", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "visibleGroups", column = "post_base_groups", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userType", column = "post_user_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isComment", column = "post_comment_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "companyId", column = "biz_mobiz_company_bd_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "shopId", column = "biz_mobiz_shop_bd_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "shopStatus", column = "mobiz_shop_status_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "address", column = "post_base_address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "appType", column = "post_base_app_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "picUrl", column = "post_base_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "post_base_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	FeedPostBase getFeedPostBaseById(@Param("id") int id);

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@InsertProvider(type = FeedPostBaseProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FeedPostBase feedPostBase);

	/**
	 * 
	 * @Title: update
	 * @Description: 编辑
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@UpdateProvider(type = FeedPostBaseProvider.class, method = "update")
	int update(FeedPostBase feedPostBase);

	/**
	 * 
	 * @Title: getFeedPostBaseList
	 * @Description: 查询动态列表
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@SelectProvider(type = FeedPostBaseProvider.class, method = "getFeedPostBaseList")
	@Results(value = {
			@Result(property = "id", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "type", column = "feed_post_entity_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicId", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "content", column = "post_base_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "forwardPostId", column = "post_base_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicName", column = "post_base_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recommend", column = "post_base_recommend", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "longitude", column = "post_base_longitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "latitude", column = "post_base_latitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "visibleType", column = "post_base_types", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "visibleFriends", column = "post_base_friends", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "visibleGroups", column = "post_base_groups", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address", column = "post_base_address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "post_base_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	List<FeedPostBaseBo> getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo);

	/**
	 * 
	 * @Title: getFeedPostBaseListByIds
	 * @Description: 通过ids批量查询动态信息
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@Select("<script> SELECT feed_post_base_bb_seq,feed_post_entity_itype,topic_topic_section_bd_seq,post_base_content,post_base_seq,post_base_title,post_base_recommend,post_base_longitude,post_base_latitude,post_base_phone_area,pub_city_dict_sd_seq,post_base_types,post_base_friends,post_base_groups,post_user_itype,post_comment_itype,biz_mobiz_company_bd_seq,biz_mobiz_shop_bd_seq,mobiz_shop_status_itype,post_base_address,post_base_favorite_cnt,post_base_review_cnt,post_base_browse_cnt,post_base_store_cnt,post_base_share_cnt,user_user_base_sb_seq,post_base_app_itype,post_base_pic_url,post_base_display_itype,post_base_mask_reason,post_base_order,create_time "
			+ " FROM feed_post_base_bb WHERE active_flag='y' <if test='ids!=null'>  AND  feed_post_base_bb_seq in <foreach item='item' collection='ids' open ='(' separator=',' close=')'>#{item}</foreach></if> order by create_time desc  </script>  ")
	@Results(value = {
			@Result(property = "id", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "type", column = "feed_post_entity_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicId", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "content", column = "post_base_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "forwardPostId", column = "post_base_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicName", column = "post_base_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recommend", column = "post_base_recommend", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "longitude", column = "post_base_longitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "latitude", column = "post_base_latitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "visibleType", column = "post_base_types", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "visibleFriends", column = "post_base_friends", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "visibleGroups", column = "post_base_groups", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address", column = "post_base_address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "post_base_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	List<FeedPostBaseBo> getFeedPostBaseListByIds(@Param("ids") List<Integer> ids);

	/**
	 * 
	 * @Title: getgetFewDynamicByTopicId
	 * @Description: 通过话题id批量查询动态信息
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@Select(" SELECT post_base_content,feed_post_base_bb_seq,date_format(c.create_time,'%Y-%m-%d %H:%I:%S') AS createTime,user_user_base_sb_seq FROM feed_post_base_bb c"
			+ " WHERE active_flag='y' AND topic_topic_section_bd_seq=#{topicId} AND post_base_display_itype=1 AND (post_base_seq  is NULL OR c.post_base_seq=0) AND feed_post_entity_itype = 2 ORDER BY create_time DESC LIMIT 0,5")
	@Results(value = {
			@Result(property = "dynamicId", column = "feed_post_base_bb_seq", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "dynamicContent", column = "post_base_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = long.class, jdbcType = JdbcType.INTEGER) })
	public List<Map<String, Object>> getgetFewDynamicByTopicId(@Param("topicId") int topicId);

	/**
	 * 
	 * @Title: getFeedPostBaseListByTopicSectionPostListVo
	 * @Description: 根据话题id查询动态列表
	 * @Table 2.3.1feed_post_base_bb（动态信息表）
	 */
	@SelectProvider(type = FeedPostBaseProvider.class, method = "getFeedPostBaseListByTopicSectionPostListVo")
	@Results(value = {
			@Result(property = "id", column = "feed_post_base_bb_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "type", column = "feed_post_entity_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicId", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "content", column = "post_base_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "forwardPostId", column = "post_base_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "topicName", column = "post_base_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recommend", column = "post_base_recommend", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "longitude", column = "post_base_longitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "latitude", column = "post_base_latitude", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "visibleType", column = "post_base_types", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "visibleFriends", column = "post_base_friends", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "visibleGroups", column = "post_base_groups", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address", column = "post_base_address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "post_base_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
	List<FeedPostBaseBo> getFeedPostBaseListByTopicSectionPostListVo(TopicSectionPostListVo topicSectionPostListVo);
}
