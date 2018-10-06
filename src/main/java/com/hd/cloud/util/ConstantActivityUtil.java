package com.hd.cloud.util;

/**
 * 
 * @ClassName: ConstantActivityUtil
 * @Description: 修改活动统计信息常量
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:54:51
 *
 */
public class ConstantActivityUtil {

	// 活动转发量
	public final static int FORWARD_COUNT = 1;

	// 活动浏览量
	public final static int VIEW_COUNT = 2;

	// 活动分享量
	public final static int SHARE_COUNT = 3;

	// 活动评论量
	public final static int COMMENT_COUNT = 4;

	// 活动收藏量
	public final static int FAVORITE_COUNT = 5;

	// 活动报名人数+1
	public final static int JOIN_COUNT_ADD = 6;

	// 活动报名人数-1
	public final static int JOIN_COUNT_REDUCE = 7;

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
	public final static int SHOPSCREENING_SHEILD = 2;

	// 活动抽奖 不在现场
	public final static int ACTIVITY_LOTTER_ABSENCE = 1;

	// 活动抽奖 恭喜中奖
	public final static int ACTIVITY_LOTTER_WINNING = 2;
}
