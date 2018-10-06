package com.hd.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityHomeListBo
 * @Description: 活动首页列表返回的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:08:37
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHomeListBo {

	List<ActivityListBo> activityListBo;// 活动列表 

}
