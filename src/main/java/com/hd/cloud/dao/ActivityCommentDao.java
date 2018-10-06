package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.ActivityComment;

/**
 * 
 * @ClassName: ActivityCommentDao
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:52:30
 *
 */
public interface ActivityCommentDao {

	/**
	 * 
	 * @Title: addActivityComment
	 * @param: ActivityComment
	 *             activityComment
	 * @Description: 新增活动评论
	 * @return
	 */
	void addActivityComment(ActivityComment activityComment);

	/**
	 * 
	 * @Title: getAllComments
	 * @param: int
	 *             type,int activityId,int pageIndex,int pageSize
	 * @Description: 获取活动评论列表
	 * @return List<ActivityComment>
	 */
	List<ActivityComment> getAllComments(int type, int activityId, int pageIndex, int pageSize);
}
