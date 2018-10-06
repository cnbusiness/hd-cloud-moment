package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityAddress;
import com.hd.cloud.bo.ActivityAddressBo;
import com.hd.cloud.dao.ActivityAddressDao;
import com.hd.cloud.dao.mapper.ActivityAddressMapper;

/**
 * 
 * @ClassName: ActivityAddressDaoMyBatisImpl
 * @Description: 活动地址
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:52:07
 *
 */
@Repository
public class ActivityAddressDaoMyBatisImpl implements ActivityAddressDao {

	@Inject
	private ActivityAddressMapper activityAddressMapper;

	@Override
	public void addActivityAddress(ActivityAddress activityAddress) {
		activityAddressMapper.addActivityAddress(activityAddress);
	}

	@Override
	public ActivityAddress getActivityAddressById(int activityId, int shopId) {
		return activityAddressMapper.getActivityAddressById(activityId, shopId);
	}

	@Override
	public List<Integer> getActivityShopCount(int activityId) {
		return activityAddressMapper.getActivityShopCount(activityId);
	}

	@Override
	public List<ActivityAddressBo> getActivityAddress(List<Integer> activityIds) {
		return activityAddressMapper.getActivityAddress(activityIds);
	}

	@Override
	public void updateActivityAddress(ActivityAddress activityAddress) {
		activityAddressMapper.updateActivityAddress(activityAddress);
	}

	@Override
	public int getShopActivityCount(int shopId) {
		return activityAddressMapper.getShopActivityCount(shopId);
	}

	@Override
	public List<ActivityAddressBo> getActivityAddrById(int activityId) {
		return activityAddressMapper.getActivityAddrById(activityId);
	}

	@Override
	public List<Long> getActivityByShopId(long shopId, int screening) {
		return activityAddressMapper.getActivityByShopId(shopId, screening);
	}

}
