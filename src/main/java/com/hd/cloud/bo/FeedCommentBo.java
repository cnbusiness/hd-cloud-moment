package com.hd.cloud.bo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedCommentBo {

	private long id;
	
	private long commentUserId;// 回复id
	
	private String comment;// 内容
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp createTime;// 创建时间

	private UserProfile publishUser;//发布人
	
	private UserProfile replyUser;// 回复人
	
	private long userId;// 作者
	
	private int postId;// 动态id
}
