package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedPostRes;
import com.hd.cloud.dao.FeedPostResDao;
import com.hd.cloud.dao.mapper.FeedPostResMapper;

@Repository
public class FeedPostResDaoMyBatisImpl implements FeedPostResDao{

	@Inject
	private FeedPostResMapper feedPostResMapper;
	
	@Override
	public FeedPostRes getFeedPostResById(int id) {
		return feedPostResMapper.getFeedPostResById(id);
	}

	@Override
	public List<FeedPostRes> getFeedPostResListByPostId(int postId) {
		return feedPostResMapper.getFeedPostResListByPostId(postId);
	}

	@Override
	public int save(FeedPostRes feedPostRes) {
		return feedPostResMapper.save(feedPostRes);
	}

	@Override
	public int update(FeedPostRes feedPostRes) {
		return feedPostResMapper.update(feedPostRes);
	}

	@Override
	public String getIndexPicture(int postId) {
		return feedPostResMapper.getIndexPicture(postId);
	}

}
