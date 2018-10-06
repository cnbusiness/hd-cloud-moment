package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.FeedTagDictBo;

/**
 * 
 * @ClassName: FeedTagDictService
 * @Description: 标签管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午3:54:30
 *
 */
public interface FeedTagDictService {

	/**
	 * 
	 * @Title: getFeedTagDictList
	 * @param:
	 * @Description: 列表
	 * @return List<FeedTagDictBo>
	 */
	public List<FeedTagDictBo> getFeedTagDictList();
}
