package us.superkill.eyelike;

import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;

public class EyeFinder {

  private static final Logger log = LogManager.getLogger(EyeFinder.class);
  private static final EyeLikeConfig config = EyeLikeConfig.getInstance();

  public static Rect[] findEyes(Mat image, Rect face) {
    log.debug("Looking for eyes.");

    int eyeRegionWidth = calculateEyeRegionWidth(face);
    int eyeRegionHeight = calcuateEyeRegionHeight(face);
    int eyeRegionTop = calculateEyeRegionTop(face);
    Rect leftEyeRegion = new Rect(calculateEyeRegionLeftSideOffset(face),
                                  eyeRegionTop, 
                                  eyeRegionWidth, 
                                  eyeRegionHeight);
    Rect rightEyeRegion = new Rect(calculateEyeRegionRightSideOffset(face, eyeRegionWidth),
                                   eyeRegionTop, 
                                   eyeRegionWidth, 
                                   eyeRegionHeight);
    
    Rect[] eyeRegions = rescaleEyes(new Rect[] {leftEyeRegion, rightEyeRegion}, face);
    
    if (config.isDrawEyeRegionEnabled)
      drawEyeRegions(image, eyeRegions);
    
    log.debug("returning eye areas");
    return eyeRegions;
  }

  private static Rect[] rescaleEyes(Rect[] eyeRegions, Rect face) {
    Rect leftEye = eyeRegions[0];
    Rect leftRect = new Rect(new Point(leftEye.tl().x() + face.tl().x(), 
                                       leftEye.tl().y() + face.tl().y()),
                             new Point(leftEye.br().x() + face.tl().x(), 
                                       leftEye.br().y() + face.tl().y()));
    Rect rightEye = eyeRegions[1];
    Rect rightRect = new Rect(new Point(rightEye.tl().x() + face.tl().x(), 
                                       rightEye.tl().y() + face.tl().y()),
                             new Point(rightEye.br().x() + face.tl().x(), 
                                       rightEye.br().y() + face.tl().y()));
    return new Rect[] {leftRect, rightRect};
  }

  private static void drawEyeRegions(Mat image, Rect[] eyeRegions) {
    rectangle(image, eyeRegions[0].br(), eyeRegions[0].tl(), new Scalar(0, 255, 0, 1));
    rectangle(image, eyeRegions[1].br(), eyeRegions[1].tl(), new Scalar(0, 255, 0, 1));
  }

  private static int calculateEyeRegionLeftSideOffset(Rect face) {
    return (int) Math.round(face.width() * ((double) config.kEyePercentSide / 100));
  }
  
  private static int calculateEyeRegionRightSideOffset(Rect face, int eyeRegionWidth) {
    return (int) Math.round(face.width() - eyeRegionWidth - calculateEyeRegionLeftSideOffset(face));
  }

  private static int calculateEyeRegionTop(Rect face) {
    return (int) Math.round(face.height() * ((double) config.kEyePercentTop / 100));
  }

  private static int calcuateEyeRegionHeight(Rect face) {
    return (int) Math.round(face.height() * ((double) config.kEyePercentHeight / 100));
  }

  private static int calculateEyeRegionWidth(Rect face) {
    return (int) Math.round(face.width() * ((double) config.kEyePercentWidth / 100));
  }
}
