package com.hd.cloud.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: PushVo
 * @Description: 推送
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年12月13日 下午5:35:12
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushVo {

	/**
	 * 来至的用户id
	 */
	private long fromUserId;

	/**
	 * 推送用户id
	 */
	private long toUserId;

	/**
	 * 消息类型
	 */
	private int messageType;

	/**
	 * 内容类型
	 */
	private int contentType;

}
