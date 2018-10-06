package com.hd.cloud.bo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: ActivityJoinBo
  * @Description: 活动报名人列表返回对象的BO
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月12日 下午2:26:57
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJoinBo {
	private int activityId;//活动Id
	private long userId;//用户Id
	private String nickName;//昵称
	private String avatar;//头像
	private int sex;//性别
	private String phone;//报名填写的手机号码
	private String birthday;//生日
	private String explain;//报名填写的描述
	private String address;//生活所在城市
	private boolean hasCoupon;//是否有领取优惠券
	
}
