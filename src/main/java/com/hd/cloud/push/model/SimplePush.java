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

@Slf4j
public class SimplePush implements Push {

	@Override
	public void push(List<String> cid, ITemplate messageTmpl, GetuiConfigInfo getuiConfig) {
		log.info("################cid:{}", cid);
		log.info("################messageTmpl:{}", messageTmpl);
		log.info("################getuiConfig:{}", getuiConfig);
		try {
			IGtPush push = new IGtPush(getuiConfig.getHost(), getuiConfig.getAppKey(), getuiConfig.getMasterSecret());
			push.connect();

			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			message.setOfflineExpireTime(1 * 1000 * 3600);
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

		/*
		 * try { log.info("####Started SimplePush"); RestTemplate restTemplate = new
		 * RestTemplate();
		 * 
		 * HttpHeaders httpHeaders = new HttpHeaders(); log.info("####key=" +
		 * getuiConfig.getServerKey()); httpHeaders.set("Authorization", "key=" +
		 * getuiConfig.getServerKey()); httpHeaders.set("Content-Type",
		 * "application/json");
		 * 
		 * JSONObject msg = new JSONObject(); JSONObject json = new JSONObject();
		 * 
		 * JSONObject jsonObj =
		 * JSONObject.parseObject(messageTmpl.getTransmissionContent()); String content
		 * = jsonObj.getString("content"); String createTime =
		 * jsonObj.getString("createTime"); String wechatNumber =
		 * jsonObj.getString("wechatNumber"); String outletName =
		 * jsonObj.getString("outletName"); msg.put("content", content);
		 * msg.put("createTime", createTime); msg.put("wechatNumber", wechatNumber);
		 * msg.put("outletName", outletName); json.put("data", msg);
		 * json.put("registration_ids", cid); json.put("priority", "high");
		 * log.info("####Post request string:" + json.toString()); HttpEntity<String>
		 * httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
		 * 
		 * String response = restTemplate.postForObject(getuiConfig.getHost(),
		 * httpEntity, String.class); log.info("####Response from GCM:", response);
		 * log.info("####Ended SimplePush"); } catch (Exception e) {
		 * e.printStackTrace(); log.error("####JSON error:", e); }
		 */

	}
}
