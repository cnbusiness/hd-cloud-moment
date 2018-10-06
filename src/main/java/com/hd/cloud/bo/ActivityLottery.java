package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityLottery
 * @Description: 活动抽奖表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:45:35
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLottery {

	private int id;// 主键Id

	private int activityId;// 活动Id

	private long userId;// 用户Id

	private short prizeType;//奖品类型,1阿萨石2游戏点3其它

	private int prizeNumber;// 奖励的数量

	private String prizeDesc;// 奖励物品描述

	private int lottrtyType;// 中奖类型 1不在现场，2恭喜中奖

	private long creater;

	private Timestamp createTime;

	private long updater;

	private Timestamp updateTime;

	private char activeFlag;
}
