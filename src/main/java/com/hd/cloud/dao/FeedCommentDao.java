package com.hd.cloud.dao;
/**
 * 
  * @ClassName: FeedCommentDao
  * @Description: 评论管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月9日 下午4:14:08
  *
 */

import java.util.List;

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.vo.FeedCommentVo;

public interface FeedCommentDao {

	/**
	 * 
	 * @Title: getFeedCommentlist
	 * @param:
	 * @Description: 列表
	 * @return List<FeedComment>
	 */
	public List<FeedComment> getFeedCommentlist(FeedCommentVo feedCommentVo);

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return int
	 */
	public int save(FeedComment comment);
	
	/**
	 * 
	* @Title: update 
	* @param: 
	* @Description: 编辑
	* @return int
	 */
	public int update(FeedComment feedComment);

}
