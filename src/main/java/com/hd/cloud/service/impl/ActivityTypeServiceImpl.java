package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hd.cloud.bo.ActivityTypeBo;
import com.hd.cloud.dao.ActivityTypeDao;
import com.hd.cloud.service.ActivityTypeService; 

/**
 * 
 * @ClassName: ActivityTypeServiceImpl
 * @Description: 活动类型
 * @author yao.jie@moxiangroup.com
 * @Company moxian
 * @date 2015年7月17日 上午10:09
 *
 */
@Service
public class ActivityTypeServiceImpl implements ActivityTypeService {

	@Inject
	private ActivityTypeDao activityTypeDao;

	@Override
	public List<ActivityTypeBo> getActivityTypeList(String countryCode) {
		return activityTypeDao.getPartyActivityTypeList(countryCode);
	}

}
