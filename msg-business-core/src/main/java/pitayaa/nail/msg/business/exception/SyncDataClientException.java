package pitayaa.nail.msg.business.exception;

public class SyncDataClientException extends Exception {
	private static final long serialVersionUID = -6127762932224120122L;

	public SyncDataClientException() {
	}

	public SyncDataClientException(String message) {
		super(message);
	}

	public SyncDataClientException(Throwable cause) {
		super(cause);
	}

	public SyncDataClientException(String message, Throwable cause) {
		super(message, cause);
	}
}