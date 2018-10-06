package com.hd.cloud.bo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: Ad
 * @Description: 广告实体
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:23:07
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

	// 主键
	private long id;

	// 内码
	private String internalCode;

	// 广告标题
	private String title;
	// 广告描述或备注
	private String remark;
	// 图片路径
	private String picPath;
	// 1 url 跳转 2 内部跳转'
	private int linkType;
	// 跳转地址
	private String linkUrl;
	// 位置id
	private long linkId;
	// 类型
	private long fileId;

	// 有效起始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp startTime;

	// 有效结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp endTime;
	// 1 永久有效 2 时间
	private int effectType;
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

	// 是否统计点击
	private long isSumClick;

	private long visitCnt;
	private long watchCnt;

}
