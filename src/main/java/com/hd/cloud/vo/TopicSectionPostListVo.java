package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: TopicSectionPostListVo
 * @Description: 话题动态列表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 上午10:10:40
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSectionPostListVo {
	
	private long userId;

	private int topicId;
	
	//1 最新 2 最热
	private int sort;

	private int offset;

	private int pageSize;
}
