package com.hd.cloud.hystrix;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.bo.FriendBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.vo.UserProfileVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HystrixWrappedAccountClient implements AccountClient {

	@Inject
	private AccountClient accountClient;

	@Override
	@HystrixCommand(groupKey = "getUserProfileByUserId", fallbackMethod = "getUserProfileByUserIdFailBackCall")
	public UserProfile getUserProfileByUserId(long userId) {
		return accountClient.getUserProfileByUserId(userId);
	}

	public UserProfile getUserProfileByUserIdFailBackCall(long userId) {
		return null;
	}

	@Override
	@HystrixCommand(groupKey = "getUserProfileList", fallbackMethod = "getUserProfileListFailBackCall")
	public List<UserProfile> getUserProfileList(UserProfileVo userProfileVo) {
		return accountClient.getUserProfileList(userProfileVo);
	}

	public List<UserProfile> getUserProfileListFailBackCall(UserProfileVo userProfileVo) {
		return null;
	}

	@Override
	@HystrixCommand(groupKey = "getAllFriendsByUserId", fallbackMethod = "getAllFriendsByUserIdFailBackCall")
	public List<FriendBo> getAllFriendsByUserId(long userId) {
		return accountClient.getAllFriendsByUserId(userId);
	}

	public List<FriendBo> getAllFriendsByUserIdFailBackCall(long userId) {
		return null;
	}
}
