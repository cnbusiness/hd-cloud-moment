package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityAddress
 * @Description: 活动地址表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:41:07
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAddress {

	private int id;// 主键Id

	private int activityId;// 活动Id

	private int shopId;// 店铺Id

	private double latitude;// 经度

	private double longitude;// 纬度

	private String address;// 活动地址

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;
}
