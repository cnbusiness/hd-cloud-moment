package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hd.cloud.bo.ActivityTypeBo;

@Mapper
public interface PartyActivityTypeMapper {

	/**
	 * 
	 * @Title: getPartyActivityTypeList
	 * @Description: 查询活动类型
	 * @Table 2.3.1party_activity_type_bd(活动类型字典表)
	 */
	@Select(" select party_activity_type_bd_seq as id,activity_type_name as name from party_activity_type_bd where active_flag='y' AND city_dict_country_code=#{countryCode} order by activity_type_order desc ")
	List<ActivityTypeBo> getPartyActivityTypeList(@Param("countryCode") String countryCode);
}
