package us.superkill.eyelike;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.junit.Test;

import us.superkill.meanmachine.identity.Face;

public class EyeLikeProcessorTest {
  
  @Test
  public void eyelikeProcessorTest() {
    String imageFilepath = 
        this.getClass().getClassLoader().getResource("images/input/obama.jpeg").getFile();
    Mat image = imread(imageFilepath);
    Rect faceBox = new Rect(new Point(177,52), new Point(450, 415));
    Face face = new Face();
    face.setImage(image);
    face.setFace(faceBox);
    EyeLikeProcessor proc = new EyeLikeProcessor();
    face = proc.process(face);
    
  }
}
