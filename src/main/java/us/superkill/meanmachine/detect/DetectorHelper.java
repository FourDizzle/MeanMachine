package us.superkill.meanmachine.detect;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;

public class DetectorHelper {
  public static Rect[] filterValidHaarOutput(Mat image, RectVector detectResult) {
    List<Rect> eyes = new ArrayList<Rect>();
    if (detectResult.size() != 0) {
      for (int i = 0; i < detectResult.sizeof(); i++) {
        int eyeWidth = detectResult.get(i).width();
        if (eyeWidth < image.cols() && eyeWidth > 0) {
          eyes.add(detectResult.get(i));
        }
      }
    }
    return eyes.toArray(new Rect[eyes.size()]);
  }
}
