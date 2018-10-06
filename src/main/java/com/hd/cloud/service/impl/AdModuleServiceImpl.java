package com.hd.cloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hd.cloud.bo.Ad;
import com.hd.cloud.bo.AdClick;
import com.hd.cloud.bo.BannerBo;
import com.hd.cloud.bo.InitPageBo;
import com.hd.cloud.dao.AdDao;
import com.hd.cloud.service.AdModuleService;
import com.hd.cloud.util.RedisKeyUtils;
import com.hd.cloud.vo.BannerVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: AdModuleServiceImpl
 * @Description: ad模块广告service实现
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:35:31
 *
 */
@Slf4j
@Service
public class AdModuleServiceImpl implements AdModuleService {

	@Inject
	private AdDao adDao;

	@Inject
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public BannerBo getAdDataByIdAndCity(String bannerCode, Long cityCode, int type, String countryCode) {
		log.info("getAdDataByIdAndCity.bannerCode:{}", bannerCode);

		BannerBo banner = BannerBo.builder().build();
		List<Ad> list = getAdList(bannerCode, cityCode, type, countryCode);
		banner.setAd(list);
		if (list.size() > 0) {
			// 统计游览量
			for (Ad ad : list) {
				RedisKeyUtils.setAdRedisByCount(redisTemplate, ad.getId(), RedisKeyUtils.watchCnt);
			}
			return banner;
		} else {
			return null;
		}
	}

	@Override
	public List<InitPageBo> getInitPageByCity(String bannerCode, Long cityCode, String countryCode) {
		List<Ad> list = getAdList(bannerCode, cityCode, 0, countryCode);
		List<InitPageBo> adList = new ArrayList<InitPageBo>();
		for (int i = 0; i < list.size(); i++) {
			Ad ad = list.get(i);
			adList.add(InitPageBo.builder().id(ad.getId()).picUrl(ad.getPicPath())
					.picName(new File(ad.getPicPath()).getName()).pageVersion(ad.getUpdateTime().getTime()).build());
		}
		return adList;
	}

	@Override
	public List<InitPageBo> getNewInitPageByCity(String bannerCode, Long cityCode, long pageVersion,
			String countryCode) {

		List<Ad> list = getAdList(bannerCode, cityCode, 0, countryCode);

		List<InitPageBo> adList = new ArrayList<InitPageBo>();
		for (int i = 0; i < list.size(); i++) {
			Ad ad = list.get(i);
			adList.add(InitPageBo.builder().id(ad.getId()).picUrl(ad.getPicPath())
					.picName(new File(ad.getPicPath()).getName()).pageVersion(ad.getUpdateTime().getTime()).build());
		}
		return adList;

	}

	public List<Ad> getAdList(String bannerCode, Long cityCode, int type, String countryCode) {
		// 查询条件
		BannerVo bannerVo = BannerVo.builder().build();
		bannerVo.setCityId(cityCode);
		bannerVo.setBannerCode(bannerCode);
		bannerVo.setShowLevel(1);
		// 先查询对应的城市banner
		log.info("**************getAdList.bannerVo:{}", bannerVo);
		List<Ad> list = adDao.getAdByCode(bannerVo);
		// 如果对应的城市查询不到查询全国
		if (list.size() == 0) {
			bannerVo.setCityId(null);
			bannerVo.setShowLevel(2);
			bannerVo.setCountryCode(countryCode);
			log.info("**************getAdList.bannerVo:{}", bannerVo);
			list = adDao.getAdByCode(bannerVo);
		}
		// 如果对应全国查询不到
		if (list.size() == 0) {
			bannerVo.setCityId(null);
			bannerVo.setShowLevel(3);
			bannerVo.setCountryCode(null);
			log.info("**************getAdList.bannerVo:{}", bannerVo);
			list = adDao.getAdByCode(bannerVo);
		}
		return list;
	}

	@Override
	public int saveClickAd(AdClick adClick) {
		int i = adDao.saveClickAd(adClick);
		// 设置点击量
		RedisKeyUtils.setAdRedisByCount(redisTemplate, adClick.getAdId(), RedisKeyUtils.visitCnt);
		return i;
	}

	@Override
	public void setAdDynamic() {
		RedisKeyUtils.setAdRedisToMySql(redisTemplate, adDao);
	}

}
