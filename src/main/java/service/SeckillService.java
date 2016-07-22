package service;

import java.util.List;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午4:24:29 
 * @Description 业务接口：站在“使用者”的角度设计接口：
 * 1.方法定义的粒度
 * 		要明确，如秒杀类型要有个秒杀接口去执行秒杀，
 * 		而不应该关注怎么减库存，怎么添加用户购买行为等。
 * 2.参数列表
 * 		直接简练
 * 3.返回类型return
 * 		类型要友好／异常
 */

public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 提供秒杀入口，秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀开始时间
	 * @param seckillId
	 * @return 返回跟业务相关的实体都不合适，需要的是url或提示信息，所以需要封装一个DTO提供这些信息
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return 秒杀执行后的结果
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone, String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
}
