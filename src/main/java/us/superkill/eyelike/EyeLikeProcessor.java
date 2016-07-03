package us.superkill.eyelike;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.detect.EyeDetector;
import us.superkill.meanmachine.detect.EyeFinderImpl;
import us.superkill.meanmachine.geometry.PointHelper;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class EyeLikeProcessor implements PhotoPreprocessor {
  
  private static EyeDetector finder = new EyeFinderImpl();
  
  @Override
  public Face process(Face face) {
    Rect[] eyes = EyeFinder.findEyes(face.getImage(), face.getFace());
//    Mat leftEye = new Mat(face.getImage(), eyes[0]);
//    Rect[] newLeftEye = finder.findEye(leftEye);
//    Mat rightEye = new Mat(face.getImage(), eyes[1]);
//    Rect[] newRightEye = finder.findEye(leftEye);
    Point leftEyeCenter = PupilFinder.findEyeCenter(face.getImage(), eyes[0]);
    Point rightEyeCenter = PupilFinder.findEyeCenter(face.getImage(), eyes[1]);
    face.setLeftEyeCenter(leftEyeCenter);
    face.setRightEyeCenter(rightEyeCenter);
    return face;
  }
  
  private Rect rescaleEye(Rect eye, Rect eyeRegion) {
    Point offset = eyeRegion.tl();
    return new Rect(PointHelper.rescalePoint(eye.tl(), offset), 
                    PointHelper.rescalePoint(eye.br(), offset));
  }

}
