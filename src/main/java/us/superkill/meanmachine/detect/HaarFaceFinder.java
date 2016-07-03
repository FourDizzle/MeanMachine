package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;
import static org.bytedeco.javacpp.opencv_objdetect.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class HaarFaceFinder extends FaceFinder {

  private static final Logger log = LogManager.getLogger(HaarFaceFinder.class);
  
  private static final CascadeClassifier faceCascade = new CascadeClassifier(
      HaarFaceFinder.class.getResource("/haarcascades/haarcascade_frontalface_alt.xml").getPath());

  public Rect[] detectFaces(Mat image) {

    RectVector faces = new RectVector();
    Mat gray = new Mat();
    cvtColor(image, gray, COLOR_BGR2GRAY);
    equalizeHist(gray, gray);
    
    int absoluteFaceSize = 0;
    if (absoluteFaceSize == 0) {
      int dheight = gray.rows();
      if (Math.round(dheight * 0.75f) > 0) {
        absoluteFaceSize = Math.round(dheight * 0.75f);
      }
    }
    
    Size maxFace = new Size(absoluteFaceSize, absoluteFaceSize);
    Size minFace = new Size(30, 30);

    faceCascade.detectMultiScale(gray, faces, 1.4, 3, 0, minFace, maxFace);
    Rect[] faceArray = DetectorHelper.filterValidHaarOutput(image, faces);
    
    log.debug(faceArray.length + " potential matches.");

    return faceArray;
  }
}
