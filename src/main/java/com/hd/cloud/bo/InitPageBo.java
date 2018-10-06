package com.hd.cloud.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: InitPageBo
 * @Description: 启动页返回对象
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:31:05
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitPageBo implements Serializable {

	// 启动页id
	private long id;

	// 启动页列表
	private String picUrl;

	// 启动页(图片)名称
	private String picName;
	// 图片版本号
	private long pageVersion;
	// 图片组Id
	private long pageGroupId;

}
