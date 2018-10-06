package com.hd.cloud.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hd.cloud.bo.FeedPostRes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AddMomentVo
 * @Description: 发布动态
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月8日 下午4:20:22
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddMomentVo {

	// 动态内容
	private String content;

	// 动态可见代码类型 取值范围（1-公开,2-好友,3-指定组员,4-指定好友）
	private int visibleType;

	// 地址
	private String address;

	// 维度
	private double latitude;

	// 经度
	private double longitude;

	// 转发动态id
	private int forwardPostId;

	// 动态附件
	private List<FeedPostRes> feedPostRes;

	// 话题id
	private int topicId;

	// 话题名称
	private String topicName;

	// 话题标签
	private String title;

	// 动态类型,1.动态,2.话题
	private int type;

	// 可见组
	private Long[] visibleGroupids;
	
	// 可见好友
	private Long[] visibleFriendids;

	// 城市编码
	private String area;

	private long userId;
}
