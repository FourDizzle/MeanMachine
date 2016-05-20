package us.superkill.mean_machine.imagetools;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public abstract class FaceDetector {
	
	public abstract Rect[] detectFaces(Mat image);
}
