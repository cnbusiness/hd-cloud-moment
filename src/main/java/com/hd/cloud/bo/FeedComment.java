package com.hd.cloud.bo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FeedComment
 * @Description: 评论
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午4:11:42
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedComment {

	private int id;

	// 评论内容
	private String comment;

	// 动态id
	private int postId;

	// 被回复用户id
	private long commentUserId;

	// 用户id
	private long userId;

	// 状态，1 正常2屏蔽3关闭
	private int status;

	private long createBy;

	private long updateBy;

	private String activeFlag;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp createTime;// 创建时间
}
