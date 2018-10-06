package com.hd.cloud.bo;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: PushRecord
 * @Description: 推送记录对象
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:43:14
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushRecord {

	@Id
	private long id;

	/**
	 * 发送者来源userid
	 */
	private long fromUserId;

	/**
	 * 发送者的app类型
	 */
	private int fromAppType;

	/**
	 * 发送者公司id
	 */
	private long fromCompanyId;

	/**
	 * 推送者id
	 */
	private long toUserId;

	/**
	 * 
	 */
	private String toCid;

	private long toCompanyId;

	private int toAppType;

	private int deviceType;

	private int messageType;

	private int contentType;

	private String content;

	private int status;

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
