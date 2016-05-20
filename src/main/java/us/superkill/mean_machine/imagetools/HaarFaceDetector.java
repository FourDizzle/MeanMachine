package us.superkill.mean_machine.imagetools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class HaarFaceDetector extends FaceDetector {
	
	private static final Logger log = 
			LogManager.getLogger(HaarFaceDetector.class);
	
	public Rect[] detectFaces(Mat image) {
		
		MatOfRect faces = new MatOfRect();
		Mat gray = new Mat();
		Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(gray, gray);
		int absoluteFaceSize = 0;
		if (absoluteFaceSize == 0) {
		    int dheight = gray.rows();
		    if (Math.round(dheight * 0.2f) > 0) {
		        absoluteFaceSize = Math.round(dheight * 0.2f);
		    }
		}
		
		CascadeClassifier faceCascade = new CascadeClassifier("/Users/ncassiani/Projects/MeanMachine/mean-machine/src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
		
		faceCascade.detectMultiScale(gray, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize, absoluteFaceSize), new Size());
		Rect[] facesArray = faces.toArray();
		log.debug(facesArray.length + " potential matches.");
//		for(int i = 0; i < facesArray.length; i++) {
//			Imgproc.rectangle(image, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
//		}
		return facesArray;
	}
}
