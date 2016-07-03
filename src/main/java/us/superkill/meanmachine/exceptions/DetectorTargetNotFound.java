package us.superkill.meanmachine.exceptions;

public class DetectorTargetNotFound extends Exception {

  private static final long serialVersionUID = 1191152487015751407L;

  public DetectorTargetNotFound() {
    super();
  }

  public DetectorTargetNotFound(String message) {
    super(message);
  }

  public DetectorTargetNotFound(Throwable cause) {
    super(cause);
  }

  public DetectorTargetNotFound(String message, Throwable cause) {
    super(message, cause);
  }

  public DetectorTargetNotFound(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
