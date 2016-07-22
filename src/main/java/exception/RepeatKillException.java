package exception;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午5:16:06 
 * @Description 
 */

public class RepeatKillException extends SeckillException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}

}
