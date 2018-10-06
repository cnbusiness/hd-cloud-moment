package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityAddressBo
 * @Description: 活动地点和活动报名关系返回Bo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:49:04
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAddressBo {
	private int activityId;// 活动Id
	private String address;// 活动活动地址
	private int shopId;// 店铺Id
	private double longitude;// 经度
	private double latitude;// 纬度
	private double distance;// 距离
}
