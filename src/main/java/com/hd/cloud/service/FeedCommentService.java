package com.hd.cloud.service;


import java.util.List;

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.bo.FeedCommentBo;
import com.hd.cloud.vo.FeedCommentVo;
import com.hlb.cloud.bo.BoUtil;
/**
 * 
  * @ClassName: FeedCommentService
  * @Description: 评论管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月9日 下午4:14:08
  *
 */
public interface FeedCommentService {

	/**
	 * 
	 * @Title: getFeedCommentlist
	 * @param:
	 * @Description: 列表
	 * @return List<FeedComment>
	 */
	public List<FeedCommentBo> getFeedCommentlist(FeedCommentVo feedCommentVo);

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return BoUtil
	 */
	public BoUtil save(FeedComment comment);
	
	/**
	 * 
	* @Title: update 
	* @param: 
	* @Description: 编辑
	* @return int
	 */
	public int update(FeedComment feedComment);

}
