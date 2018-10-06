package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.PostInfoBo;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.dao.mapper.FeedPostInfoMapper;
import com.hd.cloud.vo.PostInfoVo;

@Repository
public class FeedPostInfoDaoMyBatisImpl implements FeedPostInfoDao{
	
	@Inject
	private FeedPostInfoMapper feedPostInfoMapper;

	@Override
	public int save(FeedPostInfo feedPostInfo) {
		return feedPostInfoMapper.save(feedPostInfo);
	}

	@Override
	public int update(FeedPostInfo feedPostInfo) {
		return feedPostInfoMapper.update(feedPostInfo);
	}

	@Override
	public List<PostInfoBo> getPostInfoList(PostInfoVo postInfoVo) {
		return feedPostInfoMapper.getPostInfoList(postInfoVo);
	}

	@Override
	public int deleteByPostId(int postId) {
		return feedPostInfoMapper.deleteByPostId(postId);
	}

}
