package com.hd.cloud.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: MxActivityUpdateVo
 * @Description: 修改魔线活动的VO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:14:42
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MxActivityUpdateVo {
	private int activityId;// 活动Id
	private String pictureUrls;// 活动图片
	private String theme;// 活动主题
	private long startTime;// 活动开始时间
	private long endTime;// 活动结束时间
	private String address;// 活动地址
	private int cityId;// 城市Id
	private double latitude;// 纬度
	private double longitude;// 经度
	private String detail;// 活动详情
	private int activityTypeId;// 活动类型Id
	private int isNeedPhone;// 报名是否需要填写手机号码
}
