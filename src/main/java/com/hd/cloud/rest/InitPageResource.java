package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.InitPageBo;
import com.hd.cloud.service.AdModuleService;
import com.hd.cloud.util.ConstantUtil;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: InitPageResource
 * @Description: 启动也
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:43:21
 *
 */
@RefreshScope
@RestController
@RequestMapping("initpage/nofilter")
public class InitPageResource {

	@Inject
	private AdModuleService adModuleService;

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getLatestInitPage(@QueryParam("cityId") Long cityId, @QueryParam("countryCode") String countryCode) {
		cityId = cityId == null ? 0 : cityId;
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<InitPageBo> initPageBo = adModuleService.getInitPageByCity(ConstantUtil.INIT_PAGE_CODE, cityId,
				countryCode);
		boUtil.setData(initPageBo);
		return boUtil;
	}
}
