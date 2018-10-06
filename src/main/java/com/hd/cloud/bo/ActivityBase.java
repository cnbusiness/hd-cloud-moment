package com.hd.cloud.bo;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityBase
 * @Description: 活动基础表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:22:44
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBase {

	private int id;// 活动Id

	private String innerCode;// 活动内码

	private int activityTypeId;// 活动类型Id

	private int cityId;// 城市Id

	private String theme;// 活动主题

	private String detail;// 活动详情

	private int personNumber;// 限报人数,0为不限

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;// 活动开始日期

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;// 活动结束日期

	@JsonFormat(pattern = "HH:mm:ss")
	private String startTime;// 活动开始时间

	@JsonFormat(pattern = "HH:mm:ss")
	private String endTime;// 活动结束时间

	private int delayHour;// 活动延迟多少小时

	private String qrcode;// 活动二维码

	private short isNeedPhone;// 报名是否要求参与者填写手机号,1是2否

	private short isFree;// 是否收费,1是2否

	private double money;// 费用

	private short appType;// 活动类型 1.手机端魔线 2.手机端魔商 3.BOSS后台魔线 4.BOSS后台魔商

	private long organizer;// 创建活动者 魔线创建的活动则为用户Id 魔商创建的活动则为店铺司Id

	private String contactPhone;// 创建者的联系电话

	private short status;// 活动状态, 1活动取消, 2后台屏蔽,0正常

	private String cancleReason;// 取消原因

	private String logoUrl;// 活动的首页图片

	private String pictureUrls;// 活动图片,多个图片用|分隔

	private int userGroupId;// 活动关联群组id

	private short isNeedNotice;// 是否需要通知粉丝 1是通知,2不通知

	private short hasShake;// 活动是否开启摇一摇 1是 2否

	private int couponId;// 优惠券Id

	private int authType;// 公司资质认证是否通过, 1、待审核2、审核中、3、审核通过、4审核退回

	private int shopShield;// 店铺屏蔽状态 1.正常 2.屏蔽

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date beginTime;// 活动开始时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date finishTime;// 活动结束时间
}
