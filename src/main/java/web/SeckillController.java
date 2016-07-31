package web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillResult;
import entity.Seckill;
import enums.SeckillStateEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import service.SeckillService;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月22日 下午10:41:50
 * @Description
 */

//@RequestMapping("seckill") // url:/模块/资源/{id}/细分
@Controller
public class SeckillController {
//	@Autowired
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		System.out.println(seckill);
		model.addAttribute(seckill);
		return "detail";
	}

	// ajax
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long phone) {
		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}

		try {
			SeckillExecution execute = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true, execute);
		} catch (RepeatKillException e) {
			SeckillExecution execute = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execute);
		} catch (SeckillCloseException e) {
			SeckillExecution execute = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true, execute);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execute = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execute);
		}
	}

	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}
