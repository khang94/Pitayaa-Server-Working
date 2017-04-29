package pitayaa.nail.msg.business.exception;

public class CreateClientDBException extends Exception {
	private static final long serialVersionUID = -6127762932224120122L;

	public CreateClientDBException() {
	}

	public CreateClientDBException(String message) {
		super(message);
	}

	public CreateClientDBException(Throwable cause) {
		super(cause);
	}

	public CreateClientDBException(String message, Throwable cause) {
		super(message, cause);
	}
}