package com.hd.cloud.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.bo.TopicSection;
import com.hd.cloud.vo.TopicSectionVo;

public class TopicSectionProvider {

	/**
	 * 
	 * @Title: save
	 * @param:
	 * @Description: 保存
	 * @return String
	 */
	public String save(TopicSection topicSection) {
		String sql = new SQL() {
			{
				INSERT_INTO("topic_topic_section_bd");
				VALUES("topic_section_name", "#{name}");
				VALUES("topic_section_desc", "#{desc}");
				VALUES("topic_section_display_itype", "#{isDisplay}");
				VALUES("topic_section_source_itype", "#{sourceType}");
				VALUES("topic_section_check_itype", "#{isCheck}");
				VALUES("city_dict_country_code", "#{countryCode}");
				
				
				VALUES("create_time", "now()");
				VALUES("create_by", "#{createBy}");
				VALUES("active_flag", "#{activeFlag}");
			}
		}.toString();
		return sql;
	}

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑
	 * @return String
	 */
	public String update(TopicSection topicSection) {
		String sql = new SQL() {
			{
				UPDATE("topic_topic_section_bd");

				SET("update_time=now()");
				SET("update_by=#{updateBy} ");
				WHERE("topic_topic_section_bd_seq = #{id} ");
			}
		}.toString();
		return sql;
	}

	/**
	 * 
	 * @Title: getTopicSectionList
	 * @param:
	 * @Description: 查询列表
	 * @return String
	 */
	public String getTopicSectionList(TopicSectionVo topicSectionVo) {
		String sql = new SQL() {
			{
				SELECT("topic_topic_section_bd_seq,topic_section_name,topic_section_desc,topic_section_icon_url,topic_section_backgroud_url,topic_section_home_url,topic_section_display_itype,topic_section_news_itype");
				FROM("topic_topic_section_bd");
				WHERE(" active_flag='y' AND topic_section_check_itype=1");
				
				if(topicSectionVo.getType()==1) {
					AND();
					WHERE(" topic_section_source_itype=2 AND topic_section_news_itype=1 ");
				}else {
					AND();
					WHERE(" topic_section_news_itype!=1 ");
				}
				ORDER_BY(" topic_section_review_cnt DESC LIMIT #{offset},#{pageSize}");
			}
		}.toString();
		return sql;
	}
}
