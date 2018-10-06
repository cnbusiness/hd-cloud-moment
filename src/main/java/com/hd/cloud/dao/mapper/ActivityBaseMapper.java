package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityBase;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.ActivityListBo;
import com.hd.cloud.bo.ActivityManageBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.HotActivityListBo;
import com.hd.cloud.dao.sql.ActivityBaseSqlProvider;

/**
 * 
 * @ClassName: ActivityBaseMapper
 * @Description: 活动基础信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:37:25
 *
 */
@Mapper
public interface ActivityBaseMapper {

	/**
	 * @Title: addActivity
	 * @Description: 新增活动
	 * @Table party_activity_base_bd 活动基础表
	 */
	@Insert("insert into party_activity_base_bd "
			+ " (activity_base_internal_code,party_activity_type_bd_seq,activity_base_topic,activity_base_desc,activity_base_person_cnt,activity_base_bdate,activity_base_edate,activity_base_btime, "
			+ " activity_base_etime,activity_base_qrcode_url,activity_base_contact_itype,activity_base_fee_itype,activity_base_fee,activity_base_app_itype,mobiz_shop_auth_itype,"
			+ " activity_base_organizer,activity_base_phone,activity_base_status_itype,activity_base_cancle_reason,activity_pic_url,activity_base_delay_cnt,pub_city_dict_sd_seq,mobiz_shop_status_itype, "
			+ " activity_pic_urls,user_group_base_br_seq,goods_goods_base_bd_seq,activity_base_notice_itype,activity_base_shake_itype,create_by,create_time,update_by,update_time,active_flag) "
			+ " values (#{innerCode},#{activityTypeId},#{theme},#{detail},#{personNumber},#{startDate},#{endDate},#{startTime},#{endTime},"
			+ " #{qrcode},#{isNeedPhone},#{isFree},#{money},#{appType},#{authType},#{organizer},#{contactPhone},#{status},#{cancleReason},#{logoUrl},#{delayHour},#{cityId},#{shopShield},#{pictureUrls},#{userGroupId},#{couponId},#{isNeedNotice},#{hasShake},#{creater},now(),#{updater},now(),'y')")
	@SelectKey(keyProperty = "id", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void addActivity(ActivityBase activityBase);

	/**
	 * @Title: getActivityById
	 * @Description: 获取活动基础信息
	 * @Table party_activity_base_bd 活动基础表
	 */
	@Select("select *,CONCAT(activity_base_bdate,' ',activity_base_btime) beginTime,"
			+ " CONCAT(activity_base_edate,' ',activity_base_etime) finishTime "
			+ " from party_activity_base_bd where party_activity_base_bd_seq=#{activityId} and active_flag='y'")
	@Results(value = {
			@Result(property = "id", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "innerCode", column = "activity_base_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityTypeId", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "detail", column = "activity_base_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "personNumber", column = "activity_base_person_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "cityId", column = "pub_city_dict_sd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "startDate", column = "activity_base_bdate", javaType = Date.class, jdbcType = JdbcType.DATE),
			@Result(property = "endDate", column = "activity_base_edate", javaType = Date.class, jdbcType = JdbcType.DATE),
			@Result(property = "startTime", column = "activity_base_btime", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "endTime", column = "activity_base_etime", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "qrcode", column = "activity_base_qrcode_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "isNeedPhone", column = "activity_base_contact_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "isFree", column = "activity_base_fee_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "money", column = "activity_base_fee", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "appType", column = "activity_base_app_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "organizer", column = "activity_base_organizer", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "contactPhone", column = "activity_base_phone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "status", column = "activity_base_status_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "cancleReason", column = "activity_base_cancle_reason", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "logoUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "pictureUrls", column = "activity_pic_urls", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "userGroupId", column = "user_group_base_br_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "couponId", column = "goods_goods_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isNeedNotice", column = "activity_base_notice_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "hasShake", column = "activity_base_shake_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "delayHour", column = "activity_base_delay_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "authType", column = "mobiz_shop_auth_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "shopShield", column = "mobiz_shop_status_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	ActivityBase getActivityBaseById(@Param("activityId") int activityId);

	/**
	 * activity_base_delay_cnt
	 * 
	 * @Title: checkActivityRequire
	 * @Description: 检测活动报名是否需要填写手机号码
	 * @Table party_activity_base_bd 活动基础表
	 */
	@Select("select activity_base_contact_itype from party_activity_base_bd where party_activity_base_bd_seq=#{activityId} and active_flag='y'")
	Integer checkActivityRequire(@Param("activityId") int activityId);

	/**
	 * @Title: getActivityList
	 * @Description: 获取活动首页列表
	 * @Table party_activity_base_bd 活动基础表,party_activity_type_bd
	 *        活动类型字典表,party_activity_count_br 活动统计信息表
	 */
	@SelectProvider(type = ActivityBaseSqlProvider.class, method = "getActivityList")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityTypeName", column = "activity_type_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "type", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinNumber", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityTypeId", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<ActivityListBo> getActivityList(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("activityTypeId") int activityTypeId, @Param("activityTime") int activityTime,
			@Param("type") int type, @Param("cityId") int cityId);

	/**
	 * @Title: getActivity
	 * @Description: 获取单个活动信息
	 * @Table party_activity_base_bd 活动基础表,party_activity_address_br
	 *        活动地址表,party_activity_count_br 活动统计信息表
	 */
	@SelectProvider(type = ActivityBaseSqlProvider.class, method = "getActivity")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityTypeId", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "type", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinNumber", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "longitude", column = "activity_address_longitude", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "latitude", column = "activity_address_latitude", javaType = double.class, jdbcType = JdbcType.DECIMAL) })
	ActivityListBo getActivity(@Param("activityId") int activityId, @Param("appType") int appType,
			@Param("shopId") int shopId);

	/**
	 * @Title: updateActivityBase
	 * @Description: 修改活动
	 * @Table party_activity_base_bd 活动基础表
	 */
	@UpdateProvider(type = ActivityBaseSqlProvider.class, method = "update")
	void updateActivityBase(ActivityBase activityBase);

	/**
	 * @Title: getAllPushActivity
	 * @Description: 获取我发布的所有活动
	 * @Table party_activity_base_bd 活动基础表 party_activity_count_br 活动统计表
	 */
	@Select("SELECT a.party_activity_base_bd_seq,a.activity_pic_url,a.activity_base_topic,a.create_time,"
			+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) beginTime,"
			+ " CONCAT(a.activity_base_edate,' ',a.activity_base_etime) finishTime,"
			+ " a.activity_base_status_itype,b.activity_count_join_cnt,b.activity_count_view_cnt "
			+ " FROM party_activity_base_bd a INNER JOIN party_activity_count_br b "
			+ " ON a.party_activity_base_bd_seq=b.party_activity_base_bd_seq AND b.active_flag='y' "
			+ " WHERE a.active_flag='y' AND a.activity_base_app_itype in (1,3) AND a.activity_base_organizer=#{userId} "
			+ " ORDER BY case when a.activity_base_status_itype=0 and NOW() >= CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) "
			+ " AND NOW() <= CONCAT(a.activity_base_edate,' ',a.activity_base_etime) THEN 'a' "
			+ " when a.activity_base_status_itype=0 and NOW() < CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) "
			+ " AND CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) <= CONCAT(a.activity_base_edate,' ',a.activity_base_etime) then 'b' "
			+ " when a.activity_base_status_itype=0 and NOW() > CONCAT(a.activity_base_edate,' ',a.activity_base_etime) then 'c' "
			+ " when a.activity_base_status_itype=1 then 'd' "
			+ " when a.activity_base_status_itype=2 then 'e' end,a.create_time desc limit #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "activity_base_status_itype", javaType = int.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "joinCount", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "viewCount", column = "activity_count_view_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<AllPushActivity> getAllPushActivity(@Param("userId") long userId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: getAllJoinActivity
	 * @Description: 获取我参加的所有活动
	 * @Table party_activity_base_bd 活动基础表 party_activity_address_br 活动地点表
	 *        party_activity_join_br 活动参加人信息表
	 */
	@Select("SELECT b.party_activity_base_bd_seq,b.activity_pic_url,b.activity_base_topic,b.create_time,CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) beginTime,"
			+ " CONCAT(b.activity_base_edate,' ',b.activity_base_etime) finishTime,b.activity_base_status_itype,c.activity_address_detail,b.activity_base_shake_itype,b.activity_base_app_itype "
			+ " FROM party_activity_join_br a INNER JOIN party_activity_base_bd b ON a.party_activity_base_bd_seq=b.party_activity_base_bd_seq "
			+ " AND a.active_flag='y' AND a.activity_join_present_itype=1 AND a.user_user_base_sb_seq=#{userId} "
			+ " INNER JOIN party_activity_address_br c ON c.active_flag='y' AND a.party_activity_base_bd_seq=c.party_activity_base_bd_seq "
			+ " AND b.activity_base_organizer =(CASE WHEN b.activity_base_app_itype in (1,3) THEN b.activity_base_organizer ELSE c.biz_mobiz_shop_bd_seq END ) "
			+ " WHERE b.active_flag='y' "
			+ " ORDER BY case when b.activity_base_status_itype=0 and NOW() >= CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) AND NOW() <= CONCAT(b.activity_base_edate,' ',b.activity_base_etime) THEN 'a' "
			+ " when b.activity_base_status_itype=0 and NOW() < CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) "
			+ " AND CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) <= CONCAT(b.activity_base_edate,' ',b.activity_base_etime) then 'b' "
			+ " when b.activity_base_status_itype=0 and NOW() > CONCAT(b.activity_base_edate,' ',b.activity_base_etime) then 'c' "
			+ " when b.activity_base_status_itype=1 then 'd' "
			+ " when b.activity_base_status_itype=2 then 'e' end,b.create_time desc limit #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "status", column = "activity_base_status_itype", javaType = int.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "hasShake", column = "activity_base_shake_itype", javaType = int.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "type", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.TINYINT) })
	List<AllJoinActivity> getAllJoinActivity(@Param("userId") long userId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: getActivityManageDoing
	 * @Description: 活动管理，获取店铺正在进行的活动
	 * @Table party_activity_count_br 活动统计信息表 party_activity_base_bd 活动基础表
	 */
	@Select("SELECT a.party_activity_base_bd_seq,a.activity_pic_url,a.activity_base_topic,a.create_time,"
			+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) beginTime,CONCAT(a.activity_base_edate,' ',a.activity_base_etime) finishTime,"
			+ " a.activity_base_status_itype,a.activity_base_delay_cnt,b.activity_count_join_cnt,b.activity_count_view_cnt"
			+ " FROM	party_activity_base_bd a INNER JOIN party_activity_count_br b ON a.party_activity_base_bd_seq = b.party_activity_base_bd_seq"
			+ " AND b.active_flag = 'y' WHERE a.active_flag = 'y' AND a.activity_base_app_itype in (2,4) AND a.activity_base_organizer = #{shopId} "
			+ " and now() <=  CONCAT(a.activity_base_edate,' ',a.activity_base_etime) AND a.activity_base_status_itype = 0 "
			+ " ORDER BY CASE WHEN a.activity_base_status_itype = 0 AND NOW()>= CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) "
			+ " AND NOW()<= CONCAT(a.activity_base_edate,' ',a.activity_base_etime) THEN	'a' WHEN a.activity_base_status_itype = 0 "
			+ " AND NOW()< CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) "
			+ " AND CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) <= CONCAT(a.activity_base_edate,' ',a.activity_base_etime) THEN	'b' END, "
			+ " a.create_time DESC LIMIT #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "activity_base_status_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinCount", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "viewCount", column = "activity_count_view_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "delayHour", column = "activity_base_delay_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER), })
	List<ActivityManageBo> getActivityManageDoing(@Param("shopId") int shopId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: getActivityManageDoing
	 * @Description: 活动管理，获取店铺已经结束的活动
	 * @Table party_activity_count_br 活动统计信息表 party_activity_base_bd 活动基础表
	 */
	@Select("SELECT a.party_activity_base_bd_seq,a.activity_pic_url,a.activity_base_topic,a.create_time,"
			+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) beginTime,CONCAT(a.activity_base_edate,' ',a.activity_base_etime) finishTime,"
			+ " a.activity_base_status_itype,a.activity_base_delay_cnt,b.activity_count_join_cnt,b.activity_count_view_cnt"
			+ " FROM	party_activity_base_bd a INNER JOIN party_activity_count_br b ON a.party_activity_base_bd_seq = b.party_activity_base_bd_seq"
			+ " AND b.active_flag = 'y' WHERE a.active_flag = 'y' AND a.activity_base_app_itype in (2,4) AND a.activity_base_organizer = #{shopId} "
			+ " AND now() > case when a.activity_base_status_itype in (1,2) then DATE_ADD(NOW(),INTERVAL -1 day) "
			+ " else CONCAT(a.activity_base_edate,' ',a.activity_base_etime) end "
			+ " ORDER BY	CASE WHEN a.activity_base_status_itype = 2 THEN 'a' WHEN a.activity_base_status_itype = 0 "
			+ " AND NOW()> CONCAT(a.activity_base_edate,' ',a.activity_base_etime) THEN	'b' "
			+ " WHEN a.activity_base_status_itype = 1 THEN 'c' END,  "
			+ " CONCAT(a.activity_base_edate,' ',a.activity_base_etime) DESC LIMIT #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "status", column = "activity_base_status_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinCount", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "viewCount", column = "activity_count_view_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "delayHour", column = "activity_base_delay_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER), })
	List<ActivityManageBo> getActivityManageEnding(@Param("shopId") int shopId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: getActivityCount
	 * @Description: 获取城市未结束活动的总量
	 * @Table party_activity_base_bd 活动基础表
	 */
	@Select("SELECT COUNT(1) from party_activity_base_bd WHERE active_flag='y' and pub_city_dict_sd_seq=#{cityId} "
			+ " AND CONCAT(activity_base_edate,' ',activity_base_etime)>now() and activity_base_status_itype=0 "
			+ " AND CONCAT(activity_base_edate,' ',activity_base_etime)>CONCAT(activity_base_bdate,' ',activity_base_btime) "
			+ " AND mobiz_shop_auth_itype = 3  and mobiz_shop_status_itype = 1")
	int getActivityCount(@Param("cityId") int cityId);

	/**
	 * @Title: getHotActivity
	 * @Description: 获取热门活动
	 * @Table party_activity_base_bd 活动基础表 home_hot_activity_br 首页热门活动表
	 *        party_activity_address_br 活动地址表
	 */
	@Select("SELECT	a.hot_activity_order,b.party_activity_base_bd_seq,b.activity_base_topic,b.activity_base_app_itype,b.activity_pic_url,b.party_activity_type_bd_seq,"
			+ " CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) startTime,CONCAT(b.activity_base_edate,' ',b.activity_base_etime) endTime"
			+ " FROM	home_hot_activity_br a INNER JOIN party_activity_base_bd b ON a.active_flag = 'y'"
			+ " AND a.party_activity_base_bd_seq=b.party_activity_base_bd_seq AND a.pub_city_dict_sd_seq = #{cityId}"
			+ " WHERE b.active_flag = 'y' AND CONCAT(b.activity_base_edate,' ',b.activity_base_etime)> now()"
			+ " AND CONCAT(b.activity_base_edate,' ',b.activity_base_etime)>CONCAT(b.activity_base_bdate,' ',b.activity_base_btime) "
			+ " AND b.activity_base_status_itype=0 ORDER BY a.hot_activity_order LIMIT #{begin},#{end}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "type", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "order", column = "hot_activity_order", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityTypeId", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<HotActivityListBo> getHotActivity(@Param("cityId") int cityId, @Param("begin") int begin,
			@Param("end") int end);

	/**
	 * @Title: getAllActivitiesByShopIds
	 * @Description: 获取店铺活动
	 * @Table party_activity_base_bd 活动基础表 party_activity_address_br 活动地址表
	 */
	@Select("<script> SELECT party_activity_base_bd_seq FROM party_activity_base_bd a "
			+ " WHERE (a.activity_base_edate > CURDATE() or (activity_base_edate = CURDATE() and a.activity_base_etime > CURRENT_TIME())) "
			+ " AND a.activity_base_status_itype = 0 AND a.activity_base_app_itype IN (2,4) AND a.active_flag = 'y' AND mobiz_shop_auth_itype=#{authType} "
			+ " AND exists (select 1 from party_activity_address_br b where a.party_activity_base_bd_seq = b.party_activity_base_bd_seq and "
			+ " b.active_flag = 'y' AND b.biz_mobiz_shop_bd_seq "
			+ " in <foreach item='item' collection='shopIdList' open ='(' separator=',' close=')'>#{item}</foreach>) </script>")
	List<Long> getAllActivitiesByShopIds(@Param("shopIdList") List<Long> shopIdList, @Param("authType") int authType);

	/**
	 * @Title: updateQualificationById
	 * @Description: 修改资质认证状态
	 * @Table party_activity_base_bd 活动基础表
	 */
	@Update("UPDATE party_activity_base_bd SET mobiz_shop_auth_itype = #{authType} "
			+ " WHERE active_flag ='y' AND party_activity_base_bd_seq = #{activityId}")
	void updateQualificationById(@Param("activityId") int activityId, @Param("authType") int authType);

	/**
	 * @Title: getPopularityActivity
	 * @Description: 获取人气最高的活动
	 * @Table party_activity_base_bd 活动基础表 party_activity_address_br 活动地址表
	 *        party_activity_count_br 活动统计信息表
	 */
	@Select("SELECT a.party_activity_base_bd_seq,a.activity_base_topic,a.party_activity_type_bd_seq,CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) startTime, "
			+ " CONCAT(a.activity_base_edate,' ',a.activity_base_etime) endTime,a.activity_pic_url,a.activity_base_app_itype, "
			+ " b.activity_address_detail,b.biz_mobiz_shop_bd_seq,c.activity_count_join_cnt,c.activity_count_view_cnt "
			+ " FROM party_activity_base_bd a,party_activity_address_br b,party_activity_count_br c "
			+ " WHERE a.active_flag = 'y' AND b.active_flag = 'y' AND c.active_flag = 'y' "
			+ " AND a.activity_base_organizer = (CASE WHEN a.activity_base_app_itype in (1,3) THEN a.activity_base_organizer ELSE b.biz_mobiz_shop_bd_seq END ) "
			+ " AND a.party_activity_base_bd_seq = b.party_activity_base_bd_seq "
			+ " AND a.party_activity_base_bd_seq = c.party_activity_base_bd_seq AND a.activity_base_status_itype = 0 "
			+ " AND CONCAT(a.activity_base_edate,' ',a.activity_base_etime) > CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) "
			+ " AND CONCAT(a.activity_base_edate,' ',a.activity_base_etime)>now() "
			+ " ORDER BY c.activity_count_join_cnt DESC,c.activity_count_view_cnt DESC LIMIT 3")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "theme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "pictureUrl", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "type", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "shopId", column = "biz_mobiz_shop_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "joinNumber", column = "activity_count_join_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityTypeId", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<HotActivityListBo> getPopularityActivity();

	/**
	 * @Title: updateShopScreeningById
	 * @Description: 修改店铺屏蔽状态
	 * @Table party_activity_address_br 活动地点表
	 */
	@Update("UPDATE party_activity_base_bd SET mobiz_shop_status_itype = #{screening} "
			+ " WHERE active_flag ='y' AND party_activity_base_bd_seq = #{activityId}")
	void updateShopScreeningById(@Param("activityId") int activityId, @Param("screening") int screening);

	/**
	 * 获取活动信息
	 * 
	 * @param activityId
	 * @return
	 */
	@Select("SELECT a.party_activity_base_bd_seq,a.activity_base_topic,a.activity_base_desc,a.activity_pic_url,a.activity_base_status_itype,a.activity_base_organizer, "
			+ " a.party_activity_type_bd_seq,b.activity_type_name,CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) beginTime, "
			+ " CONCAT(a.activity_base_edate,' ',a.activity_base_etime) endTime,a.activity_base_app_itype, "
			+ " c.activity_address_latitude,c.activity_address_longitude,c.activity_address_detail FROM party_activity_base_bd a "
			+ " INNER JOIN party_activity_type_bd b ON a.party_activity_type_bd_seq = b.party_activity_type_bd_seq "
			+ " INNER JOIN party_activity_address_br c ON a.party_activity_base_bd_seq = c.party_activity_base_bd_seq AND "
			+ " a.activity_base_organizer = (CASE WHEN a.activity_base_app_itype in (1,3) THEN a.activity_base_organizer ELSE c.biz_mobiz_shop_bd_seq END) "
			+ " WHERE a.party_activity_base_bd_seq = #{activityId} AND a.active_flag = 'y' AND a.mobiz_shop_auth_itype = 3 AND a.mobiz_shop_status_itype = 1 "
			+ " AND b.active_flag = 'y' AND c.active_flag = 'y'")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityTheme", column = "activity_base_topic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityDesc", column = "activity_base_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityPicture", column = "activity_pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityStatus", column = "activity_base_status_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "createBy", column = "activity_base_organizer", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "activityType", column = "party_activity_type_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityTypeName", column = "activity_type_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "activityOrganizerType", column = "activity_base_app_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "latitude", column = "activity_address_latitude", javaType = double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "longitude", column = "activity_address_longitude", javaType = double.class, jdbcType = JdbcType.DOUBLE),
			@Result(property = "activityAddress", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
	ActivityInfoBo getActivityById(@Param("activityId") int activityId);
}
