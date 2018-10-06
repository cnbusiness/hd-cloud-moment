package com.hd.cloud.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.hd.cloud.bo.UserPush;
import com.hd.cloud.dao.sql.UserPushSqlProvider;

/**
 * 
 * @ClassName: UserPushMapper
 * @Description: User Push
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月13日 上午10:45:53
 *
 */
@Mapper
public interface UserPushMapper {

	/**
	 * 
	 * @Title: getUserPushByUserId
	 * @param:
	 * @Description: 通过用户id查询用户的个推信息
	 * @Table push_user_device_bd 用户个推信息
	 * @return UserPush
	 */
	@Select("SELECT user_user_base_sb_seq AS userId, user_device_os_itype AS deviceType, user_device_app_itype AS appType, user_device_cid AS cid FROM "
			+ "push_user_device_bd WHERE user_user_base_sb_seq = #{userId} and user_device_app_itype = #{appType} limit 1")
	UserPush getUserPushByUserId(@Param("userId") long userId, @Param("appType") int appType);

	/**
	 * 
	 * @Title: getUserPushByCid
	 * @param:
	 * @Description: 通过cid查询用户信息
	 * @Table push_user_device_bd 用户个推信息
	 * @return UserPush
	 */
	@Select("SELECT user_user_base_sb_seq AS userId, user_device_os_itype AS deviceType, user_device_app_itype AS appType, user_device_cid AS cid  FROM "
			+ "push_user_device_bd WHERE user_device_cid= #{cid} limit 1")
	UserPush getUserPushByCid(@Param("cid") String cid);

	/**
	 * 
	 * @Title: add
	 * @param:
	 * @Description: 绑定用户个推信息
	 * @Table push_user_device_bd 用户个推信息
	 * @return void
	 */
	@InsertProvider(type = UserPushSqlProvider.class, method = "add")
	@SelectKey(keyProperty = "id", before = false, resultType = long.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void add(UserPush userPush);

	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 更新用户个推信息
	 * @Table push_user_device_bd 用户个推信息
	 * @return void
	 */
	@Update("UPDATE push_user_device_bd set user_device_cid=#{cid}, update_time=#{updateTime}, user_device_os_itype=#{deviceType} where "
			+ "user_user_base_sb_seq=#{userId} limit 1")
	void update(UserPush userPush);

	/**
	 * 
	 * @Title: delete
	 * @param:
	 * @Description: 删除用户绑定信息
	 * @Table push_user_device_bd 用户个推信息
	 * @return void
	 */
	@Delete("DELETE FROM push_user_device_bd  WHERE user_user_base_sb_seq = #{userId} ")
	void delete(@Param("userId") long userId, @Param("cid") String cid);

	/**
	 * 
	 * @Title: getBatchUserId
	 * @param:
	 * @Description: 获得绑定的用户id
	 * @Table push_user_device_bd 用户个推信息表
	 * @return List<Long>
	 */
	@SelectProvider(type = UserPushSqlProvider.class, method = "getBatchUserId")
	@ResultType(value = java.lang.Long.class)
	List<Long> getBatchUserId(@Param("userIdList") List<Long> userIdList);

	/**
	 * 
	 * @Title: getBatchUserId
	 * @param:
	 * @Description: 获得绑定的用户id
	 * @Table push_user_device_bd 用户个推信息表
	 * @return List<Long>
	 */
	@SelectProvider(type = UserPushSqlProvider.class, method = "getCidList")
	List<String> getCidList(@Param("userIdList") List<Long> userIdList, @Param("appType") int appType,
			@Param("deviceType") int deviceType);

	@Select("SELECT user_user_base_sb_seq AS userId, user_device_os_itype AS deviceType, user_device_app_itype AS appType, user_device_cid AS cid FROM "
			+ "push_user_device_bd WHERE user_user_base_sb_seq = #{userId} and user_device_app_itype = #{appType} limit 0,10000")
	List<UserPush> findUserPushByUserId(@Param("userId") long userId, @Param("appType") int appType);
}
