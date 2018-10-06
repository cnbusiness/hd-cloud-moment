package com.hd.cloud.rest;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.service.ActivityTypeService;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

/**
 * 
 * @ClassName: ActivityTypeResource
 * @Description: 活动类型
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月24日 下午3:17:49
 *
 */

@RefreshScope
@RestController
@RequestMapping("activitytype")
public class ActivityTypeResource extends RestBase {

	@Inject
	private ActivityTypeService activityTypeService;

	/**
	 * 
	 * @Title: getActivityType
	 * @param:
	 * @Description: 活动类型列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getActivityType(@QueryParam("countryCode") String countryCode) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		if (StringUtils.isBlank(countryCode)) {
			countryCode = "CN";
		}
		boUtil.setData(activityTypeService.getActivityTypeList(countryCode));
		return boUtil;
	}
}
