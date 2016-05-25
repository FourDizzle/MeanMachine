package us.superkill.mean_machine.detect;

import static org.bytedeco.javacpp.opencv_core.split;
import static org.bytedeco.javacpp.opencv_objdetect.CASCADE_SCALE_IMAGE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class FaceFinder extends FaceDetector {
	
	private static final Logger log = LogManager.getLogger(FaceFinder.class);
	
	private static final CascadeClassifier faceCascade = 
			new CascadeClassifier(
					FaceFinder.class
						.getResource("/haarcascades/haarcascade_frontalface_alt.xml")
						.getPath());
	
	public Rect[] detectFaces(Mat image) {
		log.debug("Looking for faces.");
		
		RectVector faces = new RectVector();
		MatVector rgbChannels = new MatVector();
		split(image, rgbChannels);
		
		Mat imageGray = rgbChannels.get(2);
		
		faceCascade.detectMultiScale(
				imageGray, 
				faces, 
				1.1, 
				3, 
				0|CASCADE_SCALE_IMAGE, 
				new Size(100, 100), 
				new Size(500,500) );
		
		log.debug("Finished finding faces.");
		Rect[] faceArray = new Rect[faces.sizeof()];
		for (int i = 0; i < faces.sizeof(); i++) {
			faceArray[i] = faces.get(i);
		}
		return faceArray;
	}
}
