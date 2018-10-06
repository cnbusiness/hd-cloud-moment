package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.Ad;
import com.hd.cloud.bo.AdClick;
import com.hd.cloud.dao.AdDao;
import com.hd.cloud.dao.mapper.AdMapper;
import com.hd.cloud.vo.BannerVo;

/**
 * 
 * @ClassName: AdDaoMyBatisImpl
 * @Description: ad持久层实现
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:29:42
 *
 */
@Repository
public class AdDaoMyBatisImpl implements AdDao {

	// ad持久层映射
	@Inject
	private AdMapper adMapper;

	// 根据id获取ad
	@Override
	public Ad getAdById(long id) {
		return adMapper.getAdInfoById(id);
	}

	// 根据ids获取ad集合
	@Override
	public List<Ad> getAdByIds(String ids) {
		return adMapper.getAdInfoByIds(ids);
	}

	// 保存ad
	@Override
	public int saveClickAd(AdClick adClick) {
		return adMapper.saveClickAd(adClick);
	}

	@Override
	public List<Ad> getAdByCode(BannerVo bannerVo) {
		return adMapper.getAdByCode(bannerVo);
	}

	@Override
	public int update(Ad ad) {
		return adMapper.update(ad);
	}

}
