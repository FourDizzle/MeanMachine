package us.superkill.meanmachine.detect;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;

public abstract class FaceFinder {
  
  public abstract Rect[] detectFaces(Mat image);
}
