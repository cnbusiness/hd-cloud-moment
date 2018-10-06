package com.hd.cloud.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * 
 * @ClassName: ActivityLotterySqlProvider
 * @Description: 活动抽奖
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:53:16
 *
 */
public class ActivityLotterySqlProvider {

	public String getUser(final Map<String, Object> map) {

		int pageSize = Integer.parseInt(map.get("pageSize").toString());

		String sql = new SQL() {
			{
				SELECT(" a.user_user_base_sb_seq ");
				FROM(" party_activity_join_br a ");
				WHERE("a.party_activity_base_bd_seq = #{activityId} AND a.active_flag = 'y' AND a.activity_join_present_itype = 1 "
						+ " AND NOT EXISTS(SELECT 1 FROM party_activity_lottery_bt b WHERE "
						+ " b.party_activity_base_bd_seq = a.party_activity_base_bd_seq "
						+ " AND a.user_user_base_sb_seq = b.user_user_base_sb_seq AND b.active_flag = 'y' )");
			}
		}.toString();
		if (pageSize != 0) {
			StringBuffer sb = new StringBuffer(sql);
			sb.append(" limit #{pageIndex},#{pageSize} ");
			sql = sb.toString();
		}

		return sql;
	}
}
