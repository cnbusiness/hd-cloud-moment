package com.hd.cloud.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;
import com.hd.cloud.MomentServiceApplication.GetuiMobizAndroidConfigInfo;
import com.hd.cloud.MomentServiceApplication.GetuiMobizAndroidConfigInfo4;
import com.hd.cloud.MomentServiceApplication.GetuiMobizIOSAppstoreConfigInfo;
import com.hd.cloud.MomentServiceApplication.GetuiMobizIOSEnterpriseConfigInfo;
import com.hd.cloud.service.GetuiConfigInfoService;
import com.hd.cloud.util.ConstantUtil;

/**
 * 
 * @ClassName: GetuiConfigInfoServiceImpl
 * @Description: 个推
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:22:18
 *
 */
@Service
public class GetuiConfigInfoServiceImpl implements GetuiConfigInfoService {

	@Inject
	private GetuiMobizAndroidConfigInfo getuiMobizAndroidConfigInfo;

	@Inject
	private GetuiMobizIOSEnterpriseConfigInfo getuiMobizIOSEnterpriseConfigInfo;

	@Inject
	private GetuiMobizIOSAppstoreConfigInfo GetuiMobizIOSeAppstoreConfigInfo;

	@Inject
	private GetuiMobizAndroidConfigInfo4 getuiMobizAndroidConfigInfo4;

	@Override
	public GetuiConfigInfo getGetuiConfigInfo(int appType, int deviceType) {
		// androind
		if (deviceType == ConstantUtil.DeviceType.ANDROID) {
			return getuiMobizAndroidConfigInfo;
		}
		// IOS 企业版
		if (deviceType == ConstantUtil.DeviceType.IOS_ETERPRISE) {
			return getuiMobizIOSEnterpriseConfigInfo;
		}
		// IOS appstore版
		if (deviceType == ConstantUtil.DeviceType.IOS_APPSTORE) {
			return GetuiMobizIOSeAppstoreConfigInfo;
		}

		// 添加推送商家google版
		if (deviceType == ConstantUtil.DeviceType.ANDROID4) {
			return getuiMobizAndroidConfigInfo4;
		}

		return null;
	}

}
