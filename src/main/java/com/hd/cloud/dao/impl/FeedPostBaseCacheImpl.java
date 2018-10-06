package com.hd.cloud.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.hd.cloud.bo.MomentCount;
import com.hd.cloud.bo.TopicCount;
import com.hd.cloud.dao.FeedPostBaseCache;
import com.hd.cloud.util.RedisKeyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FeedPostBaseCacheImpl implements FeedPostBaseCache {

	@Inject
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public void star(int postId, long userId) {
		String key = RedisKeyUtils.star(postId);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				boolean bool = connection.zAdd(key.getBytes(), System.currentTimeMillis(),
						serializer.serialize(String.valueOf(userId)));
				log.info("###################star result:{}", bool);
				if (bool) {
					// 更新统计数量
					updateMomentCountByHashKey(postId, RedisKeyUtils.STARCOUNT, 1);
				}
				return bool;
			}
		});
	}

	@Override
	public boolean unStar(int postId, long userId) {
		String key = RedisKeyUtils.star(postId);
		return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Long result = connection.zRem(key.getBytes(), serializer.serialize(String.valueOf(userId)));
				log.info("###################un star result:{}", result);
				if (result!=null && result > 0) {
					// 更新统计数量
					updateMomentCountByHashKey(postId, RedisKeyUtils.STARCOUNT, -1);
				}
				if (result==null ) {
					return false;
				}else {
					return result > 0 ? true : false;
				}
			}
		});
	}

	@Override
	public void deleteStar(int postId) {
		String key = RedisKeyUtils.star(postId);
		redisTemplate.delete(key);
	}

	@Override
	public boolean hasStar(int postId, Long userId) {
		String key = RedisKeyUtils.star(postId);
		return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				boolean bool=connection.exists(key.getBytes());
				if(bool) {
					Double result = connection.zScore(key.getBytes(), serializer.serialize(String.valueOf(userId)));
					log.info("###################has star result:{}", result);
					if(result==null) {
						return false;
					}else {
						return result > 0 ? true : false;
					}
				}else {
					return false;
				}
			}
		});
	}

	@Override
	public List<Long> getPostStarUserIdByPostId(int postId, int start, int end) {
		String key = RedisKeyUtils.star(postId);
		List<Long> list = Lists.newArrayList();
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> sets =connection.zRevRangeByScore(key.getBytes(),0,System.currentTimeMillis(), start, end);
				Iterator<byte[]> iter = sets.iterator();
				RedisSerializer<String> rss = redisTemplate.getStringSerializer();
				while (iter.hasNext()) {
					byte[] b = iter.next();
					String b2s = rss.deserialize(b);
					list.add(Long.valueOf(b2s));
				}
				return null;
			}
		});
		return list;
	}

	@Override
	public void updateMomentCountByHashKey(Integer postId, String hashKey, Integer i) {
		String key = RedisKeyUtils.postCount(postId);
		Long result = redisTemplate.opsForHash().increment(key, hashKey, i);
		log.info("#################updateMomentCountByHashKey:{},result:{}", hashKey, result);
	}

	@Override
	public MomentCount getMomentCount(int postId) {
		String key = RedisKeyUtils.postCount(postId);
		// 获取hash
		RedisMap<String, String> map = new DefaultRedisMap<>(key, redisTemplate);
		log.info("#######map:{},type:{}", map, map.get(RedisKeyUtils.VIEWCOUNT).getClass());
		MomentCount momentCount = new MomentCount();
		momentCount.setViewCount(StringUtils.isBlank(map.get(RedisKeyUtils.VIEWCOUNT)) ? 0
				: Integer.valueOf(map.get(RedisKeyUtils.VIEWCOUNT)));
		momentCount.setStarCount(StringUtils.isBlank(map.get(RedisKeyUtils.STARCOUNT)) ? 0
				: Integer.valueOf(map.get(RedisKeyUtils.STARCOUNT)));
		momentCount.setForwardCount(StringUtils.isBlank(map.get(RedisKeyUtils.FORWARDCOUNT)) ? 0
				: Integer.valueOf(map.get(RedisKeyUtils.FORWARDCOUNT)));
		momentCount.setFavoritesCnt(StringUtils.isBlank(map.get(RedisKeyUtils.FAVORITESCNT)) ? 0
				: Integer.valueOf(map.get(RedisKeyUtils.FAVORITESCNT)));
		momentCount.setReviewCnt(StringUtils.isBlank(map.get(RedisKeyUtils.REVIEWCNT)) ? 0
				: Integer.valueOf(map.get(RedisKeyUtils.REVIEWCNT)));
		return momentCount;
	}

	@Override
	public void deleteMomentCount(int postId) {
		String key = RedisKeyUtils.postCount(postId);
		redisTemplate.delete(key);
		// 同时删除喜欢的数据
		deleteStar(postId);
	}

	@Override
	public void updateTopicCountByHashKey(Integer topicId, String hashKey, Integer i) {
		String key = RedisKeyUtils.topicCount(topicId);
		Long result = redisTemplate.opsForHash().increment(key, hashKey, i);
		log.info("#################updateMomentCountByHashKey:{},result:{}", hashKey, result);
	}

	@Override
	public TopicCount getTopicCount(int topicId) {
		String key = RedisKeyUtils.topicCount(topicId);
		// 获取hash
		RedisMap<String, String> map = new DefaultRedisMap<>(key, redisTemplate);
		log.info("#################map:{},VIEWCOUNT:{}",map,map.get(RedisKeyUtils.VIEWCOUNT));
		TopicCount topicCount = new TopicCount(); 
		if(map.get(RedisKeyUtils.VIEWCOUNT)==null) {
			topicCount.setViewCount(new AtomicLong(0));
		}else {
			topicCount.setViewCount(new AtomicLong(Long.valueOf(map.get(RedisKeyUtils.VIEWCOUNT))));
		}
		
		log.info("#################topicCount:{}",topicCount);
		topicCount.setStarCount(
				map.get(RedisKeyUtils.STARCOUNT) == null ? new AtomicLong(0) : new AtomicLong(Long.valueOf(map.get(RedisKeyUtils.STARCOUNT))));
		topicCount.setDynamicCount(
				map.get(RedisKeyUtils.DYNAMICCOUNT) == null ? new AtomicLong(0) : new AtomicLong(Long.valueOf(map.get(RedisKeyUtils.DYNAMICCOUNT))));
		topicCount.setFavoritesCnt(
				map.get(RedisKeyUtils.FAVORITESCNT) == null ? new AtomicLong(0) : new AtomicLong(Long.valueOf(map.get(RedisKeyUtils.FAVORITESCNT))));
		return topicCount;
	}

	@Override
	public void addUserTimeline(Long userId, Integer postId) {
		String key = RedisKeyUtils.timeline(userId);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				boolean bool = connection.zAdd(key.getBytes(), System.currentTimeMillis(),
						serializer.serialize(String.valueOf(postId)));
				log.info("###################addUserTimeline result:{}", bool);
				return bool;
			}
		});
	}

	@Override
	public List<Integer> getUserTimeline(Long userId, long start, long end) {
		String key = RedisKeyUtils.timeline(userId);
		List<Integer> list = Lists.newArrayList();
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> sets = connection.zRevRangeByScore(key.getBytes(),0,System.currentTimeMillis(), start, end);
				Iterator<byte[]> iter = sets.iterator();
				RedisSerializer<String> rss = redisTemplate.getStringSerializer();
				while (iter.hasNext()) {
					byte[] b = iter.next();
					String b2s = rss.deserialize(b);
					list.add(Integer.valueOf(b2s));
				}
				return null;
			}
		});
		return list;
	}

	@Override
	public void deleteUserTimeline(Long userId, Integer postId) {
		String key = RedisKeyUtils.timeline(userId);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Long result = connection.zRem(key.getBytes(), serializer.serialize(String.valueOf(postId)));
				log.info("###################deleteUserTimeline result:{}", result);
				if(result==null) {
					return false;
				}else {
					return result > 0 ? true : false;
				}
			}
		});
	}

}
