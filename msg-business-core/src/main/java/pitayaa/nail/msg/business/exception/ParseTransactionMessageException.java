package pitayaa.nail.msg.business.exception;

public class ParseTransactionMessageException extends Exception {
	private static final long serialVersionUID = -6127762932224120122L;

	public ParseTransactionMessageException() {
	}

	public ParseTransactionMessageException(String message) {
		super(message);
	}

	public ParseTransactionMessageException(Throwable cause) {
		super(cause);
	}

	public ParseTransactionMessageException(String message, Throwable cause) {
		super(message, cause);
	}
}