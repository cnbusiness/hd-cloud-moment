package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: FavoritesBo
 * @Description: 收藏bo
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月10日 上午11:42:36
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesBo {

	private int id;

	private int postId;// 动态id

	private String tags;// 用户动态收藏标签串，用|号分隔

	// 用户id
	private long userId;

	private Timestamp createTime;// 创建时间

	private Timestamp updateTime;// 修改时间

	private long createBy;// 创建人

	private long updateBy;// 修改人

	private String activeFlag;
}
