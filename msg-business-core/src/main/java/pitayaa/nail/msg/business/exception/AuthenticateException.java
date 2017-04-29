package pitayaa.nail.msg.business.exception;

public class AuthenticateException extends Exception {
	private static final long serialVersionUID = -6061510811274815193L;

	public AuthenticateException() {
	}

	public AuthenticateException(String arg0) {
		super(arg0);
	}

	public AuthenticateException(Throwable arg0) {
		super(arg0);
	}

	public AuthenticateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}