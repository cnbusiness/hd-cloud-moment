package com.hd.cloud.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ListPushModel
 * @Description: 列表推送
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:11:51
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListPushModel {

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
	private List<Long> toUserIdLIst;

	/**
	 * 推送的应用 1.魔线。2.魔商
	 */
	private int toAppType;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 是否需要免打扰(默认不开启免打扰)
	 */
	private boolean isRemind = false;

	/**
	 * 透传信息
	 */
	private String json;

}
