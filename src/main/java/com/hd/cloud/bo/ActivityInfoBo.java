package com.hd.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityInfoBo
 * @Description: 活动信息的BO
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:33:41
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityInfoBo {
	private int activityId;
	private String activityTheme;
	private String activityDesc;
	private String activityPicture;
	private double longitude;
	private double latitude;
	private String activityAddress;
	private String beginTime;
	private String endTime;
	private int activityType;
	private String activityTypeName;
	private int activityOrganizerType; // 1是app用户2app商家3后台用户4后台商家
	private long createBy;
	private String createName;
	private String createLogo;
	private int joinNumber;
	private int activityStatus; // 活动状态, 1活动取消, 2后台屏蔽,0正常
}
