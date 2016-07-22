package dto;

import entity.SuccessKilled;
import enums.SeckillStateEnum;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午4:54:48
 * @Description 秒杀执行后的结果
 */

public class SeckillExecution {
	private long seckillId;

	// 秒杀执行结果状态
	private int state;
	// 状态表示
	private String stateInfo;
	// 秒杀成功对象
	private SuccessKilled successKilled;

	/**
	 * 秒杀成功
	 * 
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 * @param successKilled
	 */
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getSateInfo();
		this.successKilled = successKilled;
	}

	/**
	 * 秒杀失败
	 * 
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 */
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getSateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

}
