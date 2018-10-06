
package com.hd.cloud.service;

import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;

/**
 * 
 * @ClassName: GetuiConfigInfoService
 * @Description: 个推config
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:21:11
 *
 */
public interface GetuiConfigInfoService {

	public GetuiConfigInfo getGetuiConfigInfo(int appType, int deviceType);

}
