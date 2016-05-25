package us.superkill.mean_machine.imagetools;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public abstract class RecognizerPreparer {
	
	public abstract Mat prepare(Mat image, Point centerOfTarget) 
			throws FormatterTargetNotFound;
}
