package us.superkill.mean_machine.eyelike;

import static us.superkill.mean_machine.eyelike.DetectConfig.*;
import static us.superkill.mean_machine.eyelike.Helper.computeDynamicThreshold;
import static us.superkill.mean_machine.eyelike.Helper.matrixMagnitude;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PupilFinder {
	
	private static final Logger log = LogManager.getLogger(PupilFinder.class);
	
	private static Point unscalePoint(Point p, Rect origSize) {
		float ratio = (((float)kFastEyeWidth)/origSize.width);
		int x = (int) Math.round(p.x / ratio);
		int y = (int) Math.round(p.y / ratio);
		
		return new Point(x, y);
	}
	
	private static Mat scaleToFastSize(Mat src) {
		Imgproc.resize(src, src, 
				new Size(kFastEyeWidth, 
						((double) kFastEyeWidth/src.cols()) * src.rows()));
		return src;
	}
	
	private static Mat computeMatXGradient(Mat mat) {
		Mat output = new Mat(mat.rows(), mat.cols(), CvType.CV_64F);
		for(int y = 0; y < mat.rows(); y++) {
			output.put(y,0, mat.get(y,1)[0] - mat.get(y,0)[0]);
			for(int x = 1; x < mat.cols() - 1; x++) {
				output.put(y,x, (mat.get(y,x+1)[0] - mat.get(y,x-1)[0])/2.0);
			}
		}
		return output;
	}
	
	private static Mat testPossibleCentersFormula(
			int x, int y, Mat weight, double gx, double gy, Mat out) {
		//for all possible centers
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
					double dotProduct = dx*gx + dy*gy;
					dotProduct = Math.max(0.0, dotProduct);
					//square and multiply by the weight
					if (kEnableWeight) {
						out.put(cy, cx, 
								out.get(cy, cx)[0]
									+ dotProduct * dotProduct
									* (weight.get(cy, cx)[0]/kWeightDivisor));
					} else {
						out.put(cy, cx, 
								out.get(cy, cx)[0] + dotProduct * dotProduct);
					}
				}
			}
		}
		return out;
	}
	
	public static Point findEyeCenter(Mat face, Rect eye) {
		
		Mat eyeROIUnscaled = face.submat(eye);
		Mat eyeROI = scaleToFastSize(eyeROIUnscaled);
		
		//Convert to grayscale
		List<Mat> rgbChannels = new ArrayList<Mat>();
		Core.split(eyeROI, rgbChannels);
		eyeROI = rgbChannels.get(2);
//		Imgproc.cvtColor(eyeROI, eyeROI, Imgproc.COLOR_RGB2GRAY);
		
		//Find the gradient
		Mat gradientX = computeMatXGradient(eyeROI);
		Mat gradientY = computeMatXGradient(eyeROI.t()).t();
		
		// Normalize and threshold the gradient
		// compute all the magnitudes
		Mat mags = matrixMagnitude(gradientX, gradientY);
		
		// Compute the threshold
		double gradientThresh = 
				computeDynamicThreshold(mags, kGradientThreshold);
		
		//normalize
		for (int y = 0; y < eyeROI.rows(); ++y) {
			for(int x = 0; x < eyeROI.cols(); ++x) {
				double gX = gradientX.get(y, x)[0];
				double gY = gradientY.get(y, x)[0];
				double magnitude = mags.get(y, x)[0];
				if (magnitude > gradientThresh) {
					gradientX.put(y, x, gX/magnitude);
					gradientY.put(y, x, gY/magnitude);
				} else {
					gradientX.put(y, x, 0.0);
					gradientY.put(y, x, 0.0);
				}
			}
		}
		
		// Create a blurred and inverted image for weighting
		Mat weight = new Mat();
		
		Imgproc.GaussianBlur(eyeROI,
				             weight,
				             new Size(kWeightBlurSize, kWeightBlurSize),
				             0, 0);
		for (int y = 0; y < weight.rows(); y++) {
			for (int x = 0; x < weight.cols(); x++) {
				weight.put(y, x, 255 - weight.get(y, x)[0]);
			}
		}
		
		 //-- Run the algorithm!
		Mat outSum = Mat.zeros(eyeROI.rows(), eyeROI.cols(), CvType.CV_64F);
		// for each possible gradient location
		// Note: these loops are reversed from the way the paper does them
		// it evaluates every possible center for each gradient location instead of
		// every possible gradient location for every center.
		log.debug("Eye Size: " + outSum.rows() + "x" + outSum.cols());
		for (int y = 0; y < weight.rows(); y++) {
			for (int x = 0; x < weight.cols(); x++) {
				double gX = gradientX.get(y, x)[0];
				double gY = gradientY.get(y, x)[0];
				if ((gX == 0.0 && gY == 0.0) == false) {
					outSum = testPossibleCentersFormula(
							x, y, weight, gX, gY, outSum);
				}
			}
		}
		
		// scale all the values down, basically averaging them
		double numGradients = (weight.rows()*weight.cols());
		Mat out = new Mat();
		outSum.convertTo(out, CvType.CV_64F, 1.0/numGradients);
		
		// find the maximum point
		Core.MinMaxLocResult outMinMaxLocResult = Core.minMaxLoc(out);
		Point maxP = outMinMaxLocResult.maxLoc;
//		double maxVal = outMinMaxLocResult.maxVal;
		
		return unscalePoint(maxP, eye);
	}
}
