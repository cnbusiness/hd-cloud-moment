package com.hd.cloud.rest;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.service.FeedPostInfoService;
import com.hd.cloud.vo.PostInfoVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

/**
 * 
 * @ClassName: MessageResource
 * @Description: 消息管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午11:30:03
 *
 */
@RefreshScope
@RestController
@RequestMapping("message")
public class MessageResource extends RestBase {

	@Inject
	private FeedPostInfoService feedPostInfoService;

	/**
	 * 
	 * @Title: getPostInfoByUser
	 * @param:
	 * @Description: 获取消息列表(动态通知)
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getPostInfoByUser(@QueryParam("postId") Integer postId, @QueryParam("status") Integer status,
			@QueryParam("infoType") Integer infoType, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		postId = postId == null ? 0 : postId;
		status = status == null ? 0 : status;
		infoType = infoType == null ? 0 : infoType;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		PostInfoVo postInfoVo = PostInfoVo.builder().postId(postId).userId(getLoginUserID()).status(status)
				.infoType(infoType).offset(offset).pageSize(pageSize).build();
		boUtil.setData(feedPostInfoService.getPostInfoList(postInfoVo));
		return boUtil;
	}
}
