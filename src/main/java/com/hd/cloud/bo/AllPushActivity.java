package com.hd.cloud.bo;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AllPushActivity
 * @Description: 我发布的活动返回的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:30:05
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllPushActivity {
	private int activityId;// 活动Id
	private String pictureUrl;// 活动图片
	private String theme;// 活动主题
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;// 活动创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginTime;// 活动开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date finishTime;// 活动结束时间
	private int status;// 活动状态, 1活动取消, 2后台屏蔽,0正常
	private int joinCount;// 活动参加人数
	private int viewCount;// 活动浏览量
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date currentTime;// 当前时间

}
