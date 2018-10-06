package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AdClick
 * @Description: 广告点击实体
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:25:27
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdClick {

	private long adClickId;
	private long adId;
	private long cityId;
	private long userId;
	// 创建者
	private int createBy;
	// 创建时间
	// private Timestamp createTime;
	// 最近修改人
	private int updateBy;
	// 最近修改时间
	// private Timestamp updateTime;
	// 激活状态
	private String activeFlag;

}
