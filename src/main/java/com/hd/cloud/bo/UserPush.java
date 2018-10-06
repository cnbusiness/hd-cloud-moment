package com.hd.cloud.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserPush
  * @Description: 用户绑定推送信息
  * @author ShengHao shenghaohao@hadoop-tech.com
  * @Company hadoop-tech 
  * @date 2018年4月13日 上午10:42:49
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPush {

	private long id;

	/**
	 * 用户
	 */
	private long userId;

	/**
	 * 
	 */
	private String cid;

	/**
	 * 设备类型
	 */
	private int deviceType;

	/**
	 * 应用类型
	 */
	private int appType;

	/**
	 * 创建者
	 */
	private int createBy;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 最近修改人
	 */
	private int updateBy;

	/**
	 * 最近修改时间
	 */
	private Timestamp updateTime;

	/**
	 * 激活状态
	 */
	private String activeFlag;

}
