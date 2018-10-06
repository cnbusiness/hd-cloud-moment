package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivitySignUpVo
 * @Description: 活动报名VO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午4:39:22
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySignUpVo {
	private int activityId;// 活动Id
	private String phone;// 报名填写的手机号码
	private String explain;// 报名填写的说明 
}
