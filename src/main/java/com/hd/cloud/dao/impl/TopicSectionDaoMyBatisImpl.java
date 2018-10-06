package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.dao.TopicSectionDao;
import com.hd.cloud.dao.mapper.TopicSectionMapper;
import com.hd.cloud.vo.TopicSectionVo;

@Repository
public class TopicSectionDaoMyBatisImpl implements TopicSectionDao{

	@Inject
	private TopicSectionMapper topicSectionMapper;
	
	@Override
	public int save(TopicSection topicSection) {
		return topicSectionMapper.save(topicSection);
	}

	@Override
	public int update(TopicSection topicSection) {
		return topicSectionMapper.update(topicSection);
	}

	@Override
	public List<TopicSection> getTopicSectionList(TopicSectionVo topicSectionVo) {
		return topicSectionMapper.getTopicSectionList(topicSectionVo);
	}

	@Override
	public TopicSection getTopicSectionById(int topicId) {
		return topicSectionMapper.getTopicSectionById(topicId);
	}

	@Override
	public TopicSection getTopicSectionByName(String name) {
		return topicSectionMapper.getTopicSectionByName(name);
	}

}
