package com.hd.cloud.push;

import com.hd.cloud.push.model.PushOfflineToString;
import com.hd.cloud.push.model.PushToApp;
import com.hd.cloud.push.model.PushToList;
import com.hd.cloud.push.model.PushToString;
import com.hd.cloud.push.model.SimplePush;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PushModeFactory
 * @Description: 推送工厂
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:09:25
 *
 */
@Slf4j
public class PushModeFactory {

	private static Push pushToString = new PushToString();
	private static Push pushToApp = new PushToApp();
	private static Push pushToList = new PushToList();
	private static Push simplePush = new SimplePush();
	private static Push PushOfflineToString = new PushOfflineToString();

	public static Push getPushInstance(int pushName) {
		log.info("pushName = " + pushName);
		if (PushModelCont.PUSH_TO_APP.getValue() == pushName) {
			return pushToApp;
		} else if (PushModelCont.PUSH_TO_STRING.getValue() == pushName) {
			return pushToString;
		} else if (PushModelCont.PUSH_TO_LIST.getValue() == pushName) {
			return pushToList;
		} else if (PushModelCont.SIMPLE_PUSH.getValue() == pushName) {
			return simplePush;
		} else if (PushModelCont.PUSH_OFFLINE_TO_STRING.getValue() == pushName) {
			return PushOfflineToString;
		} else {
			log.info("pushFactory is not exit,pushName = " + "pushName");
			return null;
		}

	}

}
