package com.hd.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: TopicSectionBo
 * @Description: 话题详情
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 上午9:55:29
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSectionBo {

	// 话题id
	private int topicId;

	// 话题主题
	private String name;

	// 话题简介
	private String desc;

	// 图标url
	private String iconUrl;

	// 背景url
	private String backgroudUrl;

	// 首页Url
	private String homeUrl;

	// 动态列表
	private List<FeedPostBaseBo> dynamicList;

	// 是否币圈新闻,1是2不是
	private int isNews;

}
