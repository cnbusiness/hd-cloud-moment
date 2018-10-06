package com.hd.cloud.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.Ad;
import com.hd.cloud.bo.AdClick;
import com.hd.cloud.vo.BannerVo;

/**
 * 
 * @ClassName: AdSql
 * @Description: 广告
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:28:49
 *
 */
public class AdSql {

	public String saveClickAd(AdClick adClick) {
		return new SQL() {
			{
				INSERT_INTO("ad_advert_click_bt");
				VALUES("ad_advert_base_bb_seq", "#{adId}");
				VALUES("user_user_base_sb_seq", "#{userId}");
				VALUES("create_by", "#{userId}");
				VALUES("create_time", "NOW()");
				VALUES("update_by", "#{userId}");
				VALUES("update_time", "NOW()");
				VALUES("active_flag", "'y'");
			}
		}.toString();
	}

	public String getAdByCode(BannerVo bannerVo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" ( ");
		sql.append(" SELECT r.ad_advert_base_bb_seq,r.pub_city_dict_order from ad_xadvert_module_city_br r ");
		sql.append(" WHERE r.active_flag='y' ");
		if (bannerVo.getCityId() != null) {
			sql.append(" AND r.pub_city_dict_sd_seq = #{cityId} ");
		}
		sql.append(
				" AND r.ad_advert_module_sd_seq = (SELECT m.ad_advert_module_sd_seq from ad_advert_module_sd m WHERE m.advert_module_internal_code = #{bannerCode} AND m.active_flag='y') ");
		if (bannerVo.getCountryCode() != null) {
			sql.append(" AND r.city_dict_country_code=#{countryCode} ");
		}
		sql.append(" AND r.xadvert_module_show_itype = #{showLevel} ");
		sql.append(" ) b ON a.ad_advert_base_bb_seq = b.ad_advert_base_bb_seq ");

		return new SQL() {
			{
				SELECT("a.ad_advert_base_bb_seq AS id, " + "a.advert_base_desc AS remark, "
						+ "a.advert_base_title AS title, " + "a.advert_base_pic_url AS pic_path,"
						+ "a.advert_base_link_itype AS link_type," + "a.advert_base_link_url AS link_url,"
						+ "a.pub_app_section_sd_seq AS link_id," + "a.advert_base_valid_btime AS start_time,"
						+ "a.advert_base_valid_etime AS end_time," + "a.advert_base_valid_itype AS effect_type,"
						+ "a.update_time,a.advert_base_internal_code," + "a.advert_base_click_itype");

				FROM("ad_advert_base_bb a  INNER JOIN " + sql);
				WHERE("a.active_flag = 'y'");
				AND();
				WHERE("a.advert_base_status_itype = 2");
				ORDER_BY(" b.pub_city_dict_order ASC");

			}
		}.toString();
	}

	public String update(Ad ad) {
		return new SQL() {
			{
				UPDATE("ad_advert_base_bb");
				if (ad.getVisitCnt() != 0) {
					SET("advert_base_click_cnt = #{visitCnt} ");
				}
				if (ad.getWatchCnt() != 0) {
					SET("advert_base_view_cnt = #{watchCnt} ");
				}
				WHERE("ad_advert_base_bb_seq = #{id}");
			}
		}.toString();
	}
}
