package com.hd.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.hd.cloud.util.ConstantActivityUtil;
import com.hd.cloud.vo.ActivityCountUpdateVo;

/**
 * 
  * @ClassName: ActivityCountSqlProvider
  * @Description: 修改活动统计信息
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月12日 下午2:55:39
  *
 */
public class ActivityCountSqlProvider {

	public String update(final ActivityCountUpdateVo activityCountUpdateVo) {
		return new SQL() {
			{
				UPDATE("party_activity_count_br");

				if (activityCountUpdateVo.getType() == ConstantActivityUtil.FORWARD_COUNT) {
					SET("activity_count_forward_cnt=activity_count_forward_cnt+1");
				}

				if (activityCountUpdateVo.getType() == ConstantActivityUtil.COMMENT_COUNT) {
					SET("activity_count_comment_cnt=activity_count_comment_cnt+1");

					if (!StringUtils.isBlank(activityCountUpdateVo.getPictureUrl())) {
						SET("activity_count_com_pic_cnt=activity_count_com_pic_cnt+1");
					}
				}

				if (activityCountUpdateVo.getType() == ConstantActivityUtil.FAVORITE_COUNT) {
					SET("activity_count_favorite_cnt=activity_count_favorite_cnt+1");
				}

				if (activityCountUpdateVo.getType() == ConstantActivityUtil.VIEW_COUNT) {
					SET("activity_count_view_cnt=activity_count_view_cnt+1");
				}

				if (activityCountUpdateVo.getType() == ConstantActivityUtil.SHARE_COUNT) {
					SET("activity_count_share_cnt=activity_count_share_cnt+1");
				}
				if (activityCountUpdateVo.getType() == ConstantActivityUtil.JOIN_COUNT_ADD) {
					SET("activity_count_join_cnt=activity_count_join_cnt+1");
				}
				if (activityCountUpdateVo.getType() == ConstantActivityUtil.JOIN_COUNT_REDUCE) {
					SET("activity_count_join_cnt=activity_count_join_cnt-1");
				}

				WHERE("active_flag = 'y' AND party_activity_base_bd_seq = #{activityId} ");
			}
		}.toString();
	}
}
