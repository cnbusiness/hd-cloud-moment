package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityAddress;
import com.hd.cloud.bo.ActivityAddressBo;
import com.hd.cloud.dao.sql.ActivityAddressSqlProvider; 

/**
 * 
 * @ClassName: ActivityAddressMapper
 * @Description: 活动地址
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午2:51:14
 *
 */
@Mapper
public interface ActivityAddressMapper {

	/**
	 * @Title: addActivityAddress
	 * @Description: 新增活动地址
	 * @Table party_activity_address_br 活动地址表
	 */
	@Insert("insert into party_activity_address_br "
			+ " (party_activity_base_bd_seq,biz_mobiz_shop_bd_seq,activity_address_latitude,activity_address_longitude,activity_address_detail,create_by,create_time,update_by,update_time,active_flag) "
			+ " values (#{activityId},#{shopId},#{latitude},#{longitude},#{address},#{creater},now(),#{updater},now(),'y')")
	@SelectKey(keyProperty = "id", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS id  " })
	void addActivityAddress(ActivityAddress activityAddress);

	/**
	 * @Title: getActivityAddressById
	 * @Description: 获取活动地点详情
	 * @Table party_activity_address_br 活动地址表
	 */
	@SelectProvider(type = ActivityAddressSqlProvider.class, method = "getActivityAddress")
	@Results(value = {
			@Result(property = "id", column = "party_activity_shop_br_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "shopId", column = "biz_mobiz_shop_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "latitude", column = "activity_address_latitude", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "longitude", column = "activity_address_longitude", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	ActivityAddress getActivityAddressById(@Param("activityId") int activityId, @Param("shopId") int shopId);

	/**
	 * @Title: getActivityShopCount
	 * @Description: 获取活动店铺
	 * @Table party_activity_address_br 活动地址表
	 */
	@Select("select biz_mobiz_shop_bd_seq from party_activity_address_br where party_activity_base_bd_seq=#{activityId} and active_flag='y'")
	List<Integer> getActivityShopCount(@Param("activityId") int activityId);

	/**
	 * @Title: getActivityAddress
	 * @Description: 批量查询活动地址
	 * @Table party_activity_address_br 活动地点表
	 */
	@Select("<script> SELECT party_activity_base_bd_seq,biz_mobiz_shop_bd_seq,activity_address_detail,activity_address_latitude,activity_address_longitude "
			+ " FROM party_activity_address_br WHERE active_flag = 'y' AND party_activity_base_bd_seq in <foreach item='item' collection='activityIds' open ='(' separator=',' close=')'>#{item}</foreach> </script>")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "shopId", column = "biz_mobiz_shop_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "longitude", column = "activity_address_longitude", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "latitude", column = "activity_address_latitude", javaType = double.class, jdbcType = JdbcType.DECIMAL) })
	List<ActivityAddressBo> getActivityAddress(@Param("activityIds") List<Integer> activityIds);

	/**
	 * @Title: updateActivityAddress
	 * @Description: 修改活动地点
	 * @Table party_activity_address_br 活动地点表
	 */
	@UpdateProvider(type = ActivityAddressSqlProvider.class, method = "update")
	void updateActivityAddress(ActivityAddress activityAddress);

	/**
	 * @Title: getShopActivityCount
	 * @Description: 修改活动地点
	 * @Table party_activity_address_br 活动地点表,party_activity_base_bd 活动基础信息表
	 */
	@Select("SELECT COUNT(1) FROM party_activity_address_br a INNER JOIN party_activity_base_bd b "
			+ " ON a.active_flag='y' AND a.biz_mobiz_shop_bd_seq=#{shopId} WHERE a.party_activity_base_bd_seq=b.party_activity_base_bd_seq "
			+ " AND CONCAT(b.activity_base_edate,' ',b.activity_base_etime)>= now() AND b.activity_base_status_itype=0 AND b.active_flag='y'")
	int getShopActivityCount(@Param("shopId") int shopId);


	/**
	 * @Title: getActivityAddressById
	 * @Description: 查询活动地址
	 * @Table party_activity_address_br 活动地点表
	 */
	@Select("SELECT party_activity_base_bd_seq,biz_mobiz_shop_bd_seq,activity_address_detail,activity_address_latitude,activity_address_longitude "
			+ " FROM party_activity_address_br WHERE active_flag = 'y' AND party_activity_base_bd_seq = #{activityId}")
	@Results(value = {
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "address", column = "activity_address_detail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "shopId", column = "biz_mobiz_shop_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "longitude", column = "activity_address_longitude", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "latitude", column = "activity_address_latitude", javaType = double.class, jdbcType = JdbcType.DECIMAL) })
	List<ActivityAddressBo> getActivityAddrById(@Param("activityId") int activityId);

	/**
	 * @Title: getActivityByShopId
	 * @Description: 获取店铺所有未结束活动Id
	 * @Table party_activity_base_bd 活动基础表 party_activity_address_br 活动地址表
	 */
	@Select("SELECT party_activity_base_bd_seq FROM party_activity_base_bd a "
			+ " WHERE (a.activity_base_edate > CURDATE() or (activity_base_edate = CURDATE() and a.activity_base_etime > CURRENT_TIME())) "
			+ " AND a.activity_base_status_itype = 0 AND a.activity_base_app_itype IN (2,4) AND a.active_flag = 'y' "
			+ " AND exists (select 1 from party_activity_address_br b where a.party_activity_base_bd_seq = b.party_activity_base_bd_seq and "
			+ " b.active_flag = 'y' AND b.biz_mobiz_shop_bd_seq =#{shopId} AND a.mobiz_shop_status_itype = #{screening})")
	List<Long> getActivityByShopId(@Param("shopId") long shopId, @Param("screening") int screening);
}
