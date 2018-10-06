package com.hd.cloud.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: CidVo
 * @Description: 绑定设备或解绑设备传参VO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午10:54:42
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CidVo {

	/**
	 * 个推的ClientId
	 */
	private String cid;

	/**
	 * 设备类型 1 Android 2 ios企业版本 3是ios的appstore版
	 */
	private int deviceType;

	/**
	 * app类型 1.用户 2.商家
	 */
	private int appType;

}
