package com.hd.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: HotActivityBo
 * @Description: 热门活动返回的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:12:31
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotActivityBo {

	private List<HotActivityListBo> hotActivityList;// 热门活动列表
	private int count;
}
