package us.superkill.meanmachine.eyealign;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.geometry.PointHelper;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class CropToEyePositionProcessor implements PhotoPreprocessor {

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    Point rEye = face.getRightEyeCenter();
    Point lEye = face.getLeftEyeCenter();
    Point eyeMidPoint = PointHelper.getMidpoint(rEye, lEye);
    int eyeDistance = (int) Math.round(PointHelper.distBetweenPoints(rEye, lEye));
    Point tL = calculateTopLeft(eyeMidPoint, eyeDistance);
    Point bR = new Point(tL.x() + eyeDistance * 2, tL.y() + eyeDistance * 2);
    Rect cropBox = new Rect(tL, bR);
    face.setImage(new Mat(face.getImage(), cropBox));
    return face;
  }

  private Point calculateTopLeft(Point eyeMidPoint, int eyeDistance) {
    return new Point(eyeMidPoint.x() - eyeDistance, eyeMidPoint.y() - eyeDistance / 2);
  }

}
