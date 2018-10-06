package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityComment
 * @Description: 活动评论表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:41:56
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityComment {

	private int id;// 主键Id

	private int activityId;// 活动Id

	private long commenter;// 评论者主键id,店铺的话则是店铺id,用户则为用户id

	private long parentId;// 评论的上级id 3. 评论店铺，则为店铺Id,2.评论用户，则为用户Id,1.评论活动,则为活动Id

	private String content;// 评论内容

	private short commentType;// 评论类型,1是对活动的评论,2是对用户评论进行的评论,3评论店铺

	private String commentUrls;// 评论图片，多个图片时用|分隔

	private short appType;// 评论类型 1.魔线 2.魔商

	private int commentStatus;// 状态，1正常2屏蔽3关闭

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;
}
