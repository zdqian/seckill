package exception;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午5:22:04 
 * @Description 
 */

public class SeckillException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}
}
