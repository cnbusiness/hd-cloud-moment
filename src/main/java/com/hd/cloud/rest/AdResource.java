package com.hd.cloud.rest;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.BannerBo;
import com.hd.cloud.service.AdModuleService;
import com.hd.cloud.util.ConstantUtil;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

/**
 * 
 * @ClassName: AdResource
 * @Description: 广告管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:50:25
 *
 */
@RefreshScope
@RestController
@RequestMapping("ad")
public class AdResource extends RestBase {

	@Inject
	private AdModuleService adModuleService;

	/**
	 * 
	 * @Title: getBanner
	 * @param:
	 * @Description: 获取banner信息
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/banners", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getBanner(@QueryParam("countryCode") String countryCode, @QueryParam("cityId") Long cityId,
			@QueryParam("bannerCode") String bannerCode) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		countryCode = countryCode == null ? "CN" : countryCode;
		cityId = cityId == null ? 0 : cityId;
		// 查询城市对应的banner列表
		BannerBo adData = adModuleService.getAdDataByIdAndCity(bannerCode, cityId, ConstantUtil.APP_BANNER_TYPE,
				countryCode);
		boUtil.setData(adData);
		return boUtil;
	}
}
