package com.hd.cloud.rest;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd.cloud.bo.FavoritesBo;
import com.hd.cloud.service.FavoritesService;
import com.hd.cloud.vo.FavoritesVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.controller.RestBase;
import com.hlb.cloud.util.CommonConstantUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: FavoritesResource
 * @Description: 收藏
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午2:22:02
 *
 */
@RefreshScope
@RestController
@RequestMapping("favorites")
public class FavoritesResource extends RestBase {

	@Inject
	private FavoritesService favoritesService;

	/**
	 * 
	 * @Title: saveFavorites
	 * @param:
	 * @Description: 收藏动态
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "saveFavorites", notes = "saveFavorites")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil saveFavorites(final @RequestBody FavoritesBo favoritesBo) {
		favoritesBo.setUserId(getLoginUserID());
		favoritesBo.setCreateBy(getLoginUserID());
		favoritesBo.setActiveFlag(CommonConstantUtil.ACTIVE_FLAG_Y);
		return favoritesService.saveFavorites(favoritesBo);
	}

	/**
	 * 
	 * @Title: removeFeedComment
	 * @param:
	 * @Description: 取消收藏
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "DELETE", value = "removeFavorites", notes = "removeFavorites")
	@ResponseBody
	@RequestMapping(value = "/{id}/{postId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public BoUtil removeFavorites(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
		id = id == null ? 0 : id;
		postId = postId == null ? 0 : postId;
		FavoritesBo favoritesBo = FavoritesBo.builder().id(id).postId(postId).updateBy(getLoginUserID())
				.activeFlag(CommonConstantUtil.ACTIVE_FLAG_D).build();
		return favoritesService.update(favoritesBo);
	}

	/**
	 * 
	 * @Title: getUserFavoritesDynamics
	 * @param:
	 * @Description: 查询用户收藏的动态(我的收藏)
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/dynamics", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public BoUtil getUserFavoritesDynamics(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		FavoritesVo favoritesVo = FavoritesVo.builder().userId(getLoginUserID()).offset(offset).pageSize(pageSize)
				.build();
		boUtil.setData(favoritesService.getFavoritesFeedPostBaseList(favoritesVo));
		return boUtil;

	}
}
