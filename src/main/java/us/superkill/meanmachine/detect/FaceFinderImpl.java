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

public class FaceFinderImpl extends FaceFinder {

  private static final Logger log = LogManager.getLogger(FaceFinderImpl.class);

  private static final CascadeClassifier faceCascade = new CascadeClassifier(
      FaceFinderImpl.class.getResource("/haarcascades/haarcascade_frontalface_alt.xml").getPath());

  public Rect[] detectFaces(Mat image) {
    log.debug("Looking for faces.");

    RectVector detectResult = new RectVector();
    
    Mat imageGray = new Mat();
    cvtColor(image, imageGray, COLOR_BGR2GRAY);
    Size min = new Size(image.cols()/4, image.cols()/4);
    Size max = new Size(image.cols(), image.cols());
    faceCascade.detectMultiScale(imageGray, detectResult, 1.4, 5, 0, min, max);
    
    return DetectorHelper.filterValidHaarOutput(image, detectResult);
  }
}
