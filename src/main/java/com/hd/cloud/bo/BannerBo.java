package com.hd.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: BannerBo
 * @Description: Banner返回数据结构
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月26日 下午3:34:04
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerBo {
	// 模块名称
	private String bannerName;
	// 模块描述
	private String bannerRemark;
	// 图片集合
	private List<Ad> ad;
}
