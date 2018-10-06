package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.ActivityTypeBo;
import com.hd.cloud.dao.ActivityTypeDao;
import com.hd.cloud.dao.mapper.PartyActivityTypeMapper;

@Repository
public class ActivityTypeDaoMyBatisImpl implements ActivityTypeDao{

	@Inject
	private PartyActivityTypeMapper partyActivityTypeMapper;
	
	@Override
	public List<ActivityTypeBo> getPartyActivityTypeList(String countryCode) {
		return partyActivityTypeMapper.getPartyActivityTypeList(countryCode);
	}

}
