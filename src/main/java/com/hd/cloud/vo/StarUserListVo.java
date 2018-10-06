package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: StarUserListVo
 * @Description: 动态喜欢的人vo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月11日 下午2:42:48
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarUserListVo {

	private long userId;

	private int postId;

	private int offset;

	private int pageSize;
}
