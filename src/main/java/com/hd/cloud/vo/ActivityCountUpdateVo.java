package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: ActivityCountUpdateVo
 * @Description: 活动统计量信息+1Vo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:54:25
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCountUpdateVo {

	private int activityId;// 活动Id
	private int type;// 类型 具体意义参考ConstantActivityUtil
	private String pictureUrl;// 评论是否有图片
}
