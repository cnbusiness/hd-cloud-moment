package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.vo.TopicSectionVo;

/**
 * 
 * @ClassName: TopicSectionDao
 * @Description: 话题
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午5:06:02
 *
 */
public interface TopicSectionDao {

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存话题
	 * @return int
	 */
	public int save(TopicSection topicSection);

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑话题
	 * @return int
	 */
	public int update(TopicSection topicSection);
	
	/**
	 * 
	* @Title: getTopicSectionList 
	* @param: 
	* @Description: 获取话题列表
	* @return List<TopicSection>
	 */
	public List<TopicSection> getTopicSectionList(TopicSectionVo topicSectionVo);
	
	/**
	 * 
	* @Title: getTopicSectionById 
	* @param: 
	* @Description: 通过话题id查询话题详情
	* @return TopicSection
	 */
	public TopicSection getTopicSectionById(int topicId);
	
	
	/**
	 * 
	* @Title: getTopicSectionByName 
	* @param: 
	* @Description:   通过话题名称查询话题详情
	* @return TopicSection
	 */
	public TopicSection getTopicSectionByName(String name);
}
