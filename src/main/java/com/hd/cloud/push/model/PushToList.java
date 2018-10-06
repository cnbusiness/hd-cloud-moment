package com.hd.cloud.push.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;
import com.hd.cloud.push.Push;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PushToList
 * @Description: 列表推送
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:05:03
 *
 */
@Slf4j
public class PushToList implements Push {

	// TODO 后续完善
	@Override
	public void push(List<String> cids, ITemplate messageTmpl, GetuiConfigInfo getuiConfig) {
		try {
			IGtPush push = new IGtPush(getuiConfig.getHost(), getuiConfig.getAppKey(), getuiConfig.getMasterSecret());

			push.connect();

			System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");

			ListMessage message = new ListMessage();
			message.setOffline(true);
			message.setOfflineExpireTime(1 * 1000 * 3600);
			message.setData(messageTmpl);

			List<Target> targetList = new ArrayList<Target>();
			for (String cid : cids) {
				Target target = new Target();
				target.setAppId(getuiConfig.getAppId());
				target.setClientId(cid);
				targetList.add(target);
				log.info("targetClient = {}", target.getClientId());
			}
			IPushResult ret = push.pushMessageToList(push.getContentId(message), targetList);
			log.info("push result is {}", ret.getResponse().toString());
		} catch (IOException e) {
			log.info("PushToList is false");
			e.printStackTrace();
		}

	}
}
