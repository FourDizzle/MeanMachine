package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.junit.Assert.assertNotNull;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.junit.Before;
import org.junit.Test;

import us.superkill.meanmachine.test.util.ImageLoader;

public class EstimatedPupilDetectorTest {
  
  private PupilDetector d;
  
  @Before
  public void createSingleEyeDetector() {
    d = new EstimatedPupilDetector();
  }
  
  @Test
  public void detectEyeInRegion() {
    Mat image = ImageLoader.getImage("obama.jpeg");
    
    Rect eyeArea = new Rect(new Point(212, 143), new Point(308, 252));
    Rect rightEyeArea = new Rect(new Point(319, 143), new Point(415, 252));
    
    Point leftPupil = d.detect(image, eyeArea);
    Point rightPupil = d.detect(image, rightEyeArea);
    
    circle(image, leftPupil, 3, new Scalar(0, 0, 255, 1));
    circle(image, rightPupil, 3, new Scalar(0, 0, 255, 1));
    
    imwrite("src/test/resources/images/output/obamapupil.jpg", image);
    assertNotNull(leftPupil);
    assertNotNull(rightPupil);
  }
}
