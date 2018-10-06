package com.hd.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: AllJoinActivity
 * @Description: 我参加的活动返回的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:29:55
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllJoinActivity {
	private int activityId;// 活动Id
	private String pictureUrl;// 活动图片
	private String theme;// 活动主题
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginTime;// 活动开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date finishTime;// 活动结束时间
	private String address;// 活动地点
	private int status;// 活动状态, 0.正在进行 1.活动取消 2.后台屏蔽 3.即将开始 4.成功举办
	private int joinCount;// 活动参加人数
	private int viewCount;// 活动浏览量
	private int hasShake;// 活动是否开启摇一摇 1是 2否
	private int type;// 活动类型 1.魔线 2.魔商
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date currentTime;// 当前时间
}
