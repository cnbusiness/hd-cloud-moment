package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AdJoinDataBo
 * @Description: 联合查询ad_module 和 ad_relation 返回的对象
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:30:48
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdJoinDataBo {
	// 图片id
	private long id;
	// 模块名称
	private String moduleName;
	// 模块描述
	private String moduleRemark;
	// 图片id
	private long adId;
	// 排序
	private int sort;
	// 图片
	private Object ad;
}
