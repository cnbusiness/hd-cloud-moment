package com.hd.cloud.dao;

import java.util.List;
import java.util.Map;

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.TopicSectionPostListVo;

/**
 * 
 * @ClassName: FeedPostBaseDao
 * @Description: 动态管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 上午11:28:22
 *
 */
public interface FeedPostBaseDao {

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
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return int
	 */
	public int save(FeedPostBase feedPostBase);

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
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo);

	/**
	 * 
	 * @Title: getFeedPostBaseListByIds
	 * @param:
	 * @Description: 查询动态列表
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFeedPostBaseListByIds(List<Integer> ids);

	/**
	 * 
	 * @Title: getgetFewDynamicByTopicId
	 * @param:
	 * @Description: 根据话题id查询动态列表
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getgetFewDynamicByTopicId(int topicId);

	/**
	 * 
	 * @Title: getFeedPostBaseListByTopicSectionPostListVo
	 * @param:
	 * @Description: 根据话题id查询动态列表
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFeedPostBaseListByTopicSectionPostListVo(
			TopicSectionPostListVo topicSectionPostListVo);
}
