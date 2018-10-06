package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.ActivityCommentBo;
import com.hd.cloud.vo.ActivityCommentVo;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: ActivityCommentService
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:16:35
 *
 */
public interface ActivityCommentService {

	/**
	 * 
	 * @Title: addActivityComment
	 * @param: ActivityCommentVo
	 *             activityCommentVo
	 * @Description: 新增活动评论
	 * @return
	 */
	BoUtil addActivityComment(ActivityCommentVo activityCommentVo, long userId);

	/**
	 * 
	 * @Title: getAllComments
	 * @param: int
	 *             type,int activityId,int pageIndex,int pageSize
	 * @Description: 获取活动评论列表
	 * @return List<ActivityCommentBo>
	 */
	List<ActivityCommentBo> getAllComments(int type, int activityId, int pageIndex, int pageSize);
}
