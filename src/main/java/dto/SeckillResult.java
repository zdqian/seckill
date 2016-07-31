package dto;

/**
 * @version
 * @author 张大千 zdqian@126.com
 * @date 2016年7月22日 下午10:57:30
 * @Description 封装json结果,用范型可以放所有ajax请求返回的类型
 */

public class SeckillResult<T> {
	// 是否执行成功，不是是否秒杀成功
	private boolean success;
	private T data;
	private String error;

	public SeckillResult(boolean success, T data) {
		this.data = data;
		this.success = success;
	}

	public SeckillResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "SeckillResult [success=" + success + ", data=" + data + ", error=" + error + "]";
	}

}
