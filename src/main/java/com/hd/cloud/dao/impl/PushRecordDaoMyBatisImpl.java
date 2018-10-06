package com.hd.cloud.dao.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.PushRecord;
import com.hd.cloud.dao.PushRecordDao;
import com.hd.cloud.dao.mapper.PushRecordMapper;

/**
 * 
 * @ClassName: PushRecordDaoMyBatisImpl
 * @Description: 推送记录
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:46:16
 *
 */
@Repository
public class PushRecordDaoMyBatisImpl implements PushRecordDao {

	@Inject
	private PushRecordMapper pushRecordMapper;

	@Override
	public PushRecord addPushInfo(PushRecord record) {
		pushRecordMapper.add(record);
		return record;
	}

	@Override
	public void setHasPushedById(Long id) {
		this.pushRecordMapper.updateRecordById(id);
	}
}