package com.hd.cloud.rest;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.UserPush;
import com.hd.cloud.service.PushService;
import com.hd.cloud.vo.CidVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PushResource
 * @Description: 推送绑定
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:45:29
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/push")
public class PushResource extends RestBase {

	@Inject
	private PushService pushService;

	/**
	 * 
	 * @Title: bindCid
	 * @param: CidVo
	 *             payload
	 * @Description: 绑定设备Cid
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/cid", method = RequestMethod.POST, produces = "application/json", consumes = "application/*")
	public BoUtil bindCid(final @RequestBody CidVo payload) {
		log.info("#######payload:{}", payload);
		long userId = super.getLoginUserID();
		String cid = StringUtils.trim(payload.getCid());
		BoUtil bo = BoUtil.getDefaultTrueBo();
		// 数据校验
		if (userId <= 0) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("userId is empty");
		} else if (StringUtils.isBlank(cid)) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("cid is empty");
		} else if (payload.getDeviceType() <= 0 || payload.getDeviceType() >= 4) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("Device type error");
		} else if (payload.getAppType() <= 0 || payload.getAppType() >= 3) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("App type error");
		} else {
			UserPush userPush = UserPush.builder().userId(userId).cid(payload.getCid())
					.deviceType(payload.getDeviceType()).appType(payload.getAppType()).createBy((int) userId)
					.createTime(new Timestamp(System.currentTimeMillis() / 1000)).updateBy((int) userId)
					.updateTime(new Timestamp(System.currentTimeMillis() / 1000)).activeFlag("y").build();
			log.info("userPush = {}", userPush);
			// 绑定用户推送所需信息
			UserPush push = pushService.addUserPush(userPush);
			if (null == push) {
				bo = BoUtil.getDefaultFalseBo();
				bo.setMsg("push failed");
			}
		}
		return bo;
	}

	/**
	 * 
	 * @Title: unbindCid
	 * @param: CidVo
	 *             payload
	 * @Description: 解绑设备Cid
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/unbindcid", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/*")
	public BoUtil unbindCid(final @Valid @QueryParam("cid") String cid) {
		BoUtil bo = BoUtil.getDefaultTrueBo();
		long userId = super.getLoginUserID();
		String cidTo = StringUtils.trim(cid);
		// 数据校验
		if (userId <= 0) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("userId is empty");
		} else if (StringUtils.isBlank(cidTo)) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("CID is empty");
		} else {
			// 推送信息解绑
			pushService.deleteUserPushByUserId(userId, cidTo);
		}
		return bo;
	}
}
