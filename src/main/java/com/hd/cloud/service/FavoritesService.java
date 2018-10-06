package com.hd.cloud.service;

import java.util.List;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.bo.FeedPostBaseBo;
import com.hd.cloud.vo.FavoritesVo;
import com.hlb.cloud.bo.BoUtil;

/**
 * 
 * @ClassName: FavoritesService
 * @Description: 收藏管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 下午12:12:08
 *
 */

public interface FavoritesService {

	/**
	 * 
	 * @Title: saveFavorites
	 * @param:
	 * @Description: 保存收藏
	 * @return BoUtil
	 */
	public BoUtil saveFavorites(FavoritesBo favoritesBo);

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 修改
	 * @return BoUtil
	 */
	public BoUtil update(FavoritesBo favoritesBo);

	/**
	 * 
	 * @Title: getFavoritesFeedPostBaseList
	 * @param:
	 * @Description: 获取用户收藏动态列表
	 * @return List<FeedPostBaseBo>
	 */
	public List<FeedPostBaseBo> getFavoritesFeedPostBaseList(FavoritesVo favoritesVo);
}
