package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import us.superkill.meanmachine.geometry.PointHelper;
import us.superkill.meanmachine.geometry.RectangleHelper;

public class EstimatedPupilDetector extends PupilDetector {
  
  private static final Logger log = LogManager.getLogger(EstimatedPupilDetector.class);

  private static final CascadeClassifier eyeCascade = new CascadeClassifier(
      FaceFinderImpl.class.getResource("/haarcascades/haarcascade_eye.xml").getPath());
  
  @Override
  public Point detect(Mat image, Rect eyeArea) {
    log.debug("Detecting pupil.");

    RectVector detectResult = new RectVector();
    Mat imageGray = new Mat(image, eyeArea);
    cvtColor(imageGray, imageGray, COLOR_BGR2GRAY);
    Size min = new Size(imageGray.cols()/3, imageGray.cols()/3);
    Size max = new Size(imageGray.cols(), imageGray.cols());
    eyeCascade.detectMultiScale(imageGray, detectResult, 1.4, 5, 0, min, max);

    Rect[] eyes = DetectorHelper.filterValidHaarOutput(imageGray, detectResult);
    Point pupil = RectangleHelper.getCenterPoint(eyes[0]);
    pupil = PointHelper.rescalePoint(pupil, eyeArea.tl());
    
    imageGray.close();
    return pupil;
  }

}
