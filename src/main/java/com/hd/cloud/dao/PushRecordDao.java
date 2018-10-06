package com.hd.cloud.dao;

import com.hd.cloud.bo.PushRecord;

/**
 * 
  * @ClassName: PushRecordDao
  * @Description: 推送记录
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月13日 上午10:44:08
  *
 */
public interface PushRecordDao {

	/**
	 * 
	 * @Title: addPushInfo
	 * @param: PushRecord
	 *             record 推送记录
	 * @Description: 保存推送记录
	 * @return PushRecord
	 */
	PushRecord addPushInfo(PushRecord record);

	/**
	 * 
	 * @Title: setHasPushedById
	 * @param: Long
	 *             id 用户id
	 * @Description: 改变用户状态为激活
	 * @return void
	 */
	void setHasPushedById(Long id);

}
