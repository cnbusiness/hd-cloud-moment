package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedTagDictBo;
import com.hd.cloud.dao.FeedTagDictDao;
import com.hd.cloud.dao.mapper.FeedTagDictMapper;

@Repository
public class FeedTagDictDaoMyBatisImpl implements FeedTagDictDao{

	@Inject
	private FeedTagDictMapper feedTagDictMapper;
	
	@Override
	public List<FeedTagDictBo> getFeedTagDictList() {
		return feedTagDictMapper.getFeedTagDictList();
	}

}
