package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.AdJoinDataBo;
import com.hd.cloud.bo.InitPageBo;

/**
 * 
 * @ClassName: AdModuleDao
 * @Description: 广告分类模块
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:29:57
 *
 */
public interface AdModuleDao {

	/**
	 * 
	 * @Title: getBannerModuleById
	 * @param: String
	 *             bannerCode
	 * @param: long
	 *             cityId
	 * @param: int
	 *             type
	 * @Description: 通过bannerCode获取具体banner模块信息
	 * @return List<AdJoinDataBo>
	 */
	public List<AdJoinDataBo> getBannerModuleById(String bannerCode, long cityId, int type);

	/**
	 * 
	 * @Title: getInitPageByCityCode
	 * @param: String
	 *             bannerCode
	 * @param: long
	 *             cityId
	 * @Description: 通过城市code获取对应城市的启动页数据
	 * @return List<AdJoinDataBo>
	 */
	public List<AdJoinDataBo> getInitPageByCityCode(String bannerCode, long cityId);

	/**
	 * 
	 * @Title: getDefaultInitPage
	 * @param: String
	 *             bannerCode
	 * @Description: 获取默认广告
	 * @return List<AdJoinDataBo>
	 */
	public List<AdJoinDataBo> getDefaultInitPage(String bannerCode);

	/**
	 * 
	 * @Title: getNewInitPageByCityCode
	 * @param: String
	 *             bannerCode
	 * @param: long
	 *             cityId
	 * @Description: 通过城市code获取对应城市的启动页数据
	 * @return List<AdJoinDataBo>
	 */
	public List<InitPageBo> getNewInitPageByCityCode(String bannerCode, long cityId);

	/**
	 * 
	 * @Title: getNewDefaultInitPage
	 * @param: String
	 *             bannerCode
	 * @Description: 获取默认广告
	 * @return List<AdJoinDataBo>
	 */
	public List<InitPageBo> getNewDefaultInitPage(String bannerCode);
}
