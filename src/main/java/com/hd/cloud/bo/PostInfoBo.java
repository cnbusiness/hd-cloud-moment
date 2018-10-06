package com.hd.cloud.bo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoBo {

	private int id;
	private String content;// 内容
	private Integer infoType;// 1：评论 2：点赞 3：回复 4.转发 5.参与 6.全部
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;// 创建时间
	private long createBy;// 创建人
	private int postId;// 动态id
	private String postavatar;// 动态首页
	private int status; // 是否已读,1是已读2是未读
	private String postContent;// 动态数量
	private UserProfile userBo;// 用户
	private long fromUserId;// 作者id
	private Integer type;// 1.动态 2.话题 3.活动 4.投票
	// 原动态内容的缩写内容
	private String shortText;
	private long userId;
}
