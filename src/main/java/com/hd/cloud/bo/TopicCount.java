package com.hd.cloud.bo;

import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: TopicCount
 * @Description: 话题访问量，喜欢量集合
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 下午4:20:47
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicCount {
	private AtomicLong viewCount;// 访问量
	private AtomicLong dynamicCount;// 动态数量
	private AtomicLong starCount;// 点赞量
	private AtomicLong favoritesCnt;// 收藏量
}
