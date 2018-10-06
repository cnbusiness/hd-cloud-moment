package com.hd.cloud.util;

import java.util.Map;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.hd.cloud.bo.Ad;
import com.hd.cloud.dao.AdDao;

/**
 * 
 * @ClassName: RedisKeyUtils
 * @Description: redis key
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 上午9:17:58
 *
 */
public class RedisKeyUtils {

	// 动态统计key
	public static final String VIEWCOUNT = "viewCount";// 浏览量
	public static final String STARCOUNT = "starCount";// 点赞量
	public static final String FORWARDCOUNT = "forwardCount";// 转发量
	public static final String FAVORITESCNT = "favoritesCnt";// 收藏量
	public static final String REVIEWCNT = "reviewCnt";// 评论量
	public static final String DYNAMICCOUNT = "dynamicCount";// 动态数量

	public static final String PAL = "PAL:";
	public static final String FEED = "FEED:";
	public static final String TOPIC = "TOPIC:";
	public static final String TIMELINE = ":TIMELINE:";
	public static final String CNT = ":CNT";

	/**
	 * 
	 * @Title: postCount
	 * @param:
	 * @Description: 动态数量统计
	 * @return String
	 */
	public static String postCount(Integer postId) {
		return FEED + postId + CNT;
	}

	/**
	 * 
	 * @Title: topicCount
	 * @param:
	 * @Description: 话题统计
	 * @return String
	 */
	public static String topicCount(Integer topicId) {
		return TOPIC + topicId + CNT;
	}

	/**
	 * 
	 * @Title: timeline
	 * @param:
	 * @Description: 用户动态
	 * @return String
	 */
	public static String timeline(Long userId) {
		return PAL + userId + TIMELINE;
	}

	/**
	 * 
	 * @Title: star
	 * @param:
	 * @Description: 喜欢
	 * @return String
	 */
	public static String star(Integer postId) {
		return FEED + postId + ":STAR";
	}

	public static String feedCount(Long userId) {
		return FEED + userId + ":FEEDCNT";
	}

	// 广告
	public static final String AD = "ad";
	// 点击的游览数
	public static String visitCnt = "visitCnt";

	// 推广地址游览数
	public static String watchCnt = "watchCnt";

	// 记录店铺的推荐点击量和游览量
	public static void setAdRedisByCount(RedisTemplate<String, String> redisTemplate, long adId, String key) {
		if (!redisTemplate.boundHashOps(AD).hasKey(adId + ":" + key)) {
			redisTemplate.boundHashOps(AD).put(adId + ":" + key, "1");
		} else {
			redisTemplate.boundHashOps(AD).increment(adId + ":" + key, 1);
		}
		// 更新标识
		redisTemplate.boundHashOps(AD).put(adId + ":update:" + key, "1");
	}

	// 更新到数据库
	public static void setAdRedisToMySql(RedisTemplate<String, String> redisTemplate, AdDao adDao) {
		BoundHashOperations topicBrows = redisTemplate.boundHashOps(AD);
		Map<String, String> dataTop = topicBrows.entries();
		String adId = null;
		String key = null;
		for (Map.Entry<String, String> entry : dataTop.entrySet()) {
			if (entry.getKey().contains(":update")) {
				continue;
			}
			Ad _ad = new Ad();
			adId = entry.getKey().split(":")[0];
			key = entry.getKey().split(":")[1];
			if (redisTemplate.boundHashOps(AD).hasKey(adId + ":update:" + key)
					&& redisTemplate.boundHashOps(AD).get(adId + ":update:" + key).equals("1")) {
				// 更新数据库
				if (Integer.parseInt(entry.getValue()) > 0) {
					_ad.setId(Long.parseLong(adId));
					if (key.equals(visitCnt)) {
						_ad.setVisitCnt(Integer.parseInt(entry.getValue()));
					} else if (key.equals(watchCnt)) {
						_ad.setWatchCnt(Integer.parseInt(entry.getValue()));
					}
					adDao.update(_ad);
				}
				// 删除redis标识
				redisTemplate.boundHashOps(AD).delete(adId + ":update:" + key);
			}

		}
	}
}
