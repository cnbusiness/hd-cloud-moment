package com.hd.cloud.push;

/**
 * 
 * @ClassName: PushTemplateCont
 * @Description: 推送模板常量
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:09:05
 *
 */
public enum PushTemplateCont {
	/**
	 * 点击通知打开网页模板
	 */
	LINK_MSG_TMPL(1),
	/**
	 * 
	 */
	TRANSMISSION_MSG_TMPL(2),
	/**
	 * 点击通知打开应用模板
	 */
	NOTIFICATION_MSG_TMPL(3),
	/**
	 * 点击通知弹窗下载模板
	 */
	LOAD_MSG_TMP(4);

	private int pushTemplateCont;

	private PushTemplateCont(int pushTemplateCont) {
		this.pushTemplateCont = pushTemplateCont;
	}

	public int getValue() {
		return this.pushTemplateCont;
	}

	public String toString() {
		return String.valueOf(this.pushTemplateCont);
	}

}
