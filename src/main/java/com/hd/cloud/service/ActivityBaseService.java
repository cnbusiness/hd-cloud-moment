package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.ActivityHomeListBo;
import com.hd.cloud.bo.ActivityInfoBo;
import com.hd.cloud.bo.AllJoinActivity;
import com.hd.cloud.bo.AllPushActivity;
import com.hd.cloud.bo.HotActivityBo;
import com.hd.cloud.vo.MXActivityVo;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: ActivityBaseService
 * @Description: 活动基础信息管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:06:42
 *
 */
public interface ActivityBaseService {

	/**
	 * 
	 * @Title: addActivity
	 * @param: MXActivityVo
	 *             activityVo,long userId
	 * @Description: 发布魔线活动
	 * @return
	 */
	String addActivity(MXActivityVo activityVo, long userId);

	/**
	 * 
	 * @Title: checkActivityRequire
	 * @param: int
	 *             activityId
	 * @Description: 检测活动报名是否需要填写手机号码
	 * @return Integer
	 */
	Integer checkActivityRequire(int activityId);

	/**
	 * 
	 * @Title: getActivityList
	 * @param: int
	 *             activityId
	 * @Description: 获取活动首页列表
	 * @return ActivityHomeListBo
	 */
	ActivityHomeListBo getActivityList(int pageIndex, int pageSize, int activityTypeId, int activityTime, int type,
			int cityId, double longitude, double latitude, long userId);

	/**
	 * 
	 * @Title: endActivity
	 * @param: int
	 *             activityId
	 * @Description: 结束活动
	 * @return
	 */
	BoUtil endActivity(int activityId, long userId, int type, int shopId);

	/**
	 * 
	 * @Title: getAllPushActivity
	 * @param: long
	 *             userId
	 * @Description: 获取我发布的所有活动
	 * @return List<AllPushActivity>
	 */
	List<AllPushActivity> getAllPushActivity(long userId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: getAllJoinActivity
	 * @param: long
	 *             userId
	 * @Description: 获取我参加的所有活动
	 * @return List<AllJoinActivity>
	 */
	List<AllJoinActivity> getAllJoinActivity(long userId, int pageIndex, int pageSize);

	/**
	 * 
	 * @Title: HotActivityListBo
	 * @param: int
	 *             cityId,int begin,int end
	 * @Description: 获取城市热门活动
	 * @return HotActivityBo
	 */
	HotActivityBo getHotActivity(int cityId, int begin, int end, double longitude, double latitude, long loginUserId);

	/**
	 * 获取活动信息
	 * 
	 * @param activityId
	 * @return
	 */
	ActivityInfoBo getActivityById(int activityId);
}
