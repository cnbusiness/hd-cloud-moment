package com.hd.cloud.bo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AdModule
 * @Description: 广告模块管理Map表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:25:56
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdModule {
	// 主键
	private long id;
	// 广告模块名
	private String moduleName;
	// 广告模块描述或备注
	private String moduleRemark;
	// 最大广告数
	private int bannerTotals;
	// 1 手机端banner 广告，2 app推荐模块
	private int type;
	// banner索引标示符
	private String BannerCode;
	// 仅对启动页有效
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp pageStartTime;

	// 仅对启动页有效
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp pageEndTime;

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
