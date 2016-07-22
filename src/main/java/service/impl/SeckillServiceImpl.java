package service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import dao.SeckillDao;
import dao.SuccessKilledDao;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import entity.SuccessKilled;
import enums.SeckillStateEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import service.SeckillService;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午5:29:59
 * @Description
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 用注解方式注入Service依赖
	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	/**
	 * md5盐值字符串，用于混淆MD5
	 */
	private final String slat = "cmq3490jknrghpawn08y578969q34502-83M*W*M#*M&$";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date currTime = new Date();
		if (currTime.getTime() < startTime.getTime() || currTime.getTime() > endTime.getTime()) {
//			System.out.println("startTime: " + startTime + ", endTime: " + endTime + ", currTime: " + currTime );
			return new Exposer(false, seckillId, currTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		String md5 = getMD5(seckillId);
//		System.out.println("================="+md5);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 使用注解控制事务控制方法的优点：
	 * 1 开发团队达成一致约定，明确标注事务方法的编程风格；
	 * 2 保证事务方法的执行时间尽可能短，不要穿插其他网络操作、缓存操作：RPC／HTTP 或剥离到事务方法外部；
	 * 3 不是所有的方法都需要事务，如只有一条修改操作、只读操作不需要事务控制。
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}

		// 执行秒杀逻辑：减库存，记录购买
		Date currTime = new Date();
		try {
			int updateCount = seckillDao.reduceNumber(seckillId, currTime);
			if (updateCount <= 0) {
				throw new SeckillCloseException("seckill is closed");
			} else {
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount <= 0) {
					throw new RepeatKillException("seckill repeated");
				} else {
					SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, sk);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//  所有编译期异常，转化为运行期异常，sping帮我们做回滚
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
