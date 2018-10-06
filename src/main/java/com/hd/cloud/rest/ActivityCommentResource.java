package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.ActivityCommentBo;
import com.hd.cloud.service.ActivityCommentService;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.StringUtil;
import com.hd.cloud.vo.ActivityCommentVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ActivityCommentResource
 * @Description: 活动评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午5:42:41
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("activitycomment")
public class ActivityCommentResource extends RestBase {

	@Inject
	private ActivityCommentService activityCommentService;

	/**
	 * 
	 * @Title: createActivityComment
	 * @param:
	 * @Description: 评论活动
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "createActivityComment", notes = "createActivityComment")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil createActivityComment(final @RequestBody ActivityCommentVo payload) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		long commenter = payload.getCommenter();
		int activityId = payload.getActivityId();
		long parentId = payload.getParentId();
		String content = payload.getContent();
		int commentType = payload.getCommentType();
		String commentUrls = payload.getCommentUrls();
		int type = payload.getType();
		long userId = super.getLoginUserID();
		log.debug("@payload{},@loginUserId{}", payload, userId);
		log.debug("**********************参数验证开始***************************");
		if (commenter == 0) {// 评论者Id非空验证
			boUtil.setCode(ErrorCode.COMMENTER_IS_EMPTY);
			return boUtil;
		}
		if (activityId == 0) {// 活动Id非空验证
			boUtil.setCode(ErrorCode.ACTIVITYID_IS_EMPTY);
			return boUtil;
		}
		if (parentId == 0) {// 活动评论上级Id非空验证
			boUtil.setCode(ErrorCode.ACTIVITY_COMMENT_PARENT_IS_EMPTY);
			return boUtil;
		}
		if (StringUtils.isBlank(content) && StringUtils.isBlank(commentUrls)) {// 活动评论内容或者图片不能为空
			boUtil.setCode(ErrorCode.ACTIVITY_COMMENT_CONTENT_IS_EMPTY);
			return boUtil;
		}
		if (StringUtil.getWordCountRegex(content) > 200) {// 活动评论内容长度验证
			boUtil.setCode(ErrorCode.LENGTH_OF_CONTENT);
			return boUtil;
		}
		if (commentType != 1 && commentType != 2 && commentType != 3) {// 验证活动评论类型是否错误(评论活动还是评论用户,还是评论店铺)
			boUtil.setCode(ErrorCode.ACTIVITY_TYPE_ERROR);
			return boUtil;
		}
		if (!StringUtil.isBlank(commentUrls)) {// 图片不为空时
			if (commentUrls.split("\\|").length > 6) {// 活动评论图片验证是否超过六张
				boUtil.setCode(ErrorCode.NUMBER_OF_PICTURE);
				return boUtil;
			}
		}
		if (type != 1 && type != 2) {// 验证评论类型是否错误(评论为魔线评论还是为魔商评论)
			boUtil.setCode(ErrorCode.ACTIVITY_TYPE_ERROR);
			return boUtil;
		}
		log.debug("**********************参数验证结束***************************");

		log.debug("**********************调用新增评论Service层开始***************************");
		activityCommentService.addActivityComment(payload, userId);
		log.debug("**********************调用新增评论Service层结束***************************");

		if (type == 1)
			log.info("用户ID:{},评论了活动ID:{}", commenter, activityId);
		if (type == 2)
			log.info("店铺ID:{},评论了活动ID:{}", commenter, activityId);
		boUtil = BoUtil.getDefaultTrueBo();
		return boUtil;
	}

	/**
	 * 
	 * @Title: getActivityCommentList
	 * @param:
	 * @Description: 获取评论列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/commentaries", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getActivityCommentList(@QueryParam("activityId") Integer activityId, @QueryParam("type") Integer type,
			@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		activityId = activityId == null ? 0 : activityId;
		type = type == null ? 0 : type;
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;

		log.debug("**********************调用Service层活动评论列表开始***************************");
		List<ActivityCommentBo> activityCommentList = activityCommentService.getAllComments(type, activityId, offset,
				pageSize);
		log.debug("**********************调用Service层活动评论列表结束***************************", activityCommentList);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(activityCommentList);
		return boUtil;
	}
}
