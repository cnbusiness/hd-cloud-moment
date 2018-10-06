package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.MomentCount;
import com.hd.cloud.bo.TopicCount;

/**
 * 
 * @ClassName: FeedPostBaseCache
 * @Description: 动态缓存处理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 上午9:00:38
 *
 */
public interface FeedPostBaseCache {

	/**
	 * 
	 * @Title: star
	 * @param:
	 * @Description: 点赞
	 * @return void
	 */
	public void star(int postId, long userId);

	/**
	 * 
	 * @Title: unStar
	 * @param:
	 * @Description: 取消点赞
	 * @return boolean
	 */
	boolean unStar(int postId, long userId);

	/**
	 * 
	 * @Title: deleteStar
	 * @param:
	 * @Description: 删除动态喜欢
	 * @return void
	 */
	void deleteStar(int postId);

	/**
	 * 
	 * @Title: hasStar
	 * @param:
	 * @Description: 判断用户是否喜欢该动态
	 * @return boolean
	 */
	boolean hasStar(int postId, Long userId);

	/**
	 * 
	 * @Title: getPostStarUserIdByPostId
	 * @param:
	 * @Description: 查询动态喜欢用户id
	 * @return List<Long>
	 */
	public List<Long> getPostStarUserIdByPostId(int postId, int start, int end);

	/**
	 * 
	 * @Title: updateMomentCountByHashKey
	 * @param:
	 * @Description: 根据hash key修改数量
	 * @return void
	 */
	void updateMomentCountByHashKey(Integer postId, String hashKey, Integer i);

	/**
	 * 
	 * @Title: getMomentCount
	 * @param:
	 * @Description: 获取动态数量统计
	 * @return MomentCount
	 */
	MomentCount getMomentCount(int postId);

	/**
	 * 
	 * @Title: deleteMomentCount
	 * @param:
	 * @Description: 删除动态数量统计
	 * @return void
	 */
	void deleteMomentCount(int postId);

	/**
	 * 
	 * @Title: updateTopicCountByHashKey
	 * @param:
	 * @Description: 根据hash key修改动态数量统计
	 * @return void
	 */
	void updateTopicCountByHashKey(Integer topicId, String hashKey, Integer i);

	/**
	 * 
	 * @Title: getTopicCount
	 * @param:
	 * @Description: 获取话题统计
	 * @return TopicCount
	 */
	TopicCount getTopicCount(int topicId);
	
	
	/**
	 * 
	* @Title: addUserTimeline 
	* @param: 
	* @Description: 保存用户动态到时间轴
	* @return void
	 */
	void addUserTimeline(Long userId,Integer postId);

	 
	/***
	 * 
	* @Title: getUserTimeline 
	* @param: 
	* @Description: 获取用户时间轴动态信息
	* @return List<Integer>
	 */
	List<Integer> getUserTimeline(Long userId, long start, long end);
	
	
	/**
	 * 
	* @Title: deleteUserTimeline
	* @param: 
	* @Description: 删除用户动态到时间轴
	* @return void
	 */
	void deleteUserTimeline(Long userId,Integer postId);

}
