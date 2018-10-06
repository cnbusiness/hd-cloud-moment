package com.hd.cloud.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FeedPostBaseBo
 * @Description: 动态Bo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 下午4:17:03
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPostBaseBo {

	private int id;
	private String content;// 内容
	private double latitude;// 纬度
	private double longitude;// 经度
	private int forwardPostId;// 转发id
	private int visibleType;//1、公开，2、好友 3、指定组员 4、指定好友
	private String address;// 地址
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp createTime;// 创建时间
	private String visibleGroups;// 可见组
	private String visibleFriends;// 可见用户
	private List<FeedPostRes> postResourceBo;// 图片list
	private List<FeedCommentBo> commentBos;// 评论列表
	private MomentCount momentCount;// 统计数量
	private UserProfile user;// 用户信息
	private ForwardBo forward;// 转发信息
	private boolean isStar;// 是否点赞
	private boolean isFavorites;// 是否收藏
	private long userId;// 作者
	private int type;// 类型,1.动态,2.话题
	private int topicId;// 话题/活动/投票id
	private String topicName;// 话题标签
	private int status;//显示状态：1是正常2是关闭3是屏蔽,4话题冻结状态
}
