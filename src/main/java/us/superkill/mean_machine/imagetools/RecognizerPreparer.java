package us.superkill.mean_machine.imagetools;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public abstract class RecognizerPreparer {
	
	public abstract Mat prepare(Mat image, Point centerOfTarget) 
			throws FormatterTargetNotFound;
}
