package com.hd.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.bo.FeedCommentBo;
import com.hd.cloud.bo.FeedPostBase;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.bo.FeedPostRes;
import com.hd.cloud.bo.ForwardBo;
import com.hd.cloud.bo.MomentCount;
import com.hd.cloud.bo.UserProfile;
import com.hd.cloud.dao.FavoritesDao;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.dao.FeedPostBaseDao;
import com.hd.cloud.dao.FeedPostResDao;
import com.hd.cloud.feign.AccountClient;
import com.hd.cloud.service.FavoritesService;
import com.hd.cloud.service.FeedCommentService;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.RedisKeyUtils;
import com.hd.cloud.vo.FavoritesVo;
import com.hd.cloud.vo.FeedCommentVo;
import com.hlb.cloud.bo.BoUtil;

@Service
public class FavoritesServiceImpl implements FavoritesService {

	@Inject
	private FavoritesDao favoritesDao;

	@Inject
	private FeedPostBaseDao feedPostBaseDao;

	@Autowired
	private AccountClient accountClient;

	@Inject
	private FeedPostResDao feedPostResDao;

	@Inject
	private FeedCommentService feedCommentService;

	@Inject
	private FeedPostBaseCache feedPostBaseCache;

	@Override
	@Transactional
	public BoUtil saveFavorites(FavoritesBo favoritesBo) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(favoritesBo.getPostId());
		if (feedPostBase == null) {
			boUtil.setCode(ErrorCode.POST_EXISTSING_ERROR);
			boUtil.setMsg("dynamic does not exist");
			return boUtil;
		}
		FavoritesBo favorites = favoritesDao.findPostExistsByUser(favoritesBo.getUserId(), favoritesBo.getPostId());
		if (favorites != null) {
			boUtil.setCode(ErrorCode.NOT_REPEAT_FAVORITES);
			boUtil.setMsg("Can't repeat the favorites");
			return boUtil;
		}
		// 添加
		int result = favoritesDao.save(favoritesBo);
		if (result > 0) {
			// 新增动态收藏数量
			feedPostBaseCache.updateMomentCountByHashKey(favoritesBo.getPostId(), RedisKeyUtils.FAVORITESCNT, 1);
			if (feedPostBase.getTopicId() > 0) {
				// 更新话题收藏量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.FAVORITESCNT, 1);
			}
			// 更新mysql动态数量
			MomentCount momentCount = feedPostBaseCache.getMomentCount(favoritesBo.getPostId());
			feedPostBase.setFavoriteCnt(momentCount.getFavoritesCnt());
			feedPostBase.setUpdateBy(favoritesBo.getUpdateBy());
			feedPostBaseDao.update(feedPostBase);

			boUtil = BoUtil.getDefaultTrueBo();
			boUtil.setMsg("favorite success");
		} else {
			boUtil.setMsg("favorite fail");
		}
		return boUtil;
	}

	@Override
	public BoUtil update(FavoritesBo favoritesBo) {
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(favoritesBo.getPostId());
		if (feedPostBase == null) {
			boUtil.setCode(ErrorCode.POST_EXISTSING_ERROR);
			boUtil.setMsg("dynamic does not exist");
			return boUtil;
		}
		int result = favoritesDao.update(favoritesBo);
		if (result > 0) {
			// 更新动态收藏量
			feedPostBaseCache.updateMomentCountByHashKey(favoritesBo.getPostId(), RedisKeyUtils.FAVORITESCNT, -1);
			if (feedPostBase.getTopicId() > 0) {
				// 更新话题收藏量
				feedPostBaseCache.updateTopicCountByHashKey(feedPostBase.getTopicId(), RedisKeyUtils.FAVORITESCNT, -1);
			}
			// 更新mysql动态数量
			MomentCount momentCount = feedPostBaseCache.getMomentCount(favoritesBo.getPostId());
			feedPostBase.setFavoriteCnt(momentCount.getFavoritesCnt());
			feedPostBase.setUpdateBy(favoritesBo.getUpdateBy());
			feedPostBaseDao.update(feedPostBase);

			boUtil = BoUtil.getDefaultTrueBo();
			boUtil.setMsg(" success");
		} else {
			boUtil.setMsg(" fail");
		}
		return boUtil;
	}

	@Override
	public List<FeedPostBaseBo> getFavoritesFeedPostBaseList(FavoritesVo favoritesVo) {
		List<FeedPostBaseBo> list = null;
		List<Integer> ids = Lists.newArrayList();
		List<FavoritesBo> favoritesBos = favoritesDao.getFavoritesList(favoritesVo);
		for (FavoritesBo favoritesBo : favoritesBos) {
			ids.add(favoritesBo.getPostId());
		}
		if (ids.size() > 0) {
			list = feedPostBaseDao.getFeedPostBaseListByIds(ids);
			for (FeedPostBaseBo feedPostBaseBo : list) {
				// 浏览数+1
				feedPostBaseCache.updateMomentCountByHashKey(feedPostBaseBo.getId(), RedisKeyUtils.VIEWCOUNT, 1);
				if (feedPostBaseBo.getTopicId() > 0) {
					// 更新话题浏览量
					feedPostBaseCache.updateTopicCountByHashKey(feedPostBaseBo.getTopicId(), RedisKeyUtils.VIEWCOUNT,
							1);
				}
				feedPostBaseBo.setFavorites(true);
				// 如果是转发动态 处理
				if (feedPostBaseBo.getForwardPostId() > 0) {
					FeedPostBase feedPostBase = feedPostBaseDao.getFeedPostBaseById(feedPostBaseBo.getForwardPostId());
					if (feedPostBase != null) {
						ForwardBo forwardBo = ForwardBo.builder().content(feedPostBase.getContent())
								.activeFlag(feedPostBase.getActiveFlag()).build();
						UserProfile user = accountClient.getUserProfileByUserId(feedPostBase.getUserId());
						if (user != null) {
							forwardBo.setUser(user);
						}
						List<FeedPostRes> postResourceBo = feedPostResDao
								.getFeedPostResListByPostId(feedPostBaseBo.getForwardPostId());
						forwardBo.setPostResourceBo(postResourceBo);
						feedPostBaseBo.setForward(forwardBo);
					}
				}
				// 获取前三条评论
				FeedCommentVo feedCommentVo = FeedCommentVo.builder().postId(feedPostBaseBo.getId()).offset(0)
						.pageSize(3).build();
				List<FeedCommentBo> commentBos = feedCommentService.getFeedCommentlist(feedCommentVo);
				feedPostBaseBo.setCommentBos(commentBos);
				// 获取用户信息
				UserProfile user = accountClient.getUserProfileByUserId(favoritesVo.getUserId());
				feedPostBaseBo.setUser(user);
				// 获取点赞数 喜欢数 评论数
				MomentCount momentCount = feedPostBaseCache.getMomentCount(feedPostBaseBo.getId());
				feedPostBaseBo.setMomentCount(momentCount);
			}
		}
		return list;
	}

}
