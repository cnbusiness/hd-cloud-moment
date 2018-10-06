package com.hd.cloud.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;

/**
 * 
 * @ClassName: PushToSingle
 * @Description: 个推实现类
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:39:18
 *
 */
public class PushToSingle {

	// 类型
	public static final int ANDROID_TYPE = 1;// 安卓

	public static final int IOS_ENTERPRISE_TYPE = 2;// IOS企业版

	public static final int IOS_APPSTORE_TYPE = 3;// IOS企业版

	// 模板选择
	public static final int NOTIFICATION_MSG_TMP = 1;// 提示模板
	public static final int LINK_MSG_TMP = 2; // 连接跳转模板
	public static final int DOWNLOAD_MSG_TMP = 3;// 下载跳转模板
	public static final int CUSTOM_MSG_TMP = 4; // 自定义模板

	public static void push(String cid, int type, ITemplate messageTmpl, GetuiConfigInfo getuiConfig) throws Exception {

		IGtPush push = new IGtPush(getuiConfig.getHost(), getuiConfig.getAppKey(), getuiConfig.getMasterSecret());

		push.connect();

		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		message.setData(messageTmpl);

		Target target = new Target();
		target.setAppId(getuiConfig.getAppId());
		target.setClientId(cid);

		IPushResult ret = push.pushMessageToSingle(message, target);
		System.out.println(ret.getResponse().toString());
	}

	/**
	 * 
	 * @Title: chooseMsgTmp
	 * @param: int
	 *             messageTemplate
	 * @Description: 个推模板选择
	 * @return ITemplate
	 */
	public static ITemplate chooseMsgTmp(int messageTemplate) {

		ITemplate template = null;
		switch (messageTemplate) {
		case LINK_MSG_TMP:
			template = new LinkTemplate();
			break;
		case DOWNLOAD_MSG_TMP:
			template = new NotyPopLoadTemplate();
			break;
		case CUSTOM_MSG_TMP:
			template = new TransmissionTemplate();
			break;
		case NOTIFICATION_MSG_TMP:
		default:
			template = new NotificationTemplate();
		}
		return template;
	}

}
