package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.UserPush;

/**
 * 
 * @ClassName: UserPushDao
 * @Description: 用户 推送
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:44:20
 *
 */
public interface UserPushDao {

	/**
	 * 
	 * @Title: findByUserId
	 * @param: long
	 *             userId
	 * @Description: 通过用户id获取存储的设备信息
	 * @return UserPush
	 */
	UserPush findByUserId(long userId, int appType);

	/**
	 * 
	 * @Title: findByCid
	 * @param: String
	 *             cid
	 * @Description: 通过Cid查询用户信息
	 * @return UserPush
	 */
	UserPush findByCid(String cid);

	/**
	 * 
	 * @Title: add
	 * @param: UserPush
	 *             userPush
	 * @Description: 新建push存储记录
	 * @return UserPush
	 */
	UserPush add(UserPush userPush);

	/**
	 * 
	 * @Title: update
	 * @param: UserPush
	 *             userPush
	 * @Description: 更新push的cid存放信息
	 * @return UserPush
	 */
	UserPush update(UserPush userPush);

	/**
	 * 
	 * @Title: deleteByUserId
	 * @param: long
	 *             userId
	 * @Description: 根据用户id删除push cid信息
	 * @return void
	 */
	void deleteByUserId(long userId, String cid);

	/**
	 * 
	 * @Title: findBatchUserId
	 * @param: List<Long>
	 *             userIdList 用户idList列表
	 * @Description: 在list中找到找到绑定的用户
	 * @return List<Long>
	 */
	List<Long> findBatchUserId(List<Long> userIdList);

	/**
	 * 
	 * @Title: findCidList
	 * @param:
	 * @Description: 查找用户的List
	 * @return List<String>
	 */
	List<String> getCidList(List<Long> userIdList, int appType, int deviceType);

	List<UserPush> findUserPushByUserId(long userId, int appType);
}
