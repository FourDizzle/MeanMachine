package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.junit.Test;

import us.superkill.meanmachine.test.util.ImageLoader;

public class FaceFinderTest {
  
  @Test
  public void findAFaceWithHaarFaceFinder() {
    Mat image = ImageLoader.getImage("group.jpeg");
    FaceFinder facefinder = new HaarFaceFinder();
    Rect[] faces = facefinder.detectFaces(image);
    drawFaces(image, faces);
    imwrite("src/test/resources/images/output/grouphaarfacefinder.jpg", image);
  }
  
  @Test
  public void findAFaceWithFaceFinderImpl() {
    String imageFilepath = 
        this.getClass().getClassLoader().getResource("images/input/obama.jpeg").getFile();
    Mat image = imread(imageFilepath);
    FaceFinder facefinder = new FaceFinderImpl();
    Rect[] faces = facefinder.detectFaces(image);
    drawFaces(image, faces);
    imwrite("src/test/resources/images/output/facefinderimpl.jpg", image);
  }
  
  private void drawFaces(Mat image, Rect[] faces) {
    for (Rect face: faces) {
      rectangle(image, face.br(), face.tl(), new Scalar(0, 255, 0, 1));
    }
  }
}
