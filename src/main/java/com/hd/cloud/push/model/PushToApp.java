package com.hd.cloud.push.model;

import java.io.IOException;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;
import com.hd.cloud.push.Push;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PushToApp
 * @Description: app应用群推送
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:04:52
 *
 */
@Slf4j
public class PushToApp implements Push {

	@Override
	public void push(List<String> cid, ITemplate messageTmpl, GetuiConfigInfo getuiConfig) {
		try {
			IGtPush push = new IGtPush(getuiConfig.getHost(), getuiConfig.getAppKey(), getuiConfig.getMasterSecret());

			push.connect();

			SingleMessage message = new SingleMessage();
			message.setOffline(true);// 是否离线推送
			message.setOfflineExpireTime(1 * 1000 * 3600);// 离线保存时间
			message.setData(messageTmpl);

			Target target = new Target();
			target.setAppId(getuiConfig.getAppId());
			target.setClientId(cid.get(0));

			IPushResult ret = push.pushMessageToSingle(message, target);
			log.info("push result is {}", ret.getResponse().toString());
		} catch (IOException e) {
			log.info("PushToString is false");
			e.printStackTrace();
		}
	}

}
