package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: PostInfoVo
 * @Description: 消息vo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午10:10:31
 *
 */
@SuppressWarnings("deprecation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoVo {

	// 用户id
	private long userId;

	// 动态id
	private long postId;
	
	//是否已读,1是已读2是未读
	private int status;
	
	//1.评论 2.喜欢 3.回复 4.转发 5.参与 6.全部
	private int infoType;

	private int offset;

	private int pageSize;
}
