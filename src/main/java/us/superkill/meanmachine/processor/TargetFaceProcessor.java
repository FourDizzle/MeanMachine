package us.superkill.meanmachine.processor;

import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.detect.FaceFinder;
import us.superkill.meanmachine.detect.TargetFaceDetector;
import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;

public class TargetFaceProcessor implements PhotoPreprocessor {

  private FaceFinder fdetector;
  private TargetFaceDetector tdetector;

  public TargetFaceProcessor(FaceFinder fdetector, TargetFaceDetector tdetector) {
    this.fdetector = fdetector;
    this.tdetector = tdetector;
  }

  @Override
  public Face process(Face face) throws PreprocessFailedException {
      Rect[] faces = fdetector.detectFaces(face.getImage());
      face.setFace(tdetector.detectTargetFace(face, faces));
    return face;
  }

}
