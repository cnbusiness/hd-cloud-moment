package com.hd.cloud.dao;

import java.util.List;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.vo.FavoritesVo;

/**
 * 
  * @ClassName: FavoritesDao
  * @Description: 用户收藏管理
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月10日 上午11:44:16
  *
 */
public interface FavoritesDao {

	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存 
	* @return int
	 */
	public int save(FavoritesBo favoritesBo);
	
	/**
	 * 
	* @Title: update 
	* @param: 
	* @Description: 编辑
	* @return int
	 */
	public int update(FavoritesBo favoritesBo);
	
	
	/**
	 * 
	* @Title: getFavoritesList 
	* @param: 
	* @Description: 获取用户收藏列表
	* @return List<FavoritesBo>
	 */
	public List<FavoritesBo> getFavoritesList(FavoritesVo favoritesVo);
	
	/**
	 * 
	* @Title: findPostExistsByUser 
	* @param: 
	* @Description: 查询是否收藏过
	* @return FavoritesBo
	 */
	public FavoritesBo findPostExistsByUser(long userId,int postId);
}
