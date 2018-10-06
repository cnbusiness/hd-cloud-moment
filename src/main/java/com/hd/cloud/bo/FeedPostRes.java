package com.hd.cloud.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FeedPostRes
 * @Description: 动态资源
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月9日 下午4:43:15
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPostRes {

	private int id;

	// 资源路径
	private String path;

	// 动态id
	private int postId;

	// 是否显示主页
	private int isAvatar;

	// 创建人
	private long createBy;

	private long updateBy;

	private String activeFlag;
}
