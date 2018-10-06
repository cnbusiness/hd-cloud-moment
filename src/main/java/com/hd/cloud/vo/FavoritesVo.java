package com.hd.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FavoritesVo
 * @Description: 收藏vo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午11:47:13
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesVo {

	private long userId;

	private int offset;

	private int pageSize;
}
