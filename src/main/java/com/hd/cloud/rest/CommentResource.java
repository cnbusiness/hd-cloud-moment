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

import com.hd.cloud.bo.FeedComment;
import com.hd.cloud.bo.FeedCommentBo;
import com.hd.cloud.service.FeedCommentService;
import com.hd.cloud.vo.FeedCommentVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;
import com.hlb.cloud.util.CommonConstantUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: CommentResource
 * @Description: 评论管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午9:15:01
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("comment")
public class CommentResource extends RestBase {

	@Inject
	private FeedCommentService feedCommentService;

	/**
	 * 
	 * @Title: getFeedCommentListByPostId
	 * @param:
	 * @Description: 获取动态下的所有评论
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/{postId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getFeedCommentListByPostId(@PathVariable("postId") Integer postId, @QueryParam("page") Integer page,
			@QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		postId = postId == null ? 0 : postId;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		FeedCommentVo feedCommentVo = FeedCommentVo.builder().offset(offset).pageSize(pageSize).postId(postId).build();
		List<FeedCommentBo> feedCommentBos = feedCommentService.getFeedCommentlist(feedCommentVo);
		boUtil.setData(feedCommentBos);
		return boUtil;
	}

	/**
	 * 
	 * @Title: publishComment
	 * @param:
	 * @Description: 发布评论
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "publishComment", notes = "publishComment")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil publishComment(final @RequestBody FeedComment comment) {
		comment.setUserId(getLoginUserID());
		comment.setCreateBy(getLoginUserID());
		comment.setStatus(1);
		comment.setActiveFlag(CommonConstantUtil.ACTIVE_FLAG_Y);
		return feedCommentService.save(comment);
	}

	/**
	 * 
	 * @Title: removeFeedComment
	 * @param:
	 * @Description: 删除评论
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "DELETE", value = "removeFeedComment", notes = "removeFeedComment")
	@ResponseBody
	@RequestMapping(value = "/{id}/{postId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public BoUtil removeFeedComment(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
		log.info("################id:{},postId:{}",id,postId);
		id = id == null ? 0 : id;
		postId = postId == null ? 0 : postId;
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		FeedComment feedComment = FeedComment.builder().id(id).postId(postId)
				.activeFlag(CommonConstantUtil.ACTIVE_FLAG_D).updateBy(getLoginUserID()).build();
		int result = feedCommentService.update(feedComment);
		if (result > 0) {
			boUtil.setMsg("delete success");
		} else {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("delete fail");
		}
		return boUtil;
	}

}
