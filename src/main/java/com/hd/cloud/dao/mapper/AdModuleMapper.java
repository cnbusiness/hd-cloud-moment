package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.AdJoinDataBo;
import com.hd.cloud.bo.InitPageBo;

/**
 * 
 * @ClassName: AdModuleMapper
 * @Description: AdModule持久映射sql
 * @author wu.xu@moxiangroup.com
 * @Company moxian
 * @date 2015年7月13日 下午2:33:24
 *
 */
@Mapper
public interface AdModuleMapper {

	/**
	 * @Title: getBannerModuleById
	 * @Description: 生成查询banner分组sql
	 * @Table ad_xadvert_module_city_br (广告模块城市关联信息表)，ad_advert_module_sd（广告模块信息表）
	 */
	@Select("SELECT r.pub_city_dict_order,m.ad_advert_module_sd_seq AS id, m.advert_module_name AS module_name, m.advert_module_desc AS module_desc, r.ad_advert_base_bb_seq AS ad_id, r.pub_city_dict_order AS sort from ad_advert_module_sd m "
			+ " INNER JOIN ad_xadvert_module_city_br r ON m.ad_advert_module_sd_seq = r.ad_advert_module_sd_seq AND r.active_flag = 'y' AND "
			+ "r.pub_city_dict_sd_seq = #{cityId}  WHERE m.active_flag = 'y' AND m.advert_module_internal_code = #{bannerCode} AND"
			+ " m.advert_module_banner_itype = #{type}  ORDER BY r.pub_city_dict_order ASC ")
	@Results(value = {
			@Result(property = "moduleName", column = "module_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "moduleRemark", column = "module_remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "adId", column = "ad_id", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<AdJoinDataBo> getBannerModuleById(@Param("bannerCode") String bannerCode, @Param("cityId") long cityId,
			@Param("type") int type);

	/**
	 * @Title: getInitPageModuleByCityId
	 * @Description: 生成魔线启动页sql
	 * @Table ad_xadvert_module_city_br (广告模块城市关联信息表)，ad_advert_module_sd（广告模块信息表）
	 */
	@Select("SELECT m.ad_advert_module_sd_seq,m.ad_advert_module_sd_seq AS id, m.advert_module_name AS module_name, m.advert_module_desc AS module_desc,r.ad_advert_base_bb_seq AS ad_id ,"
			+ "r.pub_city_dict_order AS sort from"
			+ " ad_advert_module_sd as m , ad_xadvert_module_city_br as r WHERE m.ad_advert_module_sd_seq = r.ad_advert_module_sd_seq AND r.active_flag "
			+ " ='y' AND r.pub_city_dict_sd_seq = #{cityId}  "
			+ " AND m.active_flag = 'y' AND m.advert_module_internal_code = #{bannerCode} AND ( m.advert_module_valid_itype = 1  OR ( m.advert_module_valid_itype = 2"
			+ " AND advert_module_page_btime <= CURRENT_TIMESTAMP() AND advert_module_page_etime >= CURRENT_TIMESTAMP() )) ORDER BY m.ad_advert_module_sd_seq ASC LIMIT 5 ")
	@Results(value = {
			@Result(property = "moduleName", column = "module_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "moduleRemark", column = "module_remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "adId", column = "ad_id", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<AdJoinDataBo> getInitPageModuleByCityId(@Param("bannerCode") String bannerCode, @Param("cityId") long cityId);

	/**
	 * @Title: getDefaultInitPageModule
	 * @Description: 生成默认的魔线启动页sql
	 * @Table ad_xadvert_module_city_br (广告模块城市关联信息表)，ad_advert_module_sd（广告模块信息表）
	 */
	@Select("SELECT m.ad_advert_module_sd_seq,m.ad_advert_module_sd_seq AS id,m.advert_module_name AS module_name,m.advert_module_desc AS module_remark,r.ad_advert_base_bb_seq AS "
			+ "ad_id,r.pub_city_dict_order AS sort from ad_advert_module_sd as m "
			+ " , ad_xadvert_module_city_br as r WHERE m.ad_advert_module_sd_seq = r.ad_advert_module_sd_seq AND r.active_flag = 'y' AND pub_city_dict_sd_seq > 0 AND "
			+ " m.active_flag = 'y' AND m.advert_module_internal_code = #{bannerCode} AND  ( m.advert_module_valid_itype = 1  OR ( m.advert_module_valid_itype = 2"
			+ " AND advert_module_page_btime <= CURRENT_TIMESTAMP() AND advert_module_page_etime >= CURRENT_TIMESTAMP() )) ORDER BY m.ad_advert_module_sd_seq ASC LIMIT 5 ")
	@Results(value = {
			@Result(property = "moduleName", column = "module_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "moduleRemark", column = "module_remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "adId", column = "ad_id", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	List<AdJoinDataBo> getDefaultInitPageModule(@Param("bannerCode") String bannerCode);

	/**
	 * @Title: getNewInitPageModuleByCityId
	 * @Description: 生成魔商启动页sql
	 * @Table ad_xadvert_module_city_br
	 *        (广告模块城市关联信息表)，ad_advert_module_sd（广告模块信息表），ad_advert_base_bb（广告基础信息表）
	 */
	@Select("SELECT m.advert_module_page_btime,m.ad_advert_module_sd_seq AS page_group_id,m.update_time AS page_version,a.ad_advert_base_bb_seq AS ad_id ,a.advert_base_pic_url AS pic_path "
			+ "from ad_advert_module_sd as m , ad_xadvert_module_city_br as r, ad_advert_base_bb AS a WHERE m.ad_advert_module_sd_seq = r.ad_advert_module_sd_seq AND a.ad_advert_base_bb_seq=r.ad_advert_base_bb_seq AND r.active_flag = 'y' "
			+ "AND r.pub_city_dict_sd_seq = #{cityId} "
			+ " AND m.active_flag = 'y' AND m.advert_module_internal_code = #{bannerCode} AND ( m.advert_module_valid_itype = 1  OR ( m.advert_module_valid_itype = 2"
			+ " AND m.advert_module_page_btime <= UNIX_TIMESTAMP() AND m.advert_module_page_etime >= UNIX_TIMESTAMP() )) "
			+ " AND  ( (a.advert_base_valid_itype = 2 AND a.advert_base_valid_btime <= CURRENT_TIMESTAMP() AND a.advert_base_valid_etime >= CURRENT_TIMESTAMP()) OR a.advert_base_valid_itype = 1 ) "
			+ " ORDER BY m.advert_module_page_btime DESC LIMIT 5 ")
	@Results(value = {
			@Result(property = "pageGroupId", column = "page_group_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pageVersion", column = "page_version", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "id", column = "ad_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "picUrl", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
	List<InitPageBo> getNewInitPageModuleByCityId(@Param("bannerCode") String bannerCode, @Param("cityId") long cityId);

	/**
	 * @Title: getNewDefaultInitPageModule
	 * @Description: 生成默认的魔商启动页sql
	 * @Table ad_xadvert_module_city_br
	 *        (广告模块城市关联信息表)，ad_advert_module_sd（广告模块信息表），ad_advert_base_bb（广告基础信息表）
	 */
	@Select("SELECT  m.advert_module_page_btime,r.pub_city_dict_sd_seq,m.ad_advert_module_sd_seq AS page_group_id,m.update_time AS page_version,a.ad_advert_base_bb_seq AS ad_id ,a.advert_base_pic_url AS "
			+ "pic_path from ad_advert_module_sd as m , ad_xadvert_module_city_br as r,ad_advert_base_bb AS a WHERE m.ad_advert_module_sd_seq = r.ad_advert_module_sd_seq AND a.ad_advert_base_bb_seq=r.ad_advert_base_bb_seq AND r.active_flag = 'y'  AND "
			+ " m.active_flag = 'y' AND m.advert_module_internal_code = #{bannerCode} AND ( m.advert_module_valid_itype = 1  OR ( m.advert_module_valid_itype = 2"
			+ " AND m.advert_module_page_btime <= CURRENT_TIMESTAMP() AND m.advert_module_page_etime >= CURRENT_TIMESTAMP() )) "
			+ " AND  ( (a.advert_base_valid_itype = 2 AND a.advert_base_valid_btime <= CURRENT_TIMESTAMP() AND a.advert_base_valid_etime >= CURRENT_TIMESTAMP()) OR a.advert_base_valid_itype = 1 ) "
			+ " ORDER BY m.advert_module_page_btime DESC,r.pub_city_dict_sd_seq DESC  LIMIT 5 ")
	@Results(value = {
			@Result(property = "pageGroupId", column = "page_group_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "pageVersion", column = "page_version", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "id", column = "ad_id", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "picUrl", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
	List<InitPageBo> getNewDefaultInitPageModule(@Param("bannerCode") String bannerCode);

}
