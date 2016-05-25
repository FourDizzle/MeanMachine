package us.superkill.mean_machine.exceptions;

public class FormatterTargetNotFound extends Exception {

	private static final long serialVersionUID = 1191152487015751407L;
	
	public FormatterTargetNotFound() {
		super();
	}
	
	public FormatterTargetNotFound(String message) {
		super(message);
	}
	
	public FormatterTargetNotFound(Throwable cause) {
		super(cause);
	}
	
	public FormatterTargetNotFound(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FormatterTargetNotFound(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
