package com.hd.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.dao.FavoritesDao;
import com.hd.cloud.dao.mapper.FavoritesMapper;
import com.hd.cloud.vo.FavoritesVo;

@Repository
public class FavoritesDaoMyBatisImpl implements FavoritesDao {

	@Inject
	private FavoritesMapper favoritesMapper;

	@Override
	public int save(FavoritesBo favoritesBo) {
		return favoritesMapper.save(favoritesBo);
	}

	@Override
	public int update(FavoritesBo favoritesBo) {
		return favoritesMapper.update(favoritesBo);
	}

	@Override
	public List<FavoritesBo> getFavoritesList(FavoritesVo favoritesVo) {
		return favoritesMapper.getFavoritesList(favoritesVo);
	}

	@Override
	public FavoritesBo findPostExistsByUser(long userId, int postId) {
		return favoritesMapper.findPostExistsByUser(userId, postId);
	}

}
