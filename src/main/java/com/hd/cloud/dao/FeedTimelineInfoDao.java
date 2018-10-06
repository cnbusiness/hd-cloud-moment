package com.hd.cloud.dao;

import com.hd.cloud.bo.FeedTimelineInfo;

/**
 * 
  * @ClassName: FeedTimelineInfoDao
  * @Description: 动态指定用户表
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月12日 上午11:04:01
  *
 */
public interface FeedTimelineInfoDao {

	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存 
	* @return int
	 */
	public int save(FeedTimelineInfo feedTimelineInfo);
}
