package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityCommentVo
 * @Description: 评论活动的VO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:17:09
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCommentVo {

	private int activityId;// 活动Id
	private long parentId;// 评论的上级Id(评论店铺，则为店铺Id,评论用户，则为用户Id,评论活动,则为活动Id)
	private long commenter;// 评论者主键id,店铺的话则是店铺id,用户则为用户id
	private String content;// 评论的内容
	private int commentType;// 评论类型,1是对活动的评论,2是对用户评论进行的评论
	private String commentUrls;// 评论的图片，用 | 分割
	private int type;// 评论类型 1.用户评论 2.商家评论
}
