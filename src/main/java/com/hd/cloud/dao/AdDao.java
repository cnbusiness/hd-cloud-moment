package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.Ad;
import com.hd.cloud.bo.AdClick;
import com.hd.cloud.vo.BannerVo;

/**
 * 
 * @ClassName: AdDao
 * @Description: 广告管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:27:33
 *
 */
public interface AdDao {

	/**
	 * 
	 * @Title: getAdById
	 * @param: long
	 *             id
	 * @Description: 通过广告id获取广告信息
	 * @return Ad
	 */
	public Ad getAdById(long id);

	/**
	 * 
	 * @Title: getAdByIds
	 * @param: String
	 *             ids
	 * @Description: 通过id串获取广告集合
	 * @return List<Ad>
	 */
	public List<Ad> getAdByIds(String ids);

	/**
	 * 保存点击记录
	 * 
	 * @param ad
	 * @return
	 */
	public int saveClickAd(AdClick adClick);

	/**
	 * 根据位置差对应的广告
	 * 
	 * @param ad
	 * @return
	 */
	public List<Ad> getAdByCode(BannerVo bannerVo);

	/**
	 * 更新游览量和点击量
	 * 
	 * @param ad
	 * @return
	 */
	public int update(Ad ad);

}
