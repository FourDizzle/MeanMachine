package us.superkill.meanmachine.test.util;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import org.bytedeco.javacpp.opencv_core.Mat;

public class ImageLoader {
  public static Mat getImage(String imageName) {
    String imageFilepath = 
        ImageLoader.class.getClassLoader().getResource("images/input/" + imageName).getFile();
    Mat image = imread(imageFilepath);
    return image;
  }
}
