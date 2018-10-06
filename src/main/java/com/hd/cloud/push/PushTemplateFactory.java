package com.hd.cloud.push;

import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;

/**
 * 
 * @ClassName: PushTemplateFactory
 * @Description: 推送模板工厂
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:08:54
 *
 */
public class PushTemplateFactory {

	public static ITemplate getInstance(int tmpl) {

		if (PushTemplateCont.LINK_MSG_TMPL.getValue() == tmpl) {
			return new LinkTemplate();
		} else if (PushTemplateCont.LOAD_MSG_TMP.getValue() == tmpl) {
			return new NotyPopLoadTemplate();
		} else if (PushTemplateCont.NOTIFICATION_MSG_TMPL.getValue() == tmpl) {
			return new NotyPopLoadTemplate();
		} else {
			return new NotificationTemplate();
		}
	}

}
