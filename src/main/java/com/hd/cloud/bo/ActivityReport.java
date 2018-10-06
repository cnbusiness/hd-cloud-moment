package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityReport
 * @Description: 活动举报表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:46:30
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityReport {

	private int id;// 主键Id

	private String innerCode;// 举报内码

	private long userId;// 用户Id

	private int activityId;// 活动Id

	private String reportContent;// 举报描述

	private String reportTypeIds;// 举报类型Id

	private int sourceType;// 举报来源，1魔线2魔商3后台

	private int dealType;// 处理状态,1处理2未处理

	private int agreeType;// 处理结果，1已通过2未通过

	private String feedBack;// 处理意见

	private int reportDoId;// 处理方式id

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;
}
