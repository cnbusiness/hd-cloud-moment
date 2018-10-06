package com.hd.cloud.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.PushRecord;

/**
 * 
 * @ClassName: PushRecordSqlProvider
 * @Description: 推送记录Provider
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:45:24
 *
 */
public class PushRecordSqlProvider {

	/**
	 * 
	 * @Title: addRecord
	 * @param:
	 * @Description: 新增推送记录
	 * @return String
	 */
	public String addRecord(final PushRecord record) {

		return new SQL() {
			{
				INSERT_INTO("push_push_record_bt");
				VALUES("user_base_seq_from", "#{fromUserId}");
				VALUES("push_record_app_itype_from", "#{fromAppType}");
				if (record.getFromCompanyId() != 0) {
					VALUES("mobiz_company_seq_from", "#{fromCompanyId}");
				}
				VALUES("user_base_seq_to", "#{toUserId}");
				VALUES("push_record_app_itype_to", "#{toAppType}");
				if (record.getToCompanyId() != 0) {
					VALUES("mobiz_company_seq_to", "#{toCompanyId}");
				}
				VALUES("push_record_success_itype", "#{status}");
				VALUES("user_device_cid_to", "#{toCid}");
				VALUES("user_device_os_itype", "#{deviceType}");
				VALUES("push_record_notice_itype", "#{messageType}");
				VALUES("push_record_entity_itype", "#{contentType}");
				VALUES("push_record_content", "#{content}");
				VALUES("create_time", "#{createTime}");
				VALUES("create_by", "#{createBy}");
				VALUES("update_time", "#{updateTime}");
				VALUES("update_by", "#{updateBy}");
				VALUES("active_flag", "#{activeFlag}");

			}
		}.toString();

	}

}
