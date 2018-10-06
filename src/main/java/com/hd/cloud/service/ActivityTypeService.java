package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.ActivityTypeBo;

/**
 * 
 * @ClassName: ActivityTypeService
 * @Description: 活动类型标签
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:21:15
 *
 */
public interface ActivityTypeService {

	/**
	 * 
	 * @Title: getActivityTypeList
	 * @param: String
	 *             countryCode
	 * @Description: 根据国家简码获取活动类型列表
	 * @return
	 */
	List<ActivityTypeBo> getActivityTypeList(String countryCode);
}
