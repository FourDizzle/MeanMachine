package us.superkill.meanmachine.imagetools;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Size;

public abstract class Transformer {

  public abstract Mat rescale(Mat image, double rescaleRatio);

  public abstract Mat rotate(Mat image, double degreesToRotate, Point pivot);

  public abstract Mat translate(Mat image, Point offset, Size size);
}
