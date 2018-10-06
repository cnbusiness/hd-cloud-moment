package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AdRelation
 * @Description: 广告分组城市关系实体
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:26:24
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdRelation {
	// 主键id
	private long id;
	// 广告模块主键id
	private long adModuleId;
	// 广告关联城市id
	private long adCityId;
	// 图片id
	private long adId;
	// 排序
	private int sort;
	// 创建者
	private int createBy;

	// 创建时间
	private Timestamp createTime;

	// 最近修改人
	private int updateBy;

	// 最近修改时间
	private Timestamp updateTime;

	// 激活状态
	private String activeFlag;

}
