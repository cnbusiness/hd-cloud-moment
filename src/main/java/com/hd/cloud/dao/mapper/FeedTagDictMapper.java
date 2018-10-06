package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hd.cloud.bo.FeedTagDictBo;

@Mapper
public interface FeedTagDictMapper {
	/**
	 * 
	 * @Title: getFeedTagDictList
	 * @Description: 查询所有标签
	 * @Table 2.3.2feed_tag_dict_bb (动态标签字典表)
	 */
	@Select("  SELECT feed_tag_dict_bb_seq AS id,tag_dict_tag_name AS NAME FROM feed_tag_dict_bb WHERE active_flag ='y' ")
	List<FeedTagDictBo> getFeedTagDictList();
}
