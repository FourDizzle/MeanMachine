package us.superkill.mean_machine.eyelike;

import static us.superkill.mean_machine.eyelike.DetectConfig.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class EyeFinder {
	
	private static final Logger log = LogManager.getLogger(EyeFinder.class);
	
	public static Rect[] findEyes(Mat image, Rect face) {
		log.debug("Looking for eyes.");
		List<Mat> rgbChannels = new ArrayList<Mat>(3);
		Core.split(image, rgbChannels);
		Mat imageGray = rgbChannels.get(2);
		
		Mat faceROI = imageGray.submat(face);
		
		if (kSmoothFaceImage) {
			double sigma = kSmoothFaceFactor * face.width;
		    Imgproc.GaussianBlur(faceROI, faceROI, new Size(0, 0), sigma);
		}
		
		//Find eyes
		int eyeRegionWidth = 
				(int) Math.round(face.width * ((double) kEyePercentWidth/100));
		int eyeRegionHeight =
				(int) Math.round(face.height * ((double) kEyePercentHeight/100));
		int eyeRegionTop = 
				(int) Math.round(face.height * ((double) kEyePercentTop/100));
		
		Rect leftEyeRegion = 
				new Rect((int) Math.round(face.width * ((double) kEyePercentSide/100)),
				         eyeRegionTop,eyeRegionWidth,eyeRegionHeight);
		
		Rect rightEyeRegion = 
				new Rect((int) Math.round(face.width - eyeRegionWidth - 
						    face.width*((double) kEyePercentSide/100)),
				         eyeRegionTop,eyeRegionWidth,eyeRegionHeight);
		
		log.debug("returning eye areas");
		return new Rect[] {leftEyeRegion, rightEyeRegion};
	}
}