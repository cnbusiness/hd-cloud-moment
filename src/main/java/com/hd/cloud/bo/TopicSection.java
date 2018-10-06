package com.hd.cloud.bo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: TopicSection
 * @Description: 话题
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午4:29:56
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSection {

	// 话题id
	private int id;

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

	// 排序
	private int sort;

	// 是否是精选池标识,1是2不是
	private int isDisplay;

	// 国家简码
	private String countryCode;

	// 审核状态,1通过2不通过
	private int isCheck;

	// 话题创建者id
	private long userId;

	// 创建来源，1用户;2后台
	private int sourceType;

	// 动态总点赞数
	private int starCount;

	// 总动态数量
	private int dynamicCount;

	// 浏览量
	private int viewCount;

	// 收藏量
	private int favoritesCnt;

	// 是否置顶,1是2否
	private int isTop;

	// 置顶时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp topDate;

	// 动态总评论数
	private int reviewCount;

	// 创建人
	private long createBy;

	private long updateBy;

	private String activeFlag;

	// 取几行数据
	private List<Map<String, Object>> dynamicList;
	
	//是否币圈新闻,1是2不是
	private int isNews;
}
