package com.hd.cloud.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.ActivityAddress;

/**
 * 
 * @ClassName: ActivityAddressSqlProvider
 * @Description: 活动地点
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:50:51
 *
 */
public class ActivityAddressSqlProvider {

	public String getActivityAddress(final Map<String, Object> map) {
		int shopId = Integer.parseInt(map.get("shopId").toString());
		return new SQL() {
			{
				SELECT(" * ");
				FROM(" party_activity_address_br ");
				WHERE(" active_flag='y' and party_activity_base_bd_seq=#{activityId} ");
				if (shopId != 0) {
					AND();
					WHERE(" biz_mobiz_shop_bd_seq =#{shopId} ");
				}
			}
		}.toString();
	}

	public String update(final ActivityAddress activityAddress) {
		return new SQL() {
			{
				UPDATE("party_activity_address_br");
				if (activityAddress.getShopId() != 0) {
					SET("biz_mobiz_shop_bd_seq = #{shopId} ");
				}
				if (activityAddress.getLatitude() != 0.0) {
					SET("activity_address_latitude= #{latitude} ");
				}
				if (activityAddress.getLongitude() != 0.0) {
					SET("activity_address_longitude= #{longitude} ");
				}
				if (!StringUtils.isBlank(activityAddress.getAddress())) {
					SET("activity_address_detail= #{address} ");
				}
				if (activityAddress.getActiveFlag() != '\u0000') {
					SET("active_flag= #{activeFlag}");
				}
				SET(" update_by= #{updater} , update_time= now() ");
				WHERE(" active_flag='y' and party_activity_base_bd_seq= #{activityId} ");
			}
		}.toString();
	}
}
