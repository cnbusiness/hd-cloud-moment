package com.hd.cloud.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: MbActivityUpdateVo
 * @Description: 修改魔线活动的VO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:13:55
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MbActivityUpdateVo {
	private int activityId;// 活动Id
	private String pictureUrls;// 活动图片
	private String theme;// 活动主题
	private long startTime;// 活动开始时间
	private long endTime;// 活动结束时间
	private int personNumber;// 活动限抱人数
	private String detail;// 活动详情
	private List<Integer> shopIdList;// 活动举行店铺Id
	private int activityTypeId;// 活动类型Id
	private int couponId;// 优惠券Id
	private int isNeedNotice;// 是否通知粉丝
	private int shopId;// 当前店铺Id
	private int isNeedPhone;// 活动报名是否需要填写手机号码 1.是 2.否
}
