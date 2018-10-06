package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.bo.FeedPostRes;
import com.hd.cloud.dao.FeedPostResDao;
import com.hd.cloud.service.FeedPostResService;

@Service
public class FeedPostResServiceImpl implements FeedPostResService{

	@Inject
	private FeedPostResDao feedPostResDao;
	
	@Override
	public FeedPostRes getFeedPostResById(int id) {
		return feedPostResDao.getFeedPostResById(id);
	}

	@Override
	public List<FeedPostRes> getFeedPostResListByPostId(int postId) {
		return feedPostResDao.getFeedPostResListByPostId(postId);
	}

	@Override
	public int save(FeedPostRes feedPostRes) {
		return feedPostResDao.save(feedPostRes);
	}

	@Override
	public int update(FeedPostRes feedPostRes) {
		return feedPostResDao.update(feedPostRes);
	}

}
