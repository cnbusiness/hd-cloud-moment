package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.service.FeedPostBaseService;
import com.hd.cloud.vo.AddMomentVo;
import com.hd.cloud.vo.FansDynamicVo;
import com.hd.cloud.vo.FeedPostBaseVo;
import com.hd.cloud.vo.StarUserListVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;
import com.hlb.cloud.util.CommonConstantUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: MomentResource
 * @Description: 动态管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午2:39:43
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("moment")
public class MomentResource extends RestBase {

	@Inject
	private FeedPostBaseService feedPostBaseService;
	
	@Inject
	private FeedPostBaseCache feedPostBaseCache;

	/**
	 * 
	 * @Title: publishMoment
	 * @param:
	 * @Description: 发布动态
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "publish", notes = "publish")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil publishMoment(final @RequestBody AddMomentVo payload) {
		payload.setUserId(getLoginUserID());
		log.info("############payload:{}", payload);
		return feedPostBaseService.publishMoment(payload);
	}

	/**
	 * 
	 * @Title: getFeedPostBaseDetail
	 * @param:
	 * @Description: 获取动态详情
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getFeedPostBaseDetail(@PathVariable("id") Integer id) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		FeedPostBase feedPostBase = feedPostBaseService.getFeedPostBaseById(id);
		if(feedPostBase!=null) {
			feedPostBase.setStar(feedPostBaseCache.hasStar(id, getLoginUserID()));
		}
		boUtil.setData(feedPostBase);
		return boUtil;
	}

	/**
	 * 
	 * @Title: removeFeedPostBase
	 * @param:
	 * @Description: 删除动态
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "DELETE", value = "removeFeedPostBase", notes = "removeFeedPostBase")
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public BoUtil removeFeedPostBase(@PathVariable("id") Integer id) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		FeedPostBase feedPostBase = FeedPostBase.builder().id(id).activeFlag(CommonConstantUtil.ACTIVE_FLAG_D)
				.updateBy(getLoginUserID()).build();
		int result = feedPostBaseService.update(feedPostBase);
		if (result > 0) {
			boUtil.setMsg("delete success");
		} else {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("delete fail");
		}
		return boUtil;
	}

	/**
	 * 
	 * @Title: getUserDynamics
	 * @param:
	 * @Description: 获取用户动态(我的动态)列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/{userId}/dynamics", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getUserDynamics(@PathVariable("userId") long userId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		FeedPostBaseVo feedPostBaseVo = FeedPostBaseVo.builder().userId(userId).offset(offset).pageSize(pageSize)
				.loginUserId(getLoginUserID()).build();
		List<FeedPostBaseBo> feedPostBases = feedPostBaseService.getFeedPostBaseList(feedPostBaseVo);
		boUtil.setData(feedPostBases);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getFansDynamics
	 * @param:
	 * @Description: 好友动态
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/fans/dynamics", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getFansDynamics(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		page = page == null ? 0 : page-1;
		pageSize = pageSize == null ? 10 : pageSize;
		int offset = page * pageSize;
		FansDynamicVo fansDynamicVo = FansDynamicVo.builder().userId(getLoginUserID()).offset(offset).pageSize(pageSize)
				.build();
		boUtil.setData(feedPostBaseService.getFansDynamicsList(fansDynamicVo));
		return boUtil;
	}

	/**
	 * 
	 * @Title: saveStar
	 * @param:
	 * @Description: 喜欢
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "saveStar", notes = "saveStar")
	@ResponseBody
	@RequestMapping(value = "{id}/star", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil saveStar(@PathVariable("id") Integer id) {
		id = id == null ? 0 : id;
		return feedPostBaseService.saveStar(id, getLoginUserID());
	}

	/**
	 * 
	 * @Title: deleteStar
	 * @param:
	 * @Description: 取消喜欢
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "deleteStar", notes = "deleteStar")
	@ResponseBody
	@RequestMapping(value = "{id}/unstar", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil deleteStar(@PathVariable("id") Integer id) {
		id = id == null ? 0 : id;
		return feedPostBaseService.unStar(id, getLoginUserID());
	}

	/**
	 * 
	 * @Title: getStartUserList
	 * @param:
	 * @Description: 动态喜欢的人列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/star/{postId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getStartUserList(@PathVariable("postId") Integer postId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		postId = postId == null ? 0 : postId;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		StarUserListVo starUserListVo = StarUserListVo.builder().userId(getLoginUserID()).postId(postId).offset(offset)
				.pageSize(pageSize).build();
		boUtil.setData(feedPostBaseService.getStarUserList(starUserListVo));
		return boUtil;
	}

}
