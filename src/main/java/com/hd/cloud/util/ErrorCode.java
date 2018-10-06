package com.hd.cloud.util;

/**
 * 
 * @ClassName: ErrorCode
 * @Description: 错误码
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月20日 下午5:27:10
 *
 */
public class ErrorCode {

	// 请输入动态
	public final static String MOMENT_EMPTY = "hd0040001";

	// 动态不能超过500字
	public final static String MOMENT_LIMIT_ERROR = "hd0040002";

	// 请输入评论
	public final static String COMMENT_EMPTY = "hd0040003";

	// 动态id不能为空
	public final static String POSTID_EMPTY = "hd0040004";

	// 动态不存在
	public final static String POST_EXISTSING_ERROR = "hd0040005";

	// 已经收藏过了
	public final static String NOT_REPEAT_FAVORITES = "hd0040006";

	// 你已经喜欢过该动态了
	public final static String EXIST_STAR = "hd0040007";

	// 没有喜欢过该动态
	public final static String NOT_START_ERROR = "hd0040008";

	/*
	 * 活动不存在
	 */
	public static final String ACTIVITY_NOT_FOUND = "hd0040009";

	/*
	 * 活动已结束
	 */
	public static final String ACTIVITY_HAD_END = "hd0040010";

	/*
	 * 您不是活动的创建人，无权修改
	 */
	public final static String YOU_ARE_NOT_CREATER = "hd0040011";

	/*
	 * 报名手机号码不能为空
	 */
	public static final String PHONE_IS_EMPTY = "hd0040012";

	/*
	 * 手机号码格式错误
	 */
	public static final String PHONE_FORMAT_ERROR = "hd0040013";

	/*
	 * 报名说明不能超过30个字符
	 */
	public static final String LENGTH_OF_EXPLAIN = "hd0040014";

	/*
	 * 您已报名该活动
	 */
	public static final String YOU_HAD_SIGNUP_ACTIVITY = "hd0040015";

	/*
	 * 您还没有报名该活动
	 */
	public static final String YOU_HAD_NOT_SIGNUP_ACTIVITY = "hd0040016";

	/*
	 * 活动报名人数已满
	 */
	public static final String SIGNUP_COUNT_TO_FULL = "hd0040017";

	/*
	 * 图片不能为空
	 */
	public static final String PICTUREURLS_IS_EMPTY = "hd0040018";

	/*
	 * 图片不能超过六张
	 */
	public static final String NUMBER_OF_PICTURE = "hd0040019";

	/*
	 * 活动主题长度范围1~50
	 */
	public static final String LENGTH_OF_THEME = "hd0040020";

	/*
	 * 活动开始时间不能为空
	 */
	public static final String STARTTIME_IS_EMPTY = "hd0040021";

	/*
	 * 活动结束时间不能为空
	 */
	public static final String ENDTIME_IS_EMPTY = "hd0040022";

	/*
	 * 开始时间不能大于结束时间
	 */
	public static final String STARTTIME_MORE_THAN_ENDTIME = "hd0040023";

	/*
	 * 活动时间不能超过90天
	 */
	public static final String ACTIVITY_TIME = "hd0040024";

	/*
	 * 活动地址不能为空
	 */
	public static final String ADDRESS_IS_EMPTY = "hd0040025";

	/*
	 * 城市ID不能为空
	 */
	public static final String CITYID_IS_EMPTY = "hd0040026";

	/*
	 * 经纬度不能为空
	 */
	public static final String COORDINATES_IS_EMPTY = "hd0040027";

	/*
	 * 活动介绍长度范围1~500
	 */
	public static final String DETAIL_IS_EMPTY = "hd0040028";

	/*
	 * 活动类别不能为空
	 */
	public static final String ACTIVITYTYPEID_IS_EMPTY = "hd0040029";

	/*
	 * 报名是否需要手机号码格式错误
	 */
	public static final String ISNEEDPHONE_FORMAT_ERROR = "hd0040030";

	/*
	 * 活动ID不能为空
	 */
	public static final String ACTIVITYID_IS_EMPTY = "hd0040031";

	/*
	 * 评论者不能为空
	 */
	public static final String COMMENTER_IS_EMPTY = "hd0040032";

	/*
	 * 活动评论上级ID不能为空
	 */
	public static final String ACTIVITY_COMMENT_PARENT_IS_EMPTY = "hd0040033";

	/*
	 * 活动评论内容或者图片不能为空
	 */
	public static final String ACTIVITY_COMMENT_CONTENT_IS_EMPTY = "hd0040034";

	/*
	 * 活动评论内容不能超过200个字符
	 */
	public static final String LENGTH_OF_CONTENT = "hd0040035";
	
	/*
	 * 活动类型错误
	 */
	public static final String ACTIVITY_TYPE_ERROR = "hd0040036";
	
	/*
	 * 推送IOS模板设置异常
	 */
	public final static String PUSH_IOS_TMPL_EXCEPTION = "hd0040037";

	/*
	 * 推送发生异常
	 */
	public final static String PUSH_EXCEPTION = "hd0040038";

}
