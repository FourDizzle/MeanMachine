package us.superkill.meanmachine.imagetools;

import static org.bytedeco.javacpp.opencv_core.copyMakeBorder;
import static org.bytedeco.javacpp.opencv_imgproc.getRotationMatrix2D;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;

public class GeneralTransformer extends Transformer {
  private static final Logger log = LogManager.getLogger(GeneralTransformer.class);

  @Override
  public Mat rescale(Mat image, double rescaleRatio) {
    log.debug("Resizing image at ratio: " + rescaleRatio);
    Mat output = Mat.zeros(image.rows(), image.cols(), image.type()).asMat();
    int newWidth = (int) Math.round(image.cols() * rescaleRatio);
    int newHeight = (int) Math.round(image.rows() * rescaleRatio);
    Size scaleSize = new Size(newWidth, newHeight);
    resize(image, output, scaleSize);

    return output;
  }

  @Override
  public Mat rotate(Mat image, double degreesToRotate, Point pivot) {
    log.debug("Rotating image by " + degreesToRotate + " degress");
    Mat output = Mat.zeros(image.rows(), image.cols(), image.type()).asMat();
    Point2f pivot2f = new Point2f(pivot.x(), pivot.y());
    Mat rotMatrix = getRotationMatrix2D(pivot2f, degreesToRotate, 1.0);
    warpAffine(image, output, rotMatrix, output.size());

    return output;
  }

  @Override
  public Mat translate(Mat image, Point offset, Size size) {
    Mat translatedImage = null;
    Rect cropBox = null;
    Size imageSize = new Size(image.cols(), image.rows());

    if (offset.x() > 0 || offset.y() > 0 || offset.x() + imageSize.width() < size.width()
        || offset.y() + imageSize.height() < size.height()) {

      int offTop = offset.y();
      int offBottom = -(offset.y() + imageSize.height() - size.height());
      int offLeft = offset.x();
      int offRight = -(offset.x() + imageSize.width() - size.width());

      cropBox = new Rect(Math.max(0, -offLeft), Math.max(0, -offTop),
          Math.min(size.width(), imageSize.width()), Math.min(size.height(), imageSize.height()));

      image = new Mat(image, cropBox);

      copyMakeBorder(image, translatedImage, Math.max(0, offTop), Math.max(0, offBottom),
          Math.max(0, offLeft), Math.max(0, offRight), 1);

    } else {
      cropBox = new Rect(-offset.x(), -offset.y(), size.width(), size.height());
      translatedImage = new Mat(image, cropBox);
    }

    // close
    imageSize.close();

    return translatedImage;
  }
}
