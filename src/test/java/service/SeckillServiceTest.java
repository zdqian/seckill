package service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午11:27:50 
 * @Description 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService ss;
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = ss.getSeckillList();
		logger.info("list={}",list);//{}占位符
	}

	@Test
	public void testGetById() {
		long id = 1000;
		Seckill sk = ss.getById(id);
		logger.info("seckill={}", sk);
	}


	@Test
	public void testSeckillLogic() {
		long id = 1000;
		Exposer exposer = ss.exportSeckillUrl(id);
		if (exposer.isExposed()) {
			logger.info("exposer={}", exposer);
			
			long phone = 1238920348;
			try {
				SeckillExecution se = ss.executeSeckill(id, phone, exposer.getMd5());
				logger.info("SeckillExecution result = {}", se);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		} else {
			// 秒杀未开启
			logger.warn("exposer={}", exposer);
		}
	}
	
	@Test
	public void testExportSeckillUrl() {
		// exposer=Exposer [exposed=true, md5=0002470b1d340b4b5258f76f86f585e9', seckillId=1000, now=0, start=0, end=0]
		long id = 1000;
		Exposer e = ss.exportSeckillUrl(id);
		logger.info("exposer={}", e);
	}
	
	@Test
	public void testExecuteSeckill() {
		long id = 1000;
		long phone = 1238920348;
		String md5 = "0002470b1d340b4b5258f76f86f585e9";
		try {
			SeckillExecution se = ss.executeSeckill(id, phone, md5);
			logger.info("SeckillExecution result = {}", se);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		}
	}
//	@Test
//	public void testExecuteSeckill() {
//		long id = 1000;
//		long phone = 1238920348;
//		String md5 = "0002470b1d340b4b5258f76f86f585e9";
//		SeckillExecution se = ss.executeSeckill(id, phone, md5);
//		logger.info("SeckillExecution result = {}", se);
//	}

}
