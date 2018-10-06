package com.hd.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ForwardBo
 * @Description: 转发信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 下午4:21:59
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForwardBo {
	private String content;// 内容
	private UserProfile user;// 用户
	private List<FeedPostRes> postResourceBo;// 图片
	private String activeFlag;// 是否有效
}
