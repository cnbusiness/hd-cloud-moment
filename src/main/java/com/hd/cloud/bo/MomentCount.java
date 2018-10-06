package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: MomentCount
 * @Description: 访问量，喜欢量集合
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 下午4:20:47
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MomentCount {
	private int viewCount;// 访问量
	private int starCount;// 点赞量
	private int forwardCount;// 转发量
	private int favoritesCnt;// 收藏量
	private int reviewCnt;// 评论量
}
