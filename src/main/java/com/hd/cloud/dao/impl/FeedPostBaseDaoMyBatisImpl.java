package com.hd.cloud.dao.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.dao.mapper.FeedPostBaseMapper;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.TopicSectionPostListVo;

/**
 * 
 * @ClassName: FeedPostBaseDaoMyBatisImpl
 * @Description: 动态
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午12:08:10
 *
 */
@Repository
public class FeedPostBaseDaoMyBatisImpl implements FeedPostBaseDao {

	@Inject
	private FeedPostBaseMapper feedPostBaseMapper;

	@Override
	public FeedPostBase getFeedPostBaseById(int id) {
		return feedPostBaseMapper.getFeedPostBaseById(id);
	}

	@Override
	public int save(FeedPostBase feedPostBase) {
		return feedPostBaseMapper.save(feedPostBase);
	}

	@Override
	public int update(FeedPostBase feedPostBase) {
		return feedPostBaseMapper.update(feedPostBase);
	}

	@Override
	public List<FeedPostBaseBo> getFeedPostBaseList(FeedPostBaseVo feedPostBaseVo) {
		return feedPostBaseMapper.getFeedPostBaseList(feedPostBaseVo);
	}

	@Override
	public List<FeedPostBaseBo> getFeedPostBaseListByIds(List<Integer> ids) {
		return feedPostBaseMapper.getFeedPostBaseListByIds(ids);
	}

	@Override
	public List<Map<String, Object>> getgetFewDynamicByTopicId(int topicId) {
		return feedPostBaseMapper.getgetFewDynamicByTopicId(topicId);
	}

	@Override
	public List<FeedPostBaseBo> getFeedPostBaseListByTopicSectionPostListVo(
			TopicSectionPostListVo topicSectionPostListVo) {
		return feedPostBaseMapper.getFeedPostBaseListByTopicSectionPostListVo(topicSectionPostListVo);
	}

}
