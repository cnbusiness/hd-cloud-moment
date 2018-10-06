package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.bo.TopicSectionBo;
import com.hd.cloud.vo.TopicSectionPostListVo;
import com.hd.cloud.vo.TopicSectionVo;

/**
 * 
  * @ClassName: TopicSectionService
  * @Description: 话题动态管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月11日 下午5:43:40
  *
 */
public interface TopicSectionService {

	/**
	 * 
	* @Title: getHotTopicSectionList 
	* @param: 
	* @Description: 获取热门话题
	* @return List<TopicSection>
	 */
	public List<TopicSection> getHotTopicSectionList();
	
	
	/**
	 * 
	* @Title: getTopicSectionList 
	* @param: 
	* @Description: 获取动态列表
	* @return List<TopicSection>
	 */
	public List<TopicSection> getTopicSectionList(TopicSectionVo topicSectionVo);
	
	
	/**
	 * 
	* @Title: getTopicSectionPostList 
	* @param: 
	* @Description: 获取话题详情  动态列表
	* @return TopicSectionBo
	 */
	public TopicSectionBo getTopicSectionPostList(TopicSectionPostListVo topicSectionPostListVo);
	
}
