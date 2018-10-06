package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.UserPush;
import com.hd.cloud.dao.UserPushDao;
import com.hd.cloud.dao.mapper.UserPushMapper;

/**
 * 
 * @ClassName: UserPushDaoMyBatisImpl
 * @Description: 用户推送设备信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:45:05
 *
 */
@Repository
public class UserPushDaoMyBatisImpl implements UserPushDao {

	@Inject
	private UserPushMapper userPushMapper;

	@Override
	public UserPush findByUserId(long userId, int appType) {
		return userPushMapper.getUserPushByUserId(userId, appType);
	}

	@Override
	public UserPush add(UserPush userPush) {
		userPushMapper.add(userPush);
		return userPush;
	}

	@Override
	public UserPush update(UserPush userPush) {
		userPushMapper.update(userPush);
		return userPush;
	}

	@Override
	public void deleteByUserId(long userId, String cid) {
		userPushMapper.delete(userId, cid);
	}

	@Override
	public UserPush findByCid(String cid) {
		return userPushMapper.getUserPushByCid(cid);
	}

	@Override
	public List<Long> findBatchUserId(List<Long> userIdList) {
		return userPushMapper.getBatchUserId(userIdList);
	}

	@Override
	public List<String> getCidList(List<Long> userIdList, int appType, int deviceType) {
		return userPushMapper.getCidList(userIdList, appType, deviceType);
	}

	@Override
	public List<UserPush> findUserPushByUserId(long userId, int appType) {
		return userPushMapper.findUserPushByUserId(userId, appType);
	}
}
