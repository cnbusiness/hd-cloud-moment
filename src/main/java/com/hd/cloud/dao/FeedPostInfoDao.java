package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.PostInfoBo;
import com.hd.cloud.vo.PostInfoVo;

/**
 * 
  * @ClassName: FeedPostInfoDao
  * @Description: 消息中心管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月10日 上午10:05:12
  *
 */
public interface FeedPostInfoDao {
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存 
	* @return int
	 */
	public int save(FeedPostInfo feedPostInfo);
	
	
	/**
	 * 
	* @Title: update 
	* @param: 
	* @Description: 编辑
	* @return int
	 */
	public int update(FeedPostInfo feedPostInfo);
	
	/**
	 * 
	* @Title: getPostInfoList 
	* @param: 
	* @Description: 查询消息列表
	* @return List<PostInfoBo>
	 */
	public List<PostInfoBo> getPostInfoList(PostInfoVo postInfoVo);
	
	/**
	 * 
	* @Title: deleteByPostId 
	* @param: 
	* @Description: 删除动态的消息
	* @return int
	 */
	public int deleteByPostId(int postId);
}
