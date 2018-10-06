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
 * @ClassName: FeedPostBase
 * @Description: 动态信息表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月8日 下午4:30:38
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPostBase {

	private int id;

	// 类型,1为动态2为话题3活动动态4投票
	private int type;

	// 话题id
	private int topicId;

	// 动态内容
	private String content;

	// 转发动态id
	private int forwardPostId;

	// 标签
	private String title;

	// 推荐
	private String recommend;

	// 经度
	private double longitude;

	// 维度
	private double latitude;

	// 城市区
	private String area;

	// 城市id
	private long cityId;

	// 1、公开， 2、好友 3、指定组员 4、指定好友
	private int visibleType;

	// 可见好友
	private String visibleFriends;

	// 可见组ID
	private String visibleGroups;

	// 用户类型 1 用户 2商家
	private int userType;

	// 是否可以评论0不可评论，1可以评论
	private int isComment;

	// 公司主键id
	private long companyId;

	// 店铺主键id
	private long shopId;

	// 店铺状态1正常2屏蔽
	private int shopStatus;

	// 地址
	private String address;

	// 点赞数
	private int starCnt;

	// 浏览量
	private int viewCount;

	// 收藏量
	private int favoriteCnt;

	// 转发量
	private int forwardCount;

	// 评论数
	private int reviewCnt;

	// 分享量
	private int shareCnt;

	// 用户id
	private long userId;

	// 发布来源:1APP;2后台
	private int appType;

	// 图片
	private String picUrl;

	// 显示状态：1是正常2是关闭3是屏蔽,4话题冻结状态
	private int status;

	// 屏蔽理由
	private String maskReason;

	// 排序
	private int sort;

	// 创建人
	private long createBy;

	private long updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp createTime;// 创建时间

	private String activeFlag;

	private List<FeedPostRes> postResourceBo;// 图片list
	
	private MomentCount momentCount;// 统计数量
	
	private boolean isStar;// 是否点赞
	
	private boolean isFavorites;// 是否收藏
	
	private UserProfile user;// 用户信息
}
