package enums;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午6:03:37
 * @Description 使用枚举表达常量数据(把数据字典放到枚举当中)
 */

public enum SeckillStateEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改");
	
	private int state;
	private String sateInfo;

	SeckillStateEnum(int state, String sateInfo) {
		this.state = state;
		this.sateInfo = sateInfo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSateInfo() {
		return sateInfo;
	}

	public void setSateInfo(String sateInfo) {
		this.sateInfo = sateInfo;
	}

	public static SeckillStateEnum stateOf(int index) {
		for (SeckillStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
