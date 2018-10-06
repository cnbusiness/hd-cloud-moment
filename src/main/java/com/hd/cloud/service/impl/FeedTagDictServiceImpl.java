package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.bo.FeedTagDictBo;
import com.hd.cloud.dao.FeedTagDictDao;
import com.hd.cloud.service.FeedTagDictService;

@Service
public class FeedTagDictServiceImpl implements FeedTagDictService{

	@Inject
	private FeedTagDictDao feedTagDictDao;
	
	@Override
	public List<FeedTagDictBo> getFeedTagDictList() {
		return feedTagDictDao.getFeedTagDictList();
	}

}
