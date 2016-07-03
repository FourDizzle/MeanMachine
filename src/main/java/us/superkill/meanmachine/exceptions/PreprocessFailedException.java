package us.superkill.meanmachine.exceptions;

public class PreprocessFailedException extends Exception {

  private static final long serialVersionUID = -8544320604578717824L;

  public PreprocessFailedException() {
    super();
  }

  public PreprocessFailedException(String message) {
    super(message);
  }

  public PreprocessFailedException(Throwable cause) {
    super(cause);
  }

  public PreprocessFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PreprocessFailedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
