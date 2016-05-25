package us.superkill.mean_machine.detect;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;

public abstract class FaceDetector {
	
	public abstract Rect[] detectFaces(Mat image);
}
