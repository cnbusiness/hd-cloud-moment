package com.hd.cloud.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.hd.cloud.bo.ActivityLottery;
import com.hd.cloud.dao.sql.ActivityLotterySqlProvider;

/**
 * 
 * @ClassName: ActivityLotteryMapper
 * @Description: 活动抽奖
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午3:01:28
 *
 */
@Mapper
public interface ActivityLotteryMapper {

	/**
	 * @Title: getActivityLotteryById
	 * @Description: 获取活动中奖用户列表
	 * @Table party_activity_lottery_bt 活动抽奖表
	 */
	@Select("<script> select * from party_activity_lottery_bt where party_activity_base_bd_seq=#{activityId} "
			+ "and active_flag='y' <if test='lotteryType ==1'> and activity_lottery_hit_itype = 2 </if> ORDER BY create_time DESC limit #{pageIndex},#{pageSize} </script>")
	@Results(value = {
			@Result(property = "id", column = "party_activity_lottery_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "activityId", column = "party_activity_base_bd_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "userId", column = "user_user_base_sb_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "prizeType", column = "activity_lottery_prize_itype", javaType = short.class, jdbcType = JdbcType.TINYINT),
			@Result(property = "prizeNumber", column = "activity_lottery_prize_cnt", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "prizeDesc", column = "activity_lottery_prize_desc", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "lottrtyType", column = "activity_lottery_hit_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "creater", column = "create_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updater", column = "update_by", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "updateTime", column = "update_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "activeFlag", column = "active_flag", javaType = char.class, jdbcType = JdbcType.CHAR) })
	List<ActivityLottery> getActivityLotteryById(@Param("activityId") int activityId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize, @Param("lotteryType") int lotteryType);

	/**
	 * @Title: getActivityLotteryCount
	 * @Description: 获取活动中奖用户数量
	 * @Table party_activity_lottery_bt 活动抽奖表
	 */
	@Select("<script> select count(1) from party_activity_lottery_bt where party_activity_base_bd_seq=#{activityId} <if test='lotteryType ==1'> and activity_lottery_hit_itype = 2 </if> and active_flag='y' </script>")
	int getActivityLotteryCount(@Param("activityId") int activityId, @Param("lotteryType") int lotteryType);

	/**
	 * @Title: getActivityLotteryUser
	 * @Description: 随机获取活动摇一摇中奖的用户
	 * @Table party_activity_lottery_bt 活动抽奖表
	 */
	@SelectProvider(type = ActivityLotterySqlProvider.class, method = "getUser")
	List<Long> getActivityLotteryUser(@Param("activityId") int activityId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	/**
	 * @Title: addLotteryUser
	 * @Description: 记录中奖用户
	 * @Table party_activity_lottery_bt 活动抽奖表
	 */
	@Insert("INSERT INTO party_activity_lottery_bt(party_activity_base_bd_seq,user_user_base_sb_seq,activity_lottery_prize_itype,activity_lottery_prize_cnt,"
			+ " activity_lottery_prize_desc,activity_lottery_hit_itype,create_by,create_time,update_by,update_time,active_flag) "
			+ " VALUES(#{activityId},#{userId},#{prizeType},#{prizeNumber},#{prizeDesc},#{lottrtyType},#{creater},NOW(),#{updater},NOW(),'y')")
	void addLotteryUser(ActivityLottery activityLottery);

	/**
	 * @Title: checkActivityLotteryUser
	 * @Description: 检测该用户是否已中奖该活动
	 * @Table party_activity_lottery_bt 活动抽奖表
	 */
	@Select("select COUNT(1) from party_activity_lottery_bt WHERE user_user_base_sb_seq=#{userId} and party_activity_base_bd_seq=#{activityId} and active_flag='y'")
	int checkActivityLotteryUser(@Param("activityId") int activityId, @Param("userId") long userId);
}
