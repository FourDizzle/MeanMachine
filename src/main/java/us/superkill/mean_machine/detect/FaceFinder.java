package us.superkill.mean_machine.detect;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FaceFinder extends FaceDetector {
	
	private static final Logger log = LogManager.getLogger(FaceFinder.class);
	
	private static final CascadeClassifier faceCascade = 
			new CascadeClassifier(
					FaceFinder.class
						.getResource("/haarcascades/haarcascade_frontalface_alt.xml")
						.getPath());
	
	public Rect[] detectFaces(Mat image) {
		log.debug("Looking for faces.");
		
		MatOfRect faces = new MatOfRect();
		List<Mat> rgbChannels = new ArrayList<Mat>(3);
		Core.split(image, rgbChannels);
		
		Mat imageGray = rgbChannels.get(2);
		
		faceCascade.detectMultiScale(
				imageGray, 
				faces, 
				1.1, 
				3, 
				0|Objdetect.CASCADE_SCALE_IMAGE, 
				new Size(100, 100), 
				new Size(500,500) );
		
		log.debug("Finished finding faces.");
		return faces.toArray();
	}
}
