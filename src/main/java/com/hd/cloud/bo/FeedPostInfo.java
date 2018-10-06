package com.hd.cloud.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FeedPostInfo
 * @Description: 消息中心
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午9:59:39
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPostInfo {

	private int id;

	// 类型, 1.动态 2.话题 3.活动 4.投票
	private int type;

	// 消息内容
	private String content;

	// 动态id/活动id
	private int postId;

	// 主页图路径
	private String avatar;

	// 1.评论 2.喜欢 3.回复 4.转发 5.参与 6.全部
	private int infoType;

	// 用户id
	private long userId;

	// 来源用户id
	private long fromUserId;

	// 是否已读,1是已读2是未读
	private int status;

	// 原动态内容的缩写内容
	private String shortText;

	// 创建人
	private long createBy;

	private long updateBy;

	private String activeFlag;
}
