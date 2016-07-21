package dao;

import org.apache.ibatis.annotations.Param;

import entity.SuccessKilled;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月19日 下午6:47:54
 * @Description
 */

public interface SuccessKilledDao {
	/**
	 * 插入购买明细，可过滤重复（联合主键）
	 * 
	 * @param secKillId
	 * @param userPhone
	 * @return 插入行数
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);

	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);

}
