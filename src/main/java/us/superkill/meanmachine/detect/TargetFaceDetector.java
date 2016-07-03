package us.superkill.meanmachine.detect;

import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.exceptions.DetectorTargetNotFound;
import us.superkill.meanmachine.identity.Face;

public abstract class TargetFaceDetector {

  public abstract Rect detectTargetFace(Face photo, Rect[] faces);
}
