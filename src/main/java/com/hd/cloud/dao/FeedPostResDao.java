package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.FeedPostRes;

/**
 * 
 * @ClassName: FeedPostResDao
 * @Description: 动态资源管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午4:45:45
 *
 */
public interface FeedPostResDao {

	/**
	 * 
	 * @Title: getFeedPostResById
	 * @param:
	 * @Description: 根据id查询详情
	 * @return FeedPostRes
	 */
	public FeedPostRes getFeedPostResById(int id);
	
	/**
	 * 
	* @Title: getFeedPostResListByPostId 
	* @param: 
	* @Description: 根据动态id查询资源列表
	* @return List<FeedPostRes>
	 */
	public List<FeedPostRes> getFeedPostResListByPostId(int postId);
	
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存
	* @return int
	 */
	public int save(FeedPostRes feedPostRes);
	
	
	/**
	 * 
	* @Title: update 
	* @param: 
	* @Description: 编辑
	* @return int
	 */
	public int update(FeedPostRes feedPostRes);
	
	/**
	 * 
	* @Title: getIndexPicture 
	* @param: 
	* @Description: 获取首页图片
	* @return String
	 */
	public String getIndexPicture(int postId);
}
