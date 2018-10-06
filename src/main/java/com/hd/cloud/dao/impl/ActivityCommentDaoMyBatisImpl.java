package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityComment;
import com.hd.cloud.dao.ActivityCommentDao;
import com.hd.cloud.dao.mapper.ActivityCommentMapper;

/**
 * 
 * @ClassName: ActivityCommentDaoMyBatisImpl
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:56:25
 *
 */
@Repository
public class ActivityCommentDaoMyBatisImpl implements ActivityCommentDao {

	@Inject
	private ActivityCommentMapper activityCommentMapper;

	@Override
	public void addActivityComment(ActivityComment activityComment) {
		activityCommentMapper.addActivityComment(activityComment);
	}

	@Override
	public List<ActivityComment> getAllComments(int type, int activityId, int pageIndex, int pageSize) {
		return activityCommentMapper.getAllComments(type, activityId, pageIndex, pageSize);
	}

}
