package us.superkill.meanmachine.detect;

import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.exceptions.DetectorTargetNotFound;
import us.superkill.meanmachine.identity.Face;

public class SimpleTaggedFaceDetector extends TargetFaceDetector {

  @Override
  public Rect detectTargetFace(Face photo, Rect[] faces) {
    Point taggedPoint = new Point(photo.getFaceLocX(), photo.getFaceLocY());
    Rect taggedFace = null;
    for (Rect face : faces) {
      if (face != null && face.contains(taggedPoint))
        taggedFace = face;
    }
    return taggedFace;
  }

}
