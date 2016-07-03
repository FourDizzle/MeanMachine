package us.superkill.eyelike;

import static org.bytedeco.javacpp.opencv_core.CV_64F;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.GaussianBlur;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import static us.superkill.eyelike.Helper.computeDynamicThreshold;
import static us.superkill.eyelike.Helper.matrixMagnitude;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.indexer.DoubleIndexer;

public class PupilFinder {

  private static final Logger log = LogManager.getLogger(PupilFinder.class);
  private static final EyeLikeConfig config = EyeLikeConfig.getInstance();

  public static Point findEyeCenter(Mat face, Rect eye) {
    Mat eyeROIUnscaled = new Mat(face, eye);
    Mat eyeROI = scaleToFastSize(eyeROIUnscaled);

    // Convert to grayscale
    cvtColor(eyeROI, eyeROI, COLOR_RGB2GRAY);
    // Find the gradient
    Mat gradientX = computeMatXGradient(eyeROI);
    Mat gradientY = computeMatXGradient(eyeROI.t().asMat()).t().asMat();

    // Normalize and threshold the gradient
    // compute all the magnitudes
    Mat mags = matrixMagnitude(gradientX, gradientY);

    // Compute the threshold
    double gradientThresh = computeDynamicThreshold(mags, config.kGradientThreshold);

    // normalize
    DoubleIndexer gradXIndex = gradientX.createIndexer();
    DoubleIndexer gradYIndex = gradientY.createIndexer();
    DoubleIndexer magIndex = mags.createIndexer();
    for (int y = 0; y < eyeROI.rows(); ++y) {
      for (int x = 0; x < eyeROI.cols(); ++x) {
        double gX = gradXIndex.get(y, x);
        double gY = gradYIndex.get(y, x);
        double magnitude = magIndex.get(y, x);
        if (magnitude > gradientThresh) {
          gradXIndex.put(y, x, gX / magnitude);
          gradYIndex.put(y, x, gY / magnitude);
        } else {
          gradXIndex.put(y, x, 0.0);
          gradYIndex.put(y, x, 0.0);
        }
      }
    }

    // Create a blurred and inverted image for weighting
    Mat weight = new Mat();
    GaussianBlur(eyeROI, weight, new Size(config.kWeightBlurSize, config.kWeightBlurSize), 0.0);
    DoubleIndexer weightIndex = weight.createIndexer();
    for (int y = 0; y < weight.rows(); y++) {
      for (int x = 0; x < weight.cols(); x++) {
        weightIndex.put(y, x, 255 - weightIndex.get(y, x));
        // weight.ptr(y, x).put((byte)(255 - weight.ptr(y, x).get()));
      }
    }

    // -- Run the algorithm!
    Mat outSum = Mat.zeros(eyeROI.rows(), eyeROI.cols(), CV_64F).asMat();
    // for each possible gradient location
    // Note: these loops are reversed from the way the paper does them
    // it evaluates every possible center for each gradient location instead of
    // every possible gradient location for every center.
    log.debug("Eye Size: " + outSum.rows() + "x" + outSum.cols());
    for (int y = 0; y < weight.rows(); y++) {
      for (int x = 0; x < weight.cols(); x++) {
        double gX = gradXIndex.get(y, x);
        double gY = gradYIndex.get(y, x);
        if ((gX == 0.0 && gY == 0.0) == false) {
          outSum = testPossibleCentersFormula(x, y, weight, gX, gY, outSum);
        }
      }
    }

    // scale all the values down, basically averaging them
    double numGradients = (weight.rows() * weight.cols());
    Mat out = new Mat();
    outSum.convertTo(out, CV_64F, 1.0 / numGradients, 0.0);
    imwrite("/Users/ncassiani/Projects/MeanMachine/testimg/out.jpg", out);
    // find the maximum point
    Point maxLoc = new Point();
    double[] maxVal = {0.0};
    minMaxLoc(out, null, maxVal, null, maxLoc, null);
    
    Point pupil = rescalePointToFullImage(unscalePoint(maxLoc, eye), eye);
    if (config.isDrawEyeRegionEnabled)
      circle(face, pupil, 3, new Scalar(0, 255, 0, 1));
    return pupil;
  }
  
  private static Point rescalePointToFullImage(Point p, Rect eye) {
    return new Point(p.x() + eye.tl().x(), p.y() + eye.tl().y());
  }
  
  private static Point unscalePoint(Point p, Rect origSize) {
    float ratio = (((float) config.kFastEyeWidth) / origSize.width());
    int x = (int) Math.round(p.x() / ratio);
    int y = (int) Math.round(p.y() / ratio);

    return new Point(x, y);
  }

  private static Mat scaleToFastSize(Mat src) {
    int newWidth = config.kFastEyeWidth;
    int newHeight = config.kFastEyeWidth * src.rows() / src.cols();
    resize(src, src, new Size(newWidth, newHeight));
    return src;
  }

  private static Mat computeMatXGradient(Mat mat) {
    mat.convertTo(mat, CV_64F);
    Mat output = new Mat(mat.rows(), mat.cols(), CV_64F);
    DoubleIndexer outIndex = output.createIndexer();
    DoubleIndexer matIndex = mat.createIndexer();
    for (int y = 0; y < mat.rows(); y++) {
      double a = matIndex.get(y, 1) - matIndex.get(y, 0);
      outIndex.put(y, 0, a);
      for (int x = 1; x < mat.cols() - 1; x++) {
        double b = (matIndex.get(y, x + 1) - matIndex.get(y, x - 1)) / 2.0;
        outIndex.put(y, x, b);
      }
    }
    imwrite("Users/ncassiani/Projects/MeanMachine/testimg/xgrad.jpg", output);
    return output;
  }

  private static Mat testPossibleCentersFormula(int x, int y, Mat weight, double gx, double gy,
      Mat out) {
    // for all possible centers
    DoubleIndexer weightIndex = weight.createIndexer();
    DoubleIndexer outIndex = out.createIndexer();
    for (int cy = 0; cy < out.rows(); cy++) {
      for (int cx = 0; cx < out.cols(); cx++) {
        if ((x == cx && y == cy) == false) {
          // create a vector from the possible center
          // to the gradient origin
          double dx = x - cx;
          double dy = y - cy;
          // normalize d
          double magnitude = Math.sqrt((dx * dx) + (dy * dy));
          dx = dx / magnitude;
          dy = dy / magnitude;
          double dotProduct = dx * gx + dy * gy;
          dotProduct = Math.max(0.0, dotProduct);
          // square and multiply by the weight
          if (config.kEnableWeight) {
            outIndex.put(cy, cx, outIndex.get(cy, cx)
                + dotProduct * dotProduct * (weightIndex.get(cy, cx) / config.kWeightDivisor));
          } else {
            outIndex.put(cy, cx, outIndex.get(cy, cx) + dotProduct * dotProduct);
          }
        }
      }
    }
    return out;
  }
}
