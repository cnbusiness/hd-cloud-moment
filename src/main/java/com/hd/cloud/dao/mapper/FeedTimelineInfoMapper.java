package com.hd.cloud.dao.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import com.hd.cloud.bo.FeedTimelineInfo;
import com.hd.cloud.dao.sql.FeedTimelineInfoProvider;

@Mapper
public interface FeedTimelineInfoMapper {

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.8feed_timeline_info_br（动态指定用户信息表）
	 */
	@InsertProvider(type = FeedTimelineInfoProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(FeedTimelineInfo feedTimelineInfo);
}
