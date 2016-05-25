package us.superkill.mean_machine.detect;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public abstract class FaceDetector {
	
	public abstract Rect[] detectFaces(Mat image);
}
