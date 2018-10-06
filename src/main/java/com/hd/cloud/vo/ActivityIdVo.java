package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityIdVo
 * @Description: 活动id
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午5:30:26
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityIdVo {
	private int activityId;// 活动Id
	private int type;// 1.用户端 2.商家端
	private int shopId;// 当前店铺Id
}
