package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: PartyActivityType
 * @Description: 活动类型
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:12:15
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeBo {

	private int id;

	// 活动类型名称
	private String name;
}
