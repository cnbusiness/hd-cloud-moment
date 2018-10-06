package com.hd.cloud.dao;
/**
 * 
  * @ClassName: PartyActivityTypeDao
  * @Description: 活动类型管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月12日 下午2:13:04
  *
 */

import java.util.List;

import com.hd.cloud.bo.ActivityTypeBo;

public interface ActivityTypeDao {

	/**
	 * 
	 * @Title: getPartyActivityTypeList
	 * @param:
	 * @Description: 列表
	 * @return List<PartyActivityType>
	 */
	public List<ActivityTypeBo> getPartyActivityTypeList(String countryCode);
}
