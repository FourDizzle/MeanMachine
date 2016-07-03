package us.superkill.meanmachine.eyealign;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.geometry.PointHelper;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.imagetools.Transformer;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class EyeLevelProcessor implements PhotoPreprocessor {

  private Transformer transformer;

  public EyeLevelProcessor(Transformer transformer) {
    this.transformer = transformer;
  }

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    Point rEye = face.getRightEyeCenter();
    Point lEye = face.getLeftEyeCenter();
    Point eyeMidPoint = PointHelper.getMidpoint(rEye, lEye);
    double eyeAngle = PointHelper.getAngleInDegrees(rEye, lEye);
    Mat image = transformer.rotate(face.getImage(), eyeAngle, eyeMidPoint);
    face.setImage(image);
    return face;
  }

}
