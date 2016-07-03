package us.superkill.meanmachine.processor;

import us.superkill.meanmachine.detect.FaceLandmarkDetector;
import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;

public class FaceLandmarkProcessor implements PhotoPreprocessor {

  private FaceLandmarkDetector detector;

  public FaceLandmarkProcessor(FaceLandmarkDetector detector) {
    this.detector = detector;
  }

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    face = detector.detect(face);
    return face;
  }

}
