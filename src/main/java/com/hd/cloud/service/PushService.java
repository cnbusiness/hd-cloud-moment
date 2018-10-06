package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.UserPush;

/**
 * 
 * @ClassName: PushService
 * @Description: Push Service
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:47:07
 *
 */
public interface PushService {

	/**
	 * 
	 * @Title: addUserPush
	 * @param: UserPush
	 *             push
	 * @Description: 增加用户设备信息
	 * @return UserPush
	 */
	public UserPush addUserPush(UserPush push);

	/**
	 * 
	 * @Title: updateUserPushByUserId
	 * @param: UserPush
	 *             push
	 * @Description: 更新用户设备信息(CID)
	 * @return UserPush
	 */
	public UserPush updateUserPushByUserId(UserPush push);

	/**
	 * 
	 * @Title: deleteUserPushByUserId
	 * @param: long
	 *             userId
	 * @param: String
	 *             cid
	 * @Description: 删除用户设备信息（登出）
	 * @return void
	 */
	public void deleteUserPushByUserId(long userId, String cid);

	/**
	 * 
	 * @Title: checkBandingPushUserId
	 * @param: List<Long>
	 *             userIdList 用户id列表
	 * @Description: 校验这些用户那些已经绑定了推送
	 * @return List<Long>
	 */
	public List<Long> checkBandingPushUserId(List<Long> userIdList);

}
