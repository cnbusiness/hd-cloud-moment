package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.PostInfoBo;
import com.hd.cloud.vo.PostInfoVo;

/**
 * 
  * @ClassName: FeedPostInfoService
  * @Description: 消息中心管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月10日 上午10:05:12
  *
 */
public interface FeedPostInfoService {
	
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
}
