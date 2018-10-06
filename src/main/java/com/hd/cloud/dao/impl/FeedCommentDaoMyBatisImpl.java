package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.dao.FeedCommentDao;
import com.hd.cloud.dao.mapper.FeedCommentMapper;
import com.hd.cloud.vo.FeedCommentVo;

@Repository
public class FeedCommentDaoMyBatisImpl implements FeedCommentDao{
	
	@Inject
	private FeedCommentMapper feedCommentMapper;

	@Override
	public List<FeedComment> getFeedCommentlist(FeedCommentVo feedCommentVo) {
		return feedCommentMapper.getFeedCommentlist(feedCommentVo);
	}

	@Override
	public int save(FeedComment comment) {
		return feedCommentMapper.save(comment);
	}

	@Override
	public int update(FeedComment feedComment) {
		return feedCommentMapper.update(feedComment);
	}

}
