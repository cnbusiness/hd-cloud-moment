package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.vo.AddMomentVo;
import com.hd.cloud.vo.FansDynamicVo;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.StarUserListVo;
import com.hd.cloud.vo.TopicSectionPostListVo;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: FeedPostBaseService
 * @Description: 动态管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 上午11:28:22
 *
 */
public interface FeedPostBaseService {

	/**
	 * 
	 * @Title: getFeedPostBaseById
	 * @param:
	 * @Description: 查询详情
	 * @return FeedPostBase
	 */
	public FeedPostBase getFeedPostBaseById(int id);

	/**
	 * 
	 * @Title: publishMoment
	 * @param:
	 * @Description: 发布动态
	 * @return int
	 */
	public BoUtil publishMoment(AddMomentVo addMomentVo);

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 修改
	 * @return int
	 */
	public int update(FeedPostBase feedPostBase);

	/**
	 * 
	 * @Title: getFeedPostBaseList
	 * @param:
	 * @Description: 查询用户动态列表
	 * @return List<FeedPostBase>
	 */
	public List<FeedPostBaseBo> getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo);

	/**
	 * 
	 * @Title: saveStar
	 * @param:
	 * @Description: 喜欢
	 * @return BoUtil
	 */
	public BoUtil saveStar(int postId, long userId);

	/**
	 * 
	 * @Title: unStar
	 * @param:
	 * @Description: 取消喜欢
	 * @return BoUtil
	 */
	public BoUtil unStar(int postId, long userId);

	/**
	 * 
	 * @Title: getStarUserList
	 * @param:
	 * @Description: 获取动态喜欢的人列表
	 * @return List<UserProfile>
	 */
	public List<UserProfile> getStarUserList(StarUserListVo starUserListVo);

	/**
	 * 
	 * @Title: getFeedPostBaseListByTopicSectionPostListVo
	 * @param:
	 * @Description: 根据话题id查询动态列表
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFeedPostBaseListByTopicSectionPostListVo(
			TopicSectionPostListVo topicSectionPostListVo);

	/**
	 * 
	 * @Title: getFansDynamicsList
	 * @param:
	 * @Description: 获取好友动态
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFansDynamicsList(FansDynamicVo fansDynamicVo);
}
