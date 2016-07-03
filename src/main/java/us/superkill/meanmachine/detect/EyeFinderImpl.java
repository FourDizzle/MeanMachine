package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class EyeFinderImpl extends EyeDetector{
  
  private static final Logger log = LogManager.getLogger(EyeFinderImpl.class);

  private static final CascadeClassifier eyeCascade = new CascadeClassifier(
      EyeFinderImpl.class.getResource("/haarcascades/haarcascade_eye.xml").getPath());
  
  @Override
  public Rect[] findEye(Mat image) {
    log.debug("Looking for eyes.");

    RectVector detectResult = new RectVector();
    
    Mat imageGray = new Mat();
    cvtColor(image, imageGray, COLOR_BGR2GRAY);
    Size min = new Size(image.cols()/15, image.cols()/15);
    Size max = new Size(image.cols()/5, image.cols()/5);
    eyeCascade.detectMultiScale(imageGray, detectResult, 1.4, 5, 0, null, null);

    return DetectorHelper.filterValidHaarOutput(image, detectResult);
  }

}
