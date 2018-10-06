package com.hd.cloud.util;

/**
 * 
 * @ClassName: ConstantUtil
 * @Description: 常量字典
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月20日 下午5:24:57
 *
 */
public class ConstantUtil {
	// 关注，
	public final static int ATTENTION = 1;
	// 取消
	public final static int NOT_ATTENTION = 2;
	// 已读
	public final static int READ_TYPE_READ = 1;
	// 未读
	public final static int READ_TYPE_UNREAD = 2;
	// 操作类型1，评论
	public final static int OPERATION_COMMONT = 1;
	// 操作类型2.点赞
	public final static int OPERATION_STAR = 2;
	// 操作类型3.回复
	public final static int OPERATION_REPLY = 3;
	// 操作类型 转发
	public final static int OPERATION_FORWARD = 4;
	// 操作类型 参与
	public final static int OPERATION_JOIN = 5;

	// 类型1，动态
	public final static int TYPE_POST = 1;
	// 类型2.话题
	public final static int TYPE_TOPIC = 2;
	// 类型3.活动
	public final static int TYPE_ACTIVITIES = 3;
	// 类型4.投票
	public final static int TYPE_VOTE = 4;
	// 类型5.店铺预览动态
	public final static int SHOP_PREVIEW = 5;
	// 动态类型1.魔友动态
	public final static int POST_MOXIAN = 1;
	// 动态类型2.店铺动态
	public final static int POST_SHOP = 2;
	// 资质认证待审核
	public final static int QUALIFICATION_NO = 1;
	// 资质认证审核中
	public final static int QUALIFICATION_IN = 2;
	// 资质认证审核通过
	public final static int QUALIFICATION_PASS = 3;
	// 资质认证审核退回
	public final static int QUALIFICATION_RETURN = 4;
	// 店铺屏蔽状态正常
	public final static int SHOPSCREENING_NORMAL = 1;
	// 店铺屏蔽状态屏蔽
	public final static int SHOPSCREENING_SHIELD = 2;

	public static class DeviceType {

		// 1 位android
		public static final int ANDROID = 1;

		// 2 位IOS企业版
		public static final int IOS_ETERPRISE = 2;

		// 3 位IOS企业版
		public static final int IOS_APPSTORE = 3;

		// 4 位android google版
		public static final int ANDROID4 = 4;

	}
	

	public static class PushTitle {
		public static final String ASCS = "用户版";
	}
	
	public final static String INIT_PAGE_CODE = "INITPAGE_BANNER";
	
	public static final int APP_BANNER_TYPE = 1;

}
