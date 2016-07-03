package us.superkill.eyelike;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.junit.Assert.assertEquals;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.junit.Test;

public class EyeFinderTest {
  
  @Test
  public void EyeRegionsMatchConfigValues() {
    EyeLikeConfig config = EyeLikeConfig.getInstance();
    Rect expectedLeftEye = new Rect(new Point(212, 143), new Point(308, 252));
    Rect expectedRightEye = new Rect(new Point(319, 143), new Point(415, 252));
    String imageFilepath = 
        this.getClass().getClassLoader().getResource("images/input/obama.jpeg").getFile();
   
    Mat image = imread(imageFilepath);
    Rect face = new Rect(new Point(177,52), new Point(450, 415));
    Rect[] eyeRegions = EyeFinder.findEyes(image, face);
    Rect leftEye = eyeRegions[0];
    Rect rightEye = eyeRegions[1];
//    Point ltl = leftEye.tl();
//    Point lbr = leftEye.br();
//    Point rtl = rightEye.tl();
//    Point rbr = rightEye.br();
//    int ltlx = ltl.x();
//    int ltly = ltl.y();
//    int lbrx = lbr.x();
//    int lbry = lbr.y();
//    int rtlx = rtl.x();
//    int rtly = rtl.y();
//    int rbrx = rbr.x();
//    int rbry = rbr.y();
    imwrite("src/test/resources/images/output/eyefinder.jpg", image);
    assertEquals(expectedLeftEye.tl().x(), leftEye.tl().x());
    assertEquals(expectedLeftEye.tl().y(), leftEye.tl().y());
    assertEquals(expectedLeftEye.br().x(), leftEye.br().x());
    assertEquals(expectedLeftEye.br().y(), leftEye.br().y());
    assertEquals(expectedRightEye.tl().x(), rightEye.tl().x());
    assertEquals(expectedRightEye.tl().y(), rightEye.tl().y());
    assertEquals(expectedRightEye.br().x(), rightEye.br().x());
    assertEquals(expectedRightEye.br().y(), rightEye.br().y());
  }
}
