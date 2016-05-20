package us.superkill.mean_machine.exceptions;

public class TrainerTargetNotFound extends Exception {

	private static final long serialVersionUID = 1191152487015751407L;
	
	public TrainerTargetNotFound() {
		super();
	}
	
	public TrainerTargetNotFound(String message) {
		super(message);
	}
	
	public TrainerTargetNotFound(Throwable cause) {
		super(cause);
	}
	
	public TrainerTargetNotFound(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TrainerTargetNotFound(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
