package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityJoin
 * @Description: 活动参加人信息表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:44:33
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJoin {

	private int id;// 主键Id

	private int activityId;// 活动Id

	private long userId;// 用户Id

	private String phone;// 报名者的手机号码

	private String alaisName;// 报名者的昵称,称谓

	private short personType;// 是否报名,1是2否

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;
	
	private char activeFlag;

}
