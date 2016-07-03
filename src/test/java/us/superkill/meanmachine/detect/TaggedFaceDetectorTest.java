package us.superkill.meanmachine.detect;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.Assert.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.junit.Test;

import us.superkill.meanmachine.exceptions.DetectorTargetNotFound;
import us.superkill.meanmachine.identity.Face;

public class TaggedFaceDetectorTest {
  
  @Test
  public void findTaggedFaceWithSimpleTaggedFaceDetector() {
    String imageFilepath = 
        this.getClass().getClassLoader().getResource("images/input/obama.jpeg").getFile();
    Mat image = imread(imageFilepath);
    Face face = new Face();
    face.setImage(image);
    face.setFaceLocX(337);
    face.setFaceLocY(226);
    FaceFinder facefinder = new HaarFaceFinder();
    Rect[] faces = facefinder.detectFaces(image);
    TargetFaceDetector t = new SimpleTaggedFaceDetector();
    Rect taggedFace = null;
    taggedFace = t.detectTargetFace(face, faces);
    assertNotNull(taggedFace);
  }
}
