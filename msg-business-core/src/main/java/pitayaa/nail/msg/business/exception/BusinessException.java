package pitayaa.nail.msg.business.exception;

public class BusinessException extends Exception {
	private static final long serialVersionUID = -6127762932224120122L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
		cause.printStackTrace();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}