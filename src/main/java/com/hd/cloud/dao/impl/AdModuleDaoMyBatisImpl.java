package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.AdJoinDataBo;
import com.hd.cloud.bo.InitPageBo;
import com.hd.cloud.dao.AdModuleDao;
import com.hd.cloud.dao.mapper.AdModuleMapper;

/**
 * 
 * @ClassName: AdModuleDaoMyBatisImpl
 * @Description: AdModule持久层实现
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:31:55
 *
 */
@Repository
public class AdModuleDaoMyBatisImpl implements AdModuleDao {

	// AdModule持久映射
	@Inject
	private AdModuleMapper adModuleMapper;

	// 获取Banner分组
	@Override
	public List<AdJoinDataBo> getBannerModuleById(String bannerCode, long cityId, int type) {
		return adModuleMapper.getBannerModuleById(bannerCode, cityId, type);
	}

	// 用户启动页查询
	@Override
	public List<AdJoinDataBo> getInitPageByCityCode(String bannerCode, long cityId) {
		return adModuleMapper.getInitPageModuleByCityId(bannerCode, cityId);
	}

	//  用户默认启动页查询
	@Override
	public List<AdJoinDataBo> getDefaultInitPage(String bannerCode) {
		return adModuleMapper.getDefaultInitPageModule(bannerCode);
	}

	// 商家启动页查询
	@Override
	public List<InitPageBo> getNewInitPageByCityCode(String bannerCode, long cityId) {
		return adModuleMapper.getNewInitPageModuleByCityId(bannerCode, cityId);
	}

	// 商家默认启动页查询
	@Override
	public List<InitPageBo> getNewDefaultInitPage(String bannerCode) {
		return adModuleMapper.getNewDefaultInitPageModule(bannerCode);
	}
}
