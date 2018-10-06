package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.ActivityJoinBo;
import com.hd.cloud.service.ActivityBaseService;
import com.hd.cloud.service.ActivityJoinService;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.StringUtil;
import com.hd.cloud.vo.ActivityIdVo;
import com.hd.cloud.vo.ActivitySignUpVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityJoinResource
 * @Description: 活动报名
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午5:22:54
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("activityjoin")
public class ActivityJoinResource extends RestBase {

	@Inject
	private ActivityBaseService activityBaseService;

	@Inject
	private ActivityJoinService activityJoinService;

	/**
	 * 
	 * @Title: signUpActivity
	 * @param:
	 * @Description: 报名活动
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "signUpActivity", notes = "signUpActivity")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil signUpActivity(final @RequestBody ActivitySignUpVo payload) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		long userId = super.getLoginUserID();
		int activityId = payload.getActivityId();
		String phone = payload.getPhone();
		String explain = payload.getExplain();
		log.debug("@payload{}", payload);
		log.debug("**********************参数验证开始***************************");
		if (activityId == 0) {// 活动Id非空验证
			boUtil.setCode(ErrorCode.ACTIVITYID_IS_EMPTY);
			return boUtil;
		}
		log.debug("**********************验证该活动报名是否需要填写手机号码开始***************************");
		Integer isNeedPhoneType = activityBaseService.checkActivityRequire(activityId);
		log.debug("**********************验证该活动报名是否需要填写手机号码结束***************************", isNeedPhoneType);
		if (isNeedPhoneType != null) {
			if (isNeedPhoneType.intValue() == 1) {// ==1代表报名需要填写手机号码 1.新版本
				if (StringUtil.isBlank(phone)) {// 报名手机号码非空验证
					boUtil.setCode(ErrorCode.PHONE_IS_EMPTY);
					return boUtil;
				}
				if (!StringUtil.isNumeric(phone.trim())) {// 报名手机号码格式错误
					boUtil.setCode(ErrorCode.PHONE_FORMAT_ERROR);
					return boUtil;
				}
				if (StringUtil.getWordCountRegex(explain) > 30) {// 报名说明长度不能超过30
					boUtil.setCode(ErrorCode.LENGTH_OF_EXPLAIN);
					return boUtil;
				}
			}
		}
		log.debug("**********************参数验证结束***************************");
		return activityJoinService.signUpActivity(payload, userId);
	}

	/**
	 * 
	 * @Title: cancleSignUpActivity
	 * @param:
	 * @Description: 取消报名
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "PUT", value = "cancleSignUpActivity", notes = "cancleSignUpActivity")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public BoUtil cancleSignUpActivity(final @RequestBody ActivityIdVo payload) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		long userId = super.getLoginUserID();
		log.debug("@payload{},@loginUserId", payload, userId);
		int activityId = payload.getActivityId();
		log.debug("**********************参数验证开始***************************");
		if (activityId == 0) {// 活动Id非空验证
			boUtil.setCode(ErrorCode.ACTIVITYID_IS_EMPTY);
			return boUtil;
		}
		log.debug("**********************参数验证结束***************************");
		return activityJoinService.cancelSignUpActivity(activityId, userId);
	}

	/**
	 * 
	 * @Title: getActivityJoinList
	 * @param:
	 * @Description: 获取活动报名人数列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/alljoins", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getActivityJoinList(@QueryParam("activityId") Integer activityId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		long userId = super.getLoginUserID();
		activityId = activityId == null ? 0 : activityId;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		if (activityId == 0) {// 活动Id非空验证
			boUtil.setCode(ErrorCode.ACTIVITYID_IS_EMPTY);
			return boUtil;
		}
		log.debug("**********************参数验证结束***************************");

		log.debug("**********************调用活动报名人列表Service层开始***************************");
		List<ActivityJoinBo> activityJoinList = activityJoinService.getActivityJoinList(activityId, offset, pageSize, 1,
				userId);
		log.debug("**********************调用活动报名人列表Service层结束***************************", activityJoinList);
		boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(activityJoinList);
		return boUtil;
	}
}
