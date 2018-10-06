package com.hd.cloud.dao.sql;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * 
 * @ClassName: ActivityCommentSqlProvider
 * @Description: 活动评论信息
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:55:25
 *
 */
public class ActivityCommentSqlProvider {

	public String getCommentList(final Map<String, Object> map) {
		int type = Integer.parseInt(map.get("type").toString());
		return new SQL() {
			{
				SELECT(" * ");
				FROM(" party_activity_comment_bt ");
				WHERE(" active_flag='y' and party_activity_base_bd_seq=#{activityId} and activity_comment_status_itype = 1 ");
				if (type != 1) {
					AND();
					WHERE(" activity_comment_urls is not null AND activity_comment_urls <> ''");
				}
				ORDER_BY(" create_time desc limit #{pageIndex},#{pageSize}");
			}
		}.toString();
	}
}
