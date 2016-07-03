package us.superkill.meanmachine.detect;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

public abstract class PupilDetector {
  public abstract Point detect(Mat image, Rect eyeArea);
}
