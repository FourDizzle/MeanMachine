package us.superkill.mean_machine.imagetools;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import us.superkill.mean_machine.exceptions.TrainerTargetNotFound;

public abstract class RecognizerPreparer {
	
	public abstract Mat prepare(Mat image, Point centerOfTarget) 
			throws TrainerTargetNotFound;
}
