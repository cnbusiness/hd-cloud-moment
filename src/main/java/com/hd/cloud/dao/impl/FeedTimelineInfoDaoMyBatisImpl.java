package com.hd.cloud.dao.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedTimelineInfo;
import com.hd.cloud.dao.FeedTimelineInfoDao;
import com.hd.cloud.dao.mapper.FeedTimelineInfoMapper;

@Repository
public class FeedTimelineInfoDaoMyBatisImpl implements FeedTimelineInfoDao{

	@Inject
	private FeedTimelineInfoMapper feedTimelineInfoMapper;
	
	@Override
	public int save(FeedTimelineInfo feedTimelineInfo) {
		return feedTimelineInfoMapper.save(feedTimelineInfo);
	}

}
