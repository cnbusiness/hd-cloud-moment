package com.hd.cloud.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.bo.TopicSectionBo;
import com.hd.cloud.service.TopicSectionService;
import com.hd.cloud.vo.TopicSectionPostListVo;
import com.hd.cloud.vo.TopicSectionVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

/**
 * 
 * @ClassName: TopicDynamicResource
 * @Description: 话题管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午5:47:01
 *
 */
@RefreshScope
@RestController
@RequestMapping("topic")
public class TopicDynamicResource extends RestBase {

	@Inject
	private TopicSectionService topicSectionService;

	/**
	 * 
	 * @Title: getHotTopicList
	 * @param:
	 * @Description: 热门话题
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/hot", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getHotTopicList() {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<TopicSection> topicSections = topicSectionService.getHotTopicSectionList();
		boUtil.setData(topicSections);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getTopicList
	 * @param:
	 * @Description: 话题列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getTopicList(@QueryParam("type") Integer type,@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		page = page == null ? 1 : page;
		type= type == null ? 0 : type;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		TopicSectionVo topicSectionVo = TopicSectionVo.builder().type(type).offset(offset).pageSize(pageSize).build();
		List<TopicSection> topicSections = topicSectionService.getTopicSectionList(topicSectionVo);
		boUtil.setData(topicSections);
		return boUtil;
	}

	/**
	 * 
	 * @Title: getTopicSectionDetail
	 * @param:
	 * @Description: 获取话题详情
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getTopicSectionDetail(@PathVariable("id") Integer id, @QueryParam("sort") Integer sort,
			@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		id = id == null ? 0 : id;
		pageIndex = pageIndex == null ? 1 : pageIndex;
		sort = sort == null ? 0 : sort;
		pageSize = pageSize == null ? 10 : pageSize;
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		int offset = (pageIndex - 1) * pageSize;
		TopicSectionPostListVo topicSectionPostListVo = TopicSectionPostListVo.builder().userId(getLoginUserID()).topicId(id).offset(offset)
				.sort(sort).pageSize(pageSize).build();
		TopicSectionBo topicSectionBo = topicSectionService.getTopicSectionPostList(topicSectionPostListVo);
		boUtil.setData(topicSectionBo);
		return boUtil;
	}

}
