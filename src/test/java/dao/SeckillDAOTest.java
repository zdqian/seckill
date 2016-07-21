package dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.Seckill;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月20日 下午4:44:59 
 * @Description 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDAOTest {

	// inject the DAO implements to SeckillDAO
	@Resource
	private SeckillDao seckillDao;

	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000L, killTime);
		System.out.println(updateCount);
	}

	@Test
	public void testQueryById() {
		long seckillId = 1000;
		Seckill seck = seckillDao.queryById(seckillId);
		System.out.println(seck.getName());
	}

	@Test
	public void testQueryAll() {
		List<Seckill> secs = seckillDao.queryAll(0, 100);
		for (Seckill seckill : secs) {
			System.out.println(seckill.getName());
		}
	}

}
