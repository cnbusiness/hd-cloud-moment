package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.dao.sql.TopicSectionProvider;
import com.hd.cloud.vo.TopicSectionVo;

@Mapper
public interface TopicSectionMapper {

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @Table 2.3.11topic_topic_section_bd(话题栏目字典表）
	 */
	@InsertProvider(type = TopicSectionProvider.class, method = "save")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID() AS id  " }, keyProperty = "id", before = false, resultType = Integer.class)
	int save(TopicSection topicSection);

	/**
	 * 
	 * @Title: update
	 * @Description: 编辑
	 * @Table 2.3.11topic_topic_section_bd(话题栏目字典表）
	 */
	@UpdateProvider(type = TopicSectionProvider.class, method = "update")
	int update(TopicSection topicSection);

	/**
	 * 
	 * @Title: getTopicSectionList
	 * @Description: 查询话题列表
	 * @Table 2.3.11topic_topic_section_bd(话题栏目字典表）
	 */
	@SelectProvider(type = TopicSectionProvider.class, method = "getTopicSectionList")
	@Results(value = {
			@Result(property = "id", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "topic_section_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "desc", column = "topic_section_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "iconUrl", column = "topic_section_icon_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "backgroudUrl", column = "topic_section_backgroud_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "isDisplay", column = "topic_section_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isNews", column = "topic_section_news_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "homeUrl", column = "topic_section_home_url", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	List<TopicSection> getTopicSectionList(TopicSectionVo topicSectionVo);
	
	
	/**
	 * 
	 * @Title: getTopicSectionById
	 * @Description: 通过话题id查询详情
	 * @Table 2.3.11topic_topic_section_bd(话题栏目字典表）
	 */
	@Select(" SELECT topic_topic_section_bd_seq,topic_section_name,topic_section_desc,topic_section_icon_url,topic_section_backgroud_url,topic_section_home_url,topic_section_display_itype,topic_section_news_itype FROM topic_topic_section_bd  WHERE active_flag='y' AND topic_topic_section_bd_seq=#{topicId} ")
	@Results(value = {
			@Result(property = "id", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "topic_section_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "desc", column = "topic_section_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "iconUrl", column = "topic_section_icon_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "backgroudUrl", column = "topic_section_backgroud_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "isDisplay", column = "topic_section_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isNews", column = "topic_section_news_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "homeUrl", column = "topic_section_home_url", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	TopicSection getTopicSectionById(@Param("topicId") int topicId);
	
	
	/**
	 * 
	 * @Title: getTopicSectionByName
	 * @Description: 通过话题名称查询详情
	 * @Table 2.3.11topic_topic_section_bd(话题栏目字典表）
	 */
	@Select(" SELECT topic_topic_section_bd_seq,topic_section_name,topic_section_desc,topic_section_icon_url,topic_section_backgroud_url,topic_section_home_url,topic_section_display_itype,topic_section_news_itype FROM topic_topic_section_bd  WHERE active_flag='y' AND topic_section_name=#{name} limit 0,1")
	@Results(value = {
			@Result(property = "id", column = "topic_topic_section_bd_seq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "topic_section_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "desc", column = "topic_section_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "iconUrl", column = "topic_section_icon_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "backgroudUrl", column = "topic_section_backgroud_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "isDisplay", column = "topic_section_display_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "isNews", column = "topic_section_news_itype", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "homeUrl", column = "topic_section_home_url", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	TopicSection getTopicSectionByName(@Param("name") String name);
	
}
