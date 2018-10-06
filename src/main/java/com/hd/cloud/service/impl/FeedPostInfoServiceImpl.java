package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.FeedPostInfo;
import com.hd.cloud.bo.PostInfoBo;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.FeedPostInfoDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.service.FeedPostInfoService;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.vo.PostInfoVo;

@Service
public class FeedPostInfoServiceImpl implements FeedPostInfoService {

	@Inject
	private FeedPostInfoDao feedPostInfoDao;

	@Autowired
	private AccountClient accountClient;

	@Override
	public int save(FeedPostInfo feedPostInfo) {
		return feedPostInfoDao.save(feedPostInfo);
	}

	@Override
	public int update(FeedPostInfo feedPostInfo) {
		return feedPostInfoDao.update(feedPostInfo);
	}

	@Override
	public List<PostInfoBo> getPostInfoList(PostInfoVo postInfoVo) {
		List<UserProfile> users = Lists.newArrayList();
		List<PostInfoBo> list = feedPostInfoDao.getPostInfoList(postInfoVo);
		FeedPostInfo feedPostInfo = null;
		for (PostInfoBo postInfoBo : list) {
			if (postInfoBo.getFromUserId() > 0) {
				UserProfile userProfile = accountClient.getUserProfileByUserId(postInfoBo.getFromUserId());
				users.add(userProfile);
			}
			// 更新状态为已读
			feedPostInfo = FeedPostInfo.builder().build();
			feedPostInfo.setId(postInfoBo.getId());
			feedPostInfo.setStatus(ConstantUtil.READ_TYPE_READ);
			feedPostInfo.setUpdateBy(postInfoBo.getCreateBy());
			feedPostInfoDao.update(feedPostInfo);
		}
		for (PostInfoBo postInfoBo : list) {
			for (UserProfile userProfile : users) {
				if (postInfoBo.getFromUserId() == userProfile.getUserId()) {
					postInfoBo.setUserBo(userProfile);
				}
			}
		}

		return list;
	}

}
