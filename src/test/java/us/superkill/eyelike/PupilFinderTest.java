package us.superkill.eyelike;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.Assert.assertEquals;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.junit.Test;

public class PupilFinderTest {
  
  @Test
  public void findPupilInEye() {
    String imageFilepath = 
        this.getClass().getClassLoader().getResource("images/input/obama.jpeg").getFile();
    Mat image = imread(imageFilepath);
    Rect eye = new Rect(new Point(212, 143), new Point(308, 252));
    Point eyeCenter = PupilFinder.findEyeCenter(image, eye);
    int x = eyeCenter.x();
    int y = eyeCenter.y();
    assertEquals(273, x);
    assertEquals(199, y);
  }
}
