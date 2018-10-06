package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.ActivityJoinBo;
import com.hd.cloud.vo.ActivitySignUpVo;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: ActivityJoinService
 * @Description: 活动报名人
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午4:38:37
 *
 */
public interface ActivityJoinService {

	/**
	 * 
	 * @Title: signUpActivity
	 * @param: ActivitySignUpVo
	 *             activitySignUpVo,long userId
	 * @Description: 报名活动
	 * @return BoUtil
	 */
	BoUtil signUpActivity(ActivitySignUpVo activitySignUpVo, long userId);

	/**
	 * 
	 * @Title: cancelSignUpActivity
	 * @param: int
	 *             activityId,long userId
	 * @Description: 取消报名活动
	 * @return BoUtil
	 */
	BoUtil cancelSignUpActivity(int activityId, long userId);

	/**
	 * 
	 * @Title: getActivityJoinList
	 * @param: int
	 *             activityId,int pageIndex,int pageSize,int type,long userId
	 * @Description: 获取活动已报名列表
	 * @return List<ActivityJoinBo>
	 */
	List<ActivityJoinBo> getActivityJoinList(int activityId, int pageIndex, int pageSize, int type, long userId);

}
