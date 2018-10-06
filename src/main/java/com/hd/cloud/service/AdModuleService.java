package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.AdClick;
import com.hd.cloud.bo.BannerBo;
import com.hd.cloud.bo.InitPageBo;

/**
 * 
 * @ClassName: AdModuleService
 * @Description: 广告管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:33:30
 *
 */
public interface AdModuleService {

	/**
	 * 
	 * @Title: getAdDataByIdAndCity
	 * @param: String
	 *             bannerCode 广告模块特定码
	 * @param: long
	 *             cityCode
	 * @param: int
	 *             type
	 * @Description: 通过广告模块，城市名和类型获取Banner数据
	 * @return BannerBo
	 */
	public BannerBo getAdDataByIdAndCity(String bannerCode, Long cityCode, int type, String countryCode);

	/**
	 * 
	 * @Title: getInitPageByCity
	 * @param: String
	 *             bannerCode 对应广告吗
	 * @param: int
	 *             cityCode 城市码
	 * @Description: 通过城市code获取对应城市的有效的启动页数据
	 * @return InitPageBo
	 */
	public List<InitPageBo> getInitPageByCity(String bannerCode, Long cityCode, String countryCode);

	/**
	 * 
	 * @Title: getNewInitPageByCity
	 * @param: String
	 *             bannerCode 对应广告吗
	 * @param: int
	 *             cityCode 城市码
	 * @Description: 通过城市code获取对应城市的有效的启动页数据
	 * @return InitPageBo
	 */
	public List<InitPageBo> getNewInitPageByCity(String bannerCode, Long cityCode, long pageVersion,
			String countryCode);

	/**
	 * 保存点击记录
	 * 
	 * @param ad
	 * @return
	 */
	public int saveClickAd(AdClick adClick);

	/**
	 * 设置图片的动态游览数和点击量
	 * 
	 * @param ad
	 * @return
	 */
	public void setAdDynamic();

}