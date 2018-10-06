package com.hd.cloud.push;

import java.util.List;

import com.gexin.rp.sdk.base.ITemplate;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;

/**
 * 
 * @ClassName: Push
 * @Description: 推送接口
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:04:19
 *
 */
public interface Push {

	/**
	 * 推送方法
	 * 
	 * @param cid
	 *            clientId
	 * @param type
	 * @param messageTmpl
	 *            消息
	 * @param getuiConfig
	 */
	void push(List<String> cid, ITemplate messageTmpl, GetuiConfigInfo getuiConfig);

}
