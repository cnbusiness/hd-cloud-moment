package com.hd.cloud.dao.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.hd.cloud.bo.PushRecord;
import com.hd.cloud.dao.sql.PushRecordSqlProvider;

/**
 * 
 * @ClassName: PushRecordMapper
 * @Description: 推送记录mapper
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:46:05
 *
 */
@Mapper
public interface PushRecordMapper {

	/**
	 * 
	 * @Title: add
	 * @param:
	 * @Description: 新增个推记录
	 * @Table push_push_record_bt 个推记录表
	 * @return void
	 */
	@InsertProvider(type = PushRecordSqlProvider.class, method = "addRecord")
	@SelectKey(keyProperty = "id", before = false, resultType = long.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void add(PushRecord record);

	/**
	 * 
	 * @Title: updateRecordById
	 * @param:
	 * @Description: 更新个推记录状态
	 * @Table push_push_record_bt 个推记录表
	 * @return void
	 */
	@Update("UPDATE push_push_record_bt SET push_record_success_itype=1,update_time = CURRENT_TIMESTAMP(),active_flag='y' where push_push_record_bt_seq = #{id}")
	void updateRecordById(@Param("id") Long id);
}
