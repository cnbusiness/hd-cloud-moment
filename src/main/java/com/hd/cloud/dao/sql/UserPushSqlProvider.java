package com.hd.cloud.dao.sql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.UserPush;

/**
 * 
 * @ClassName: UserPushSqlProvider
 * @Description: 用户个推信息provider
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:45:36
 *
 */
public class UserPushSqlProvider {

	/**
	 * 
	 * @Title: add
	 * @param:
	 * @Description: 新增用户个推信息
	 * @return String
	 */
	public String add(final UserPush push) {

		return new SQL() {
			{
				INSERT_INTO("push_user_device_bd");
				VALUES("user_device_cid", "#{cid}");
				VALUES("user_user_base_sb_seq", "#{userId}");
				VALUES("user_device_os_itype", "#{deviceType}");
				VALUES("user_device_app_itype", "#{appType}");
				VALUES("create_by", "#{createBy}");
				VALUES("update_time", "#{updateTime}");
				VALUES("update_by", "#{updateBy}");
				VALUES("active_flag", "#{activeFlag}");
			}
		}.toString();

	}

	/**
	 * 
	 * @Title: getBatchUserId
	 * @param: Map<String,
	 *             List<Long>> para
	 * @Description: 批量查询provider
	 * @return String
	 */
	public String getBatchUserId(Map<String, List<Long>> para) {
		List<Long> userIdList = para.get("userIdList");
		StringBuffer inUserId = new StringBuffer();
		// 组装in内的查询语句
		for (int i = 0; i < userIdList.size(); i++) {
			inUserId.append(userIdList.get(i));
			// 如果到最后就不添加逗号
			if (i != userIdList.size() - 1) {
				inUserId.append(",");
			}
		}
		return new SQL() {
			{
				SELECT("user_user_base_sb_seq");
				FROM("push_user_device_bd");
				WHERE("user_user_base_sb_seq in (" + inUserId.toString() + ")");
			}
		}.toString();
	}

	/**
	 * 
	 * @Title: add
	 * @param:
	 * @Description: 新增用户个推信息
	 * @return String
	 */
	public String getCidList(Map<String, Object> map) {
		List<Long> userIdList = (List<Long>) map.get("userIdList");
		StringBuffer queryUserIdList = new StringBuffer();
		for (int i = 0; i < userIdList.size(); i++) {
			queryUserIdList.append(userIdList.get(i));
			if (i != userIdList.size() - 1) {
				queryUserIdList.append(",");
			}
		}

		return new SQL() {
			{
				SELECT("user_device_cid");
				FROM("push_user_device_bd");
				WHERE("user_user_base_sb_seq in (" + queryUserIdList.toString() + ")");
				AND();
				WHERE("user_device_app_itype = #{appType}");
				AND();
				WHERE("user_device_os_itype = #{deviceType}");
			}
		}.toString();

	}

}
