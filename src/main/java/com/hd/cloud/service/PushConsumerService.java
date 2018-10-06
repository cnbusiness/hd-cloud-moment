package com.hd.cloud.service;

import com.hd.cloud.model.ListPushModel;
import com.hd.cloud.model.SimplePushModel;

/**
 * 
 * @ClassName: PushConsumerService
 * @Description: 消费者Service
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:47:20
 *
 */
public interface PushConsumerService {

	/**
	 * 
	 * @Title: simplePush
	 * @param:
	 * @Description: 消费者，简单推送信息
	 * @return String
	 */
	public String simplePush(SimplePushModel simplePushModel);

	/**
	 * 
	 * @Title: listPush
	 * @param:
	 * @Description: 列表推送
	 * @return String
	 */
	public String listPush(ListPushModel listPushModel);

}
