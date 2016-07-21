package dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.SuccessKilled;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 上午11:14:24 
 * @Description 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successKilledDao;
	@Test
	public void testInsertSuccessKilled() {
		/**
		 * 1: count:1 成功
		 * 2: count:0 没成功
		 * 联合主键保证的同一用户不能重复秒杀
		 */
		long id = 1001L;
		long phone = 135123447878L;
		int count = successKilledDao.insertSuccessKilled(id, phone);
		System.out.println("count:" + count);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1001L;
		long phone = 135123447878L;
		SuccessKilled sk = successKilledDao.queryByIdWithSeckill(id, phone);
		System.out.println(sk);
	}

}
