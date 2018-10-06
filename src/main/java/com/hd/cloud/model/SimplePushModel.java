package com.hd.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: SimplePushModel
 * @Description: 推送mq对象
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:12:09
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePushModel {

	/**
	 * 发送者来源userid
	 */
	private long fromUserId;

	/**
	 * 发送者公司id
	 */
	private long fromCompanyId;

	/**
	 * 推送者id
	 */
	private long toUserId;

	/**
	 * 推送的应用 1.用户  。2.商家
	 */
	private int toAppType;

	/**
	 * 是否需要免打扰(默认不开启免打扰)
	 */
	private boolean isRemind = false;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 透传信息
	 */
	private String json;

}
