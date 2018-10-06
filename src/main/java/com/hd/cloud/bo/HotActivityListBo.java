package com.hd.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: HotActivityListBo
 * @Description: 热门活动返回的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:32:53
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotActivityListBo {

	private int activityId;// 活动Id
	private String theme;// 活动主题
	private String pictureUrl;// 活动图片
	private int type;// 活动类型 1.用户 2.商家
	private int order;// 排序序号
	private String address;// 活动地点
	private int shopId;// 最近的店铺Id
	private int joinNumber;// 报名人数
	private int activityTypeId;// 活动类型Id
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date startTime;// 活动开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date endTime;// 活动结束时间
	private boolean isJoin;// 是否已报名该活动 1.是 2.否
	private double distance;// 距离(km)
}
