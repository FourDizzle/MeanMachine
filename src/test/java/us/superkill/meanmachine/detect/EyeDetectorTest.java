package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.junit.Assert.assertNotNull;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.junit.Test;

import us.superkill.meanmachine.test.util.ImageLoader;

public class EyeDetectorTest {

  @Test
  public void findAnEye() {
    Mat image = ImageLoader.getImage("obama.jpeg");
    EyeDetector finder = new EyeFinderImpl();
    Rect[] possibleEyes = finder.findEye(image);
    drawEyes(image, possibleEyes);
    imwrite("src/test/resources/images/output/eyefinderimpl.jpg", image);
    assertNotNull(possibleEyes);
  }
  
  private void drawEyes(Mat image, Rect[] faces) {
    for (Rect face: faces) {
      rectangle(image, face.br(), face.tl(), new Scalar(0, 255, 0, 1));
    }
  }
}
