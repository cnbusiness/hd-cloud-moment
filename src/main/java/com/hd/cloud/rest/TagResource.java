package com.hd.cloud.rest;

import javax.inject.Inject;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.service.FeedTagDictService;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;

/**
 * 
 * @ClassName: TagResource
 * @Description: 标签
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午4:06:13
 *
 */
@RefreshScope
@RestController
@RequestMapping("tag")
public class TagResource extends RestBase {

	@Inject
	private FeedTagDictService feedTagDictService;

	/**
	 * 
	 * @Title: getAllTag
	 * @param:
	 * @Description: 标签列表
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getAllTag() {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setData(feedTagDictService.getFeedTagDictList());
		return boUtil;
	}
}
