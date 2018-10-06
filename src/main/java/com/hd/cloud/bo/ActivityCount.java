package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityCount
 * @Description: 活动统计信息表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:43:22
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCount {

	private int id;// 主键Id

	private int activityId;// 活动Id

	private int forwardCount;// 转发量

	private int commentCount;// 评论总量

	private int commentPicCount;// 带图片评论总量

	private int favoriteCount;// 收藏量

	private int viewCount;// 浏览量

	private int shareCount;// 分享量

	private int joinCount;// 活动已报名人数

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;
}
