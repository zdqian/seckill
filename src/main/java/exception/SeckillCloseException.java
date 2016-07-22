package exception;

/**
 * @version 
 * @author 张大千 zdqian@126.com
 * @date 2016年7月21日 下午5:20:59 
 * @Description 
 */

public class SeckillCloseException extends SeckillException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillCloseException(String message) {
		super(message);
	}

}
