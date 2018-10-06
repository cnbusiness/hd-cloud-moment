package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName: FeedTimelineInfo
 * @Description: 动态指定用户信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 上午10:59:37
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedTimelineInfo {

	private int id;

	// 用户id
	private long userId;

	// 动态id
	private int postId;

	// 创建人
	private long createBy;

	private long updateBy;

	private String activeFlag;
}
