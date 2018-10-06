package com.hd.cloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.bo.TopicCount;
import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.bo.TopicSectionBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.dao.TopicSectionDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.service.FeedPostBaseService;
import com.hd.cloud.service.TopicSectionService;
import com.hd.cloud.vo.TopicSectionPostListVo;
import com.hd.cloud.vo.TopicSectionVo;

@Service
public class TopicSectionServiceImpl implements TopicSectionService {

	@Inject
	private TopicSectionDao topicSectionDao;

	@Inject
	private FeedPostBaseDao feedPostBaseDao;

	@Autowired
	private AccountClient accountClient;
	
	@Inject
	private FeedPostBaseCache feedPostBaseCache;
	
	@Inject
	private FeedPostBaseService feedPostBaseService;

	@Override
	public List<TopicSection> getHotTopicSectionList() {
		TopicSectionVo topicSectionVo = TopicSectionVo.builder().offset(0).pageSize(3).build();
		List<TopicSection> topicSections = topicSectionDao.getTopicSectionList(topicSectionVo);
		for (TopicSection topicSection : topicSections) {
			List<Map<String, Object>> dynamicList = feedPostBaseDao.getgetFewDynamicByTopicId(topicSection.getId());
			for (Map<String, Object> map : dynamicList) {
				UserProfile userProfile = accountClient.getUserProfileByUserId(Long.valueOf(map.get("userId").toString()));
				if (userProfile != null) {
					map.put("name", userProfile.getNickName());
				}
			}
			topicSection.setDynamicList(dynamicList);
			TopicCount topicCount= feedPostBaseCache.getTopicCount(topicSection.getId());
			topicSection.setStarCount(topicCount.getStarCount().intValue());
			topicSection.setDynamicCount(topicCount.getDynamicCount().intValue());
			topicSection.setFavoritesCnt(topicCount.getFavoritesCnt().intValue());
			topicSection.setViewCount(topicCount.getViewCount().intValue());
		}
		return topicSections;
	}

	@Override
	public List<TopicSection> getTopicSectionList(TopicSectionVo topicSectionVo) {
		List<TopicSection> topicSections = topicSectionDao.getTopicSectionList(topicSectionVo);
		for (TopicSection topicSection : topicSections) {
			TopicCount topicCount= feedPostBaseCache.getTopicCount(topicSection.getId());
			topicSection.setStarCount(topicCount.getStarCount().intValue());
			topicSection.setDynamicCount(topicCount.getDynamicCount().intValue());
			topicSection.setFavoritesCnt(topicCount.getFavoritesCnt().intValue());
			topicSection.setViewCount(topicCount.getViewCount().intValue()); 
		}
		return topicSections;
	}

	@Override
	public TopicSectionBo getTopicSectionPostList(TopicSectionPostListVo topicSectionPostListVo) {
		TopicSectionBo topicSectionBo=null;
		TopicSection topicSection=topicSectionDao.getTopicSectionById(topicSectionPostListVo.getTopicId());
		//查询话题下的动态
		List<FeedPostBaseBo> dynamicList= feedPostBaseService.getFeedPostBaseListByTopicSectionPostListVo(topicSectionPostListVo);
		if(topicSection!=null) {
			topicSectionBo=TopicSectionBo.builder().build();
			topicSectionBo.setTopicId(topicSection.getId());
			topicSectionBo.setName(topicSection.getName());
			topicSectionBo.setDesc(topicSection.getDesc());
			topicSectionBo.setIconUrl(topicSection.getIconUrl());
			topicSectionBo.setBackgroudUrl(topicSection.getBackgroudUrl());
			topicSectionBo.setHomeUrl(topicSection.getHomeUrl());
			topicSectionBo.setDynamicList(dynamicList);
		}
		return topicSectionBo;
	}

}
