package us.superkill.meanmachine.processor;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;

public interface PhotoPreprocessor {

  public abstract Face process(Face face) throws PreprocessFailedException;
}
