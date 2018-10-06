package com.hd.cloud.push;

/**
 * 
 * @ClassName: PushModelCont
 * @Description: 推送枚举常量
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:09:15
 *
 */
public enum PushModelCont {

	PUSH_TO_APP(1), PUSH_TO_LIST(2), PUSH_TO_STRING(3), SIMPLE_PUSH(4), PUSH_OFFLINE_TO_STRING(5);

	private int pushMode;

	private PushModelCont(int pushMode) {
		this.pushMode = pushMode;
	}

	public int getValue() {
		return this.pushMode;
	}

}
