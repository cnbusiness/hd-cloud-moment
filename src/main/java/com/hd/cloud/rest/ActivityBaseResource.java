package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.ActivityHomeListBo;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.HotActivityBo;
import com.hd.cloud.service.ActivityBaseService;
import com.hd.cloud.util.DateUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.StringUtil;
import com.hd.cloud.vo.MXActivityVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityBaseResource
 * @Description: 活动管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午4:37:07
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("activitybase")
public class ActivityBaseResource extends RestBase {

	@Inject
	private ActivityBaseService activityBaseService;

	/**
	 * 
	 * @Title: publishActivityBase
	 * @param:
	 * @Description: 发布活动
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "publishActivityBase", notes = "publishActivityBase")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil publishActivityBase(final @RequestBody MXActivityVo payload) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		long userId = super.getLoginUserID();
		String pictureUrls = payload.getPictureUrls();
		String theme = payload.getTheme();
		long startTime = payload.getStartTime();
		long endTime = payload.getEndTime();
		String address = payload.getAddress();
		int cityId = payload.getCityId();
		double latitude = payload.getLatitude();
		double longitude = payload.getLongitude();
		String detail = payload.getDetail();
		int activityTypeId = payload.getActivityTypeId();
		int isNeedPhone = payload.getIsNeedPhone();
		log.debug("@payload{},@loginUserId{}", payload, userId);
		log.debug("**********************参数验证开始***************************");
		if (StringUtils.isBlank(pictureUrls)) {// 图片非空验证
			boUtil.setCode(ErrorCode.PICTUREURLS_IS_EMPTY);
			return boUtil;
		}
		if (pictureUrls.trim().split("\\|").length > 9) {// 图片数量验证
			boUtil.setCode(ErrorCode.NUMBER_OF_PICTURE);
			return boUtil;
		}
		if (StringUtils.isBlank(theme) || StringUtil.getWordCountRegex(theme.trim()) > 50) {// 活动主题验证有效性验证
			boUtil.setCode(ErrorCode.LENGTH_OF_THEME);
			return boUtil;
		}
		if (startTime == 0) {// 活动开始时间非空验证
			boUtil.setCode(ErrorCode.STARTTIME_IS_EMPTY);
			return boUtil;
		}
		if (endTime == 0) {// 活动结束时间非空验证
			boUtil.setCode(ErrorCode.ENDTIME_IS_EMPTY);
			return boUtil;
		}
		if (startTime > endTime) {// 开始时间是否大于结束时间开始验证
			boUtil.setCode(ErrorCode.STARTTIME_MORE_THAN_ENDTIME);
			return boUtil;
		}
		if (DateUtil.daysBetween(startTime, endTime) > 90) {// 活动时间是否大于90天验证
			boUtil.setCode(ErrorCode.ACTIVITY_TIME);
			return boUtil;
		}
		if (StringUtils.isBlank(address)) {// 活动地址非空验证
			boUtil.setCode(ErrorCode.ADDRESS_IS_EMPTY);
			return boUtil;
		}
		if (cityId == 0) {// 城市Id非空验证
			boUtil.setCode(ErrorCode.CITYID_IS_EMPTY);
			return boUtil;
		}
		if (latitude == 0.0 || longitude == 0.0) {// 经纬度非空验证
			boUtil.setCode(ErrorCode.COORDINATES_IS_EMPTY);
			return boUtil;
		}
		if (StringUtils.isBlank(detail) || StringUtil.getWordCountRegex(detail.trim()) > 800) {// 活动详情非空验证
			boUtil.setCode(ErrorCode.DETAIL_IS_EMPTY);
			return boUtil;
		}
		if (activityTypeId == 0) {// 活动类型非空验证
			boUtil.setCode(ErrorCode.ACTIVITYTYPEID_IS_EMPTY);
			return boUtil;
		}
		if (isNeedPhone != 2 && isNeedPhone != 1) {// 报名活动是否需要手机号码有效性验证
			boUtil.setCode(ErrorCode.ISNEEDPHONE_FORMAT_ERROR);
			return boUtil;
		}
		log.debug("**********************参数验证结束***************************");
		boUtil=BoUtil.getDefaultTrueBo();
		log.debug("*************************发布用户活动调用Service层开始**************************");
		String result = activityBaseService.addActivity(payload, userId);
		log.debug("*************************发布魔线活动调用Service层结束**************************");
		boUtil.setData(result);
		
		log.info("用户ID:{},发布了活动ID:{}", userId, result.split("\\|")[0]);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getActivityHome
	 * @param: int
	 *             pageIndex ,页数 int activityTypeId, 活动类型Id (0.全部活动 1.音乐
	 *             2.美食...)(默认为全部活动) int activityTime, 活动时间 (0.全部时间 1.本周末 2.今天 3.明天
	 *             4.其他时间)(默认为全部时间) int type, 1.人气最高 2.最新上线 3.将要开始 int cityId 城市Id
	 * @Description: 获取活动首页列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activities/home", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getActivityHome(@QueryParam("activityTypeId") Integer activityTypeId,
			@QueryParam("activityTime") Integer activityTime, @QueryParam("type") Integer type,
			@QueryParam("cityId") Integer cityId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		activityTypeId = activityTypeId == null ? 0 : activityTypeId;
		activityTime = activityTime == null ? 0 : activityTime;
		type = type == null ? 1 : type;
		cityId = cityId == null ? 0 : cityId;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		log.debug("********************获取城市活动调用Service层开始********************");
		ActivityHomeListBo activityHome = activityBaseService.getActivityList(offset, pageSize, activityTypeId,
				activityTime, type, cityId, 0, 0, getLoginUserID());
		log.debug("********************获取城市活动调用Service层结束********************");

		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(activityHome);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getMxActivityDetail
	 * @param:
	 * @Description: 获取活动详情
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activitydetail/{activityId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getMxActivityDetail(@PathVariable("activityId") Integer activityId) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		ActivityInfoBo activityInfoBo = activityBaseService.getActivityById(activityId);
		boUtil.setData(activityInfoBo);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getAllPushActivity
	 * @param:
	 * @Description: 获取我发布的所有活动
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activities/allpublished", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getAllPushActivity(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		long userId = super.getLoginUserID();
		page = page == null ? 1 : page;
		if (page <= 0) {
			page = 1;
		}
		pageSize = pageSize == null ? 20 : pageSize;
		int offset = (page - 1) * pageSize;
		log.debug("********************调用我发布的所有活动Service层开始********************");
		List<AllPushActivity> activityList = activityBaseService.getAllPushActivity(userId, offset, pageSize);
		log.debug("********************调用我发布的所有活动Service层结束********************");
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(activityList);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getAllJoinActivity
	 * @param:
	 * @Description: 获取我参加的所有活动
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activities/alljoined", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getAllJoinActivity(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		long userId = super.getLoginUserID();
		page = page == null ? 1 : page;
		if (page <= 0) {
			page = 1;
		}
		pageSize = pageSize == null ? 20 : pageSize;
		int offset = (page - 1) * pageSize;
		log.debug("********************调用我参加的所有活动Service层开始********************");
		List<AllJoinActivity> activityList = activityBaseService.getAllJoinActivity(userId, offset, pageSize);
		log.debug("********************调用我参加的所有活动Service层结束********************");

		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(activityList);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getHotActivity
	 * @param: int
	 *             cityId,int begin,int end
	 * @Description: 获取城市热门活动
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/activities/hot", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getHotActivity(@QueryParam("cityId") Integer cityId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		cityId = cityId == null ? 0 : cityId;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		log.debug("**********************调用热门活动Service层开始***************************");
		HotActivityBo hotActivityBo = activityBaseService.getHotActivity(cityId, offset, page, 0, 0,
				super.getLoginUserID());
		log.debug("**********************调用热门活动Service层结束***************************");
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(hotActivityBo);
		return boUtil;
	}

}
