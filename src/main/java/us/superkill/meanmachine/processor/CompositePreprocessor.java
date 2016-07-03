package us.superkill.meanmachine.processor;

import java.util.ArrayList;
import java.util.List;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;

public class CompositePreprocessor implements PhotoPreprocessor {

  private List<PhotoPreprocessor> childPreprocessors = new ArrayList<PhotoPreprocessor>();

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    if (!childPreprocessors.isEmpty()) {
      for (PhotoPreprocessor processor : childPreprocessors) {
        face = processor.process(face);
      }
    }
    return face;
  }

  public void add(PhotoPreprocessor processor) {
    this.childPreprocessors.add(processor);
  }

  public void remove(PhotoPreprocessor processor) {
    this.childPreprocessors.remove(processor);
  }

}
