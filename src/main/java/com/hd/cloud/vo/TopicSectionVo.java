package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: TopicSectionVo
 * @Description: 话题vo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午5:09:30
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSectionVo {

	// 1 币圈新闻
	private int type;

	private int offset;

	private int pageSize;

}
