package us.superkill.mean_machine.imagetools;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;
import static org.bytedeco.javacpp.opencv_objdetect.CASCADE_SCALE_IMAGE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import us.superkill.mean_machine.detect.FaceDetector;

public class HaarFaceDetector extends FaceDetector {
	
	private static final Logger log = 
			LogManager.getLogger(HaarFaceDetector.class);
	
	public Rect[] detectFaces(Mat image) {
		
		RectVector faces = new RectVector();
		Mat gray = new Mat();
		cvtColor(image, gray, COLOR_BGR2GRAY);
		equalizeHist(gray, gray);
		int absoluteFaceSize = 0;
		if (absoluteFaceSize == 0) {
		    int dheight = gray.rows();
		    if (Math.round(dheight * 0.2f) > 0) {
		        absoluteFaceSize = Math.round(dheight * 0.2f);
		    }
		}
		
		CascadeClassifier faceCascade = new CascadeClassifier("/Users/ncassiani/Projects/MeanMachine/mean-machine/src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
		
		faceCascade.detectMultiScale(gray, faces, 1.1, 2, 0 | CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize, absoluteFaceSize), new Size());
		Rect[] faceArray = new Rect[faces.sizeof()];
		for (int i = 0; i < faces.sizeof(); i++) {
			faceArray[i] = faces.get(i);
		}
		log.debug(faceArray.length + " potential matches.");
//		for(int i = 0; i < facesArray.length; i++) {
//			Imgproc.rectangle(image, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
//		}
		
		faceCascade.close();
		
		return faceArray;
	}
}
