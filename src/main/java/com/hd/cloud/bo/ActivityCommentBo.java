package com.hd.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityCommentBo
 * @Description: 活动评论列表Bo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:17:46
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCommentBo {
	private int activityId;// 活动Id
	private String avatar;// 评论者的头像
	private long commenterId;// 评论者的Id
	private String commenterName;// 评论者的名称
	private int commentType;// 评论者的类型 1.用户 2.店铺
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date commentTime;// 评论时间
	private String content;// 评论内容
	private String pictureUrl;// 评论图片
	private long replyId;// 回复者的Id
	private String replyName;// 回复者的名称
	private int replyType;// 回复者的类型 1是对活动的评论,2是对用户评论进行的评论,3评论店铺
}
