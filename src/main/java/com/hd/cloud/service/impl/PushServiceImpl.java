package com.hd.cloud.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.bo.UserPush;
import com.hd.cloud.dao.UserPushDao;
import com.hd.cloud.service.PushService;

/**
 * 
 * @ClassName: PushServiceImpl
 * @Description: 推送生产者实现类
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午10:49:48
 *
 */
@Service
public class PushServiceImpl implements PushService {

	@Inject
	private UserPushDao userPushDao;

	@Override
	public UserPush addUserPush(UserPush newPush) {
		UserPush userPush = userPushDao.findByCid(newPush.getCid());// cid 是否已经绑定
		UserPush usingPush = userPushDao.findByUserId(newPush.getUserId(), newPush.getAppType());// 用户是否已经绑定
		newPush.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		// 没有绑定任何设备
		if (null == userPush && null == usingPush) {
			return userPushDao.add(newPush);
			// 存在用户绑定设备，则解绑设备，并绑定新设备
		} else {
			// 如果存在这个cid已经被绑定其他用户，删除这个cid的用户
			if (null != userPush && userPush.getUserId() != newPush.getUserId()) {
				userPushDao.deleteByUserId(userPush.getUserId(), userPush.getCid());
			}
			// 如果这个用户userId已经存在，并且cid不一致，就替换cId
			if (null != usingPush && !newPush.getCid().endsWith(usingPush.getCid())) {
				return userPushDao.update(newPush);
				// 如果这个用户已经存在，并且cid相等，就直接返回
			}
			if (null != usingPush && newPush.getCid().endsWith(usingPush.getCid())) {
				return newPush;
				// 如果这个用户不存在，且上面已经删除了cid，所以直接插入
			} else {
				return userPushDao.add(newPush);
			}
		}
	}

	@Override
	public UserPush updateUserPushByUserId(UserPush push) {
		return addUserPush(push);
	}

	@Override
	public void deleteUserPushByUserId(long userId, String cid) {
		userPushDao.deleteByUserId(userId, cid);
	}

	@Override
	public List<Long> checkBandingPushUserId(List<Long> userIdList) {
		return userPushDao.findBatchUserId(userIdList);
	}

}
