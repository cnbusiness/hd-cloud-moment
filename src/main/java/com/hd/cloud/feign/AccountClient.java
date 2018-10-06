package com.hd.cloud.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hd.cloud.bo.FriendBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.hystrix.HystrixWrappedAccountClient;
import com.hd.cloud.vo.UserProfileVo;

/**
 * 
 * @ClassName: AccountClient
 * @Description: 用户微服务
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午5:17:07
 *
 */
@FeignClient(name = "hd-cloud-account",fallback=HystrixWrappedAccountClient.class)
public interface AccountClient {

	/**
	 * 
	 * @Title: getUserProfileByUserId
	 * @param:
	 * @Description: 获取用户详情
	 * @return UserProfile
	 */
	@RequestMapping(value = "/userprofile/{userId}", method = RequestMethod.GET)
	public UserProfile getUserProfileByUserId(@PathVariable("userId") long userId);

	/**
	 * 
	 * @Title: getUserProfileList
	 * @param:
	 * @Description: 批量获取用户详情
	 * @return List<UserProfile>
	 */
	@RequestMapping(value = "/userprofile/list", method = RequestMethod.POST)
	public List<UserProfile> getUserProfileList(@RequestBody UserProfileVo userProfileVo);

	/**
	 * 
	 * @Title: getAllFriends
	 * @param:
	 * @Description: 获取好友列表
	 * @return BoUtil
	 */
	@RequestMapping(value = "/friend/all/{userId}", method = RequestMethod.GET)
	public List<FriendBo> getAllFriendsByUserId(@PathVariable("userId") long userId);
}
