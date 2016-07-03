package us.superkill.meanmachine.eyealign;

import static org.bytedeco.javacpp.opencv_imgproc.resize;

import org.bytedeco.javacpp.opencv_core.Size;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class ResizeProcessor implements PhotoPreprocessor {

  private Size newSize;

  public ResizeProcessor(int width, int height) {
    this.newSize = new Size(width, height);
  }

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    resize(face.getImage(), face.getImage(), newSize);
    return face;
  }

}
