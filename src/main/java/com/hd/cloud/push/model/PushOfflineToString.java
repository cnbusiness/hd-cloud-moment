package com.hd.cloud.push.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;
import com.hd.cloud.push.Push;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushOfflineToString implements Push {

	@Override
	public void push(List<String> cid, ITemplate messageTmpl, GetuiConfigInfo getuiConfig) {
		try {
			IGtPush push = new IGtPush(getuiConfig.getHost(), getuiConfig.getAppKey(), getuiConfig.getMasterSecret());

			push.connect();
			IQueryResult statue = push.getClientIdStatus(getuiConfig.getAppId(), cid.get(0));
			Map<String, Object> map = statue.getResponse();
			String result = map.get("result").toString();
			log.info("mobile statue = {}", result);
			if (result.equals("Offline")) {// 离线就推送
				SingleMessage message = new SingleMessage();
				message.setOffline(true);
				message.setOfflineExpireTime(24 * 1000 * 3600);
				message.setData(messageTmpl);

				Target target = new Target();
				target.setAppId(getuiConfig.getAppId());
				target.setClientId(cid.get(0));

				IPushResult ret = push.pushMessageToSingle(message, target);
				log.info("push result is {}", ret.getResponse().toString());
			}
		} catch (IOException e) {
			log.info("PushToString is false");
			e.printStackTrace();
		}
	}

}
