package com.hd.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityListBo
 * @Description: 活动首页列表返回对象的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:25:29
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListBo {

	private int activityId;// 活动Id
	private String theme;// 活动主题
	private int activityTypeId;// 活动类型Id
	private String activityTypeName;// 活动类型名称
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date startTime;// 开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date endTime;// 结束时间
	private String address;// 活动地址
	private double longitude;// 活动地点经度
	private double latitude;// 活动地点纬度
	private double distance;// 距离
	private boolean join;// 活动是否参加 true 表示已参加 false 表示未参加
	private String pictureUrl;// 活动首页图片
	private int type;// 1.用户活动 2.商家活动
	private int joinNumber;// 活动参加人数
	private int shopId;// 店铺Id
}
