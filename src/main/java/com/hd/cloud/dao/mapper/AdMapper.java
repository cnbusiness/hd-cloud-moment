package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

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

import com.hd.cloud.bo.Ad;
import com.hd.cloud.bo.AdClick;
import com.hd.cloud.dao.sql.AdSql;
import com.hd.cloud.vo.BannerVo;

/**
 * 
 * @ClassName: AdMapper
 * @Description: 广告
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:29:09
 *
 */
@Mapper
public interface AdMapper {

	/**
	 * @Title: getAdInfoById
	 * @Description: 查询ad信息sql
	 * @Table ad_advert_base_bb（广告基础信息表）
	 */
	@Select("SELECT ad_advert_base_bb_seq AS id, advert_base_desc AS remark, advert_base_title AS title, advert_base_pic_url "
			+ "AS pic_path, advert_base_link_itype AS link_type, advert_base_link_url AS link_url, pub_app_section_sd_seq AS link_id,"
			+ " advert_base_valid_btime AS start_time, advert_base_valid_etime AS end_time, advert_base_valid_itype AS effect_type,"
			+ "update_time,advert_base_internal_code,advert_base_click_itype FROM ad_advert_base_bb WHERE ad_advert_base_bb_seq = #{id} AND active_flag ='y' "
			+ "AND  ( (advert_base_valid_itype = 2 AND advert_base_valid_btime <= CURRENT_TIMESTAMP() AND advert_base_valid_etime >= CURRENT_TIMESTAMP()) OR  advert_base_valid_itype = 1 ) ")
	@Results(value = {
			@Result(property = "picPath", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkType", column = "link_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "linkUrl", column = "link_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkId", column = "link_id", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "startTime", column = "start_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "endTime", column = "end_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "effectType", column = "effect_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "internalCode", column = "advert_base_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "isSumClick", column = "advert_base_click_itype", javaType = long.class, jdbcType = JdbcType.INTEGER) })
	Ad getAdInfoById(@Param("id") long id);

	/**
	 * @Title: getAdInfoById
	 * @Description: 查询多个ad信息sql
	 * @Table ad_advert_base_bb（广告基础信息表）
	 */
	@Select("SELECT ad_advert_base_bb_seq AS id, advert_base_desc AS remark, advert_base_title AS title, advert_base_pic_url "
			+ "AS pic_path, advert_base_link_itype AS link_type, advert_base_link_url AS link_url, pub_app_section_sd_seq AS link_id,"
			+ " advert_base_valid_btime AS start_time, advert_base_valid_etime AS end_time, advert_base_valid_itype AS effect_type,"
			+ "update_time FROM ad_advert_base_bb WHERE ad_advert_base_bb_seq in(${ids}) AND active_flag = 'y'"
			+ "AND ( (advert_base_valid_itype = 2 AND advert_base_valid_btime <= CURRENT_TIMESTAMP() AND advert_base_valid_etime >= CURRENT_TIMESTAMP()) OR  advert_base_valid_itype = 1 )")
	@Results(value = {
			@Result(property = "picPath", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkType", column = "link_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "linkUrl", column = "link_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkId", column = "link_id", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "startTime", column = "start_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "endTime", column = "end_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "effectType", column = "effect_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP) })
	List<Ad> getAdInfoByIds(@Param("ids") String ids);

	// 保存点击记录
	@InsertProvider(type = AdSql.class, method = "saveClickAd")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS adClickId  " }, keyProperty = "adClickId", before = false, resultType = long.class)
	int saveClickAd(AdClick adClick);

	// 根据广告位置查询广告
	@SelectProvider(type = AdSql.class, method = "getAdByCode")
	@Results(value = {
			@Result(property = "picPath", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkType", column = "link_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "linkUrl", column = "link_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "linkId", column = "link_id", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "startTime", column = "start_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "endTime", column = "end_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "effectType", column = "effect_type", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "internalCode", column = "advert_base_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "isSumClick", column = "advert_base_click_itype", javaType = long.class, jdbcType = JdbcType.INTEGER) })
	List<Ad> getAdByCode(BannerVo bannerVo);

	// 更新游览量和点击量
	@UpdateProvider(type = AdSql.class, method = "update")
	int update(Ad ad);
}
