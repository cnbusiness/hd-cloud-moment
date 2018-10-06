package com.hd.cloud.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.ActivityBase;

/**
 * 
 * @ClassName: ActivityBaseSqlProvider
 * @Description: 活动基本信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:36:57
 *
 */
public class ActivityBaseSqlProvider {

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑
	 * @return String
	 */
	public String update(final ActivityBase activityBase) {
		return new SQL() {
			{
				UPDATE(" party_activity_base_bd ");

				if (activityBase.getActivityTypeId() != 0) {
					SET(" party_activity_type_bd_seq=#{activityTypeId} ");
				}
				if (!StringUtils.isBlank(activityBase.getTheme())) {
					SET(" activity_base_topic=#{theme} ");
				}
				if (!StringUtils.isBlank(activityBase.getDetail())) {
					SET(" activity_base_desc=#{detail} ");
				}
				if (activityBase.getPersonNumber() != 0) {
					SET(" activity_base_person_cnt=#{personNumber}");
				}
				if (activityBase.getCityId() != 0) {
					SET(" pub_city_dict_sd_seq=#{cityId} ");
				}
				if (activityBase.getStartDate() != null) {
					SET(" activity_base_bdate=#{startDate}");
				}
				if (activityBase.getEndDate() != null) {
					SET(" activity_base_edate=#{endDate}");
				}
				if (activityBase.getStartTime() != null) {
					SET(" activity_base_btime=#{startTime}");
				}
				if (activityBase.getEndTime() != null) {
					SET(" activity_base_etime=#{endTime}");
				}
				if (!StringUtils.isBlank(activityBase.getQrcode())) {
					SET(" activity_base_qrcode_url=#{qrcode}");
				}
				if (activityBase.getDelayHour() != 0) {
					SET(" activity_base_delay_cnt=#{delayHour}");
				}
				if (activityBase.getIsNeedPhone() != 0) {
					SET(" activity_base_contact_itype=#{isNeedPhone} ");
				}
				if (activityBase.getStatus() != 0) {
					SET(" activity_base_status_itype=#{status}");
				}
				if (!StringUtils.isBlank(activityBase.getLogoUrl())) {
					SET(" activity_pic_url=#{logoUrl}");
				}
				if (!StringUtils.isBlank(activityBase.getPictureUrls())) {
					SET(" activity_pic_urls=#{pictureUrls}");
				}
				if (activityBase.getIsNeedNotice() != 0) {
					SET(" activity_base_notice_itype=#{isNeedNotice}");
				}
				if (activityBase.getHasShake() != 0) {
					SET(" activity_base_shake_itype=#{hasShake}");
				}
				if (activityBase.getCouponId() != 0) {
					SET(" goods_goods_base_bd_seq=#{couponId}");
				}
				SET(" update_by=#{updater} , update_time=now() ");

				WHERE(" active_flag='y' and party_activity_base_bd_seq=#{id} ");
			}
		}.toString();
	}

	// 活动首页
	public String getActivityList(final Map<String, Object> map) {
		int activityTypeId = Integer.parseInt(map.get("activityTypeId").toString());
		int activityTime = Integer.parseInt(map.get("activityTime").toString());
		int type = Integer.parseInt(map.get("type").toString());
		return new SQL() {
			{
				SELECT("a.party_activity_base_bd_seq,a.activity_base_topic,b.activity_type_name,a.party_activity_type_bd_seq,"
						+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) startTime,CONCAT(a.activity_base_edate,' ',a.activity_base_etime) endTime, "
						+ " a.activity_pic_url,a.activity_base_app_itype,c.activity_count_join_cnt,a.create_time,c.activity_count_view_cnt ");
				FROM(" party_activity_base_bd a,party_activity_type_bd b,party_activity_count_br c ");
				WHERE(" a.active_flag='y' and b.active_flag='y' and a.pub_city_dict_sd_seq=#{cityId} and a.party_activity_type_bd_seq=b.party_activity_type_bd_seq "
						+ " and a.party_activity_base_bd_seq=c.party_activity_base_bd_seq and c.active_flag='y' and a.activity_base_status_itype=0 ");
				if (activityTypeId != 0) {
					AND();
					WHERE(" a.party_activity_type_bd_seq=#{activityTypeId} ");
				}
				if (activityTime == 1) {
					AND();
					WHERE(" fun_get_partystatus(a.party_activity_base_bd_seq) like '%C%' ");
				}
				if (activityTime == 2) {
					AND();
					WHERE(" fun_get_partystatus(a.party_activity_base_bd_seq) like '%A%' ");
				}
				if (activityTime == 3) {
					AND();
					WHERE(" fun_get_partystatus(a.party_activity_base_bd_seq) like '%B%' ");
				}
				if (activityTime == 4) {
					AND();
					WHERE(" fun_get_partystatus(a.party_activity_base_bd_seq) like '%D%' ");
				}
				if (type != 4) {
					AND();
					WHERE(" CONCAT(a.activity_base_edate,' ',a.activity_base_etime)>now() ");
				} else {
					AND();
					WHERE(" CONCAT(a.activity_base_bdate,' ',a.activity_base_btime)>now() ");
				}
				AND();
				WHERE(" CONCAT(a.activity_base_edate,' ',a.activity_base_etime) > CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) ");
				AND();
				WHERE(" mobiz_shop_auth_itype = 3  and mobiz_shop_status_itype = 1");
				if (type == 2) {
					ORDER_BY(
							" c.activity_count_join_cnt desc, c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

				if (type == 3) {
					ORDER_BY(" a.create_time desc,c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

				if (type == 4) {
					ORDER_BY(
							" CONCAT(a.activity_base_bdate,' ',a.activity_base_btime),c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

			}
		}.toString();
	}

	// 活动详情
	public String getActivity(final Map<String, Object> map) {
		int appType = Integer.parseInt(map.get("appType").toString());
		return new SQL() {
			{
				SELECT("a.party_activity_base_bd_seq,a.activity_base_topic,a.party_activity_type_bd_seq,"
						+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) startTime,CONCAT(a.activity_base_edate,' ',a.activity_base_etime) endTime,"
						+ " b.activity_address_detail,a.activity_pic_url,a.activity_base_app_itype,c.activity_count_join_cnt,b.activity_address_latitude,b.activity_address_longitude ");

				FROM(" party_activity_base_bd a,party_activity_address_br b,party_activity_count_br c ");
				WHERE(" a.party_activity_base_bd_seq= #{activityId} and a.party_activity_base_bd_seq= b.party_activity_base_bd_seq "
						+ " and a.active_flag='y' and b.active_flag='y' and c.active_flag='y' and a.activity_base_status_itype=0 "
						+ " and a.party_activity_base_bd_seq= c.party_activity_base_bd_seq ");
				if (appType == 2 || appType == 4) {
					AND();
					WHERE(" biz_mobiz_shop_bd_seq=#{shopId} ");

				}
			}
		}.toString();
	}

	// 热门活动 （人气最高、附近的活动）
	public String getOtherHotActivity(final Map<String, Object> map) {
		int type = Integer.parseInt(map.get("type").toString());
		int cityId = Integer.parseInt(map.get("cityId").toString());

		return new SQL() {
			{
				SELECT("a.party_activity_base_bd_seq,a.activity_base_topic,b.activity_type_name,a.party_activity_type_bd_seq,"
						+ " CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) startTime,CONCAT(a.activity_base_edate,' ',a.activity_base_etime) endTime, "
						+ " a.activity_pic_url,a.activity_base_app_itype,c.activity_count_join_cnt,a.create_time,c.activity_count_view_cnt ");
				FROM(" party_activity_base_bd a,party_activity_type_bd b,party_activity_count_br c ");
				WHERE(" a.active_flag='y' and b.active_flag='y' and a.party_activity_type_bd_seq=b.party_activity_type_bd_seq "
						+ " and a.party_activity_base_bd_seq=c.party_activity_base_bd_seq and c.active_flag='y' and a.activity_base_status_itype=0 ");
				if (cityId != 0) {
					AND();
					WHERE(" a.pub_city_dict_sd_seq=#{cityId} ");
				}
				if (type != 4) {
					AND();
					WHERE(" CONCAT(a.activity_base_edate,' ',a.activity_base_etime)>now() ");
				} else {
					AND();
					WHERE(" CONCAT(a.activity_base_bdate,' ',a.activity_base_btime)>now() ");
				}
				AND();
				WHERE(" CONCAT(a.activity_base_edate,' ',a.activity_base_etime) > CONCAT(a.activity_base_bdate,' ',a.activity_base_btime) ");
				AND();
				WHERE(" mobiz_shop_auth_itype = 3 ");
				if (type == 2) {
					ORDER_BY(
							" c.activity_count_join_cnt desc, c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

				if (type == 3) {
					ORDER_BY(" a.create_time desc,c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

				if (type == 4) {
					ORDER_BY(
							" CONCAT(a.activity_base_bdate,' ',a.activity_base_btime),c.activity_count_view_cnt desc limit #{pageIndex},#{pageSize}");
				}

			}
		}.toString();
	}
}
