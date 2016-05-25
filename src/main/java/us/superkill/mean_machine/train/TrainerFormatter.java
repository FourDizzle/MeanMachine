package us.superkill.mean_machine.train;

import org.opencv.core.Point;
import org.opencv.core.Rect;

import us.superkill.mean_machine.detect.FaceDetector;
import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;
import us.superkill.mean_machine.identity.Photo;
import us.superkill.mean_machine.identity.PhotoFormatter;

public class TrainerFormatter extends PhotoFormatter {
	
	private FaceDetector faceDetector;
	
	public TrainerFormatter(FaceDetector faceDetector) {
		this.faceDetector = faceDetector;
	}
	@Override
	public Photo format(Photo photo) throws FormatterTargetNotFound {
		Rect face = findTargetsFace(photo);
		return null;
	}
	
	private Rect findTargetsFace(Photo photo) throws FormatterTargetNotFound {
		Point targetLoc = 
				new Point(photo.getPersonLocX(), photo.getPersonLocY());
		Rect[] allFaces = faceDetector.detectFaces(photo.getImage());
		
		return findTargetFaceInArrayOfFaces(allFaces, targetLoc);
	}
	
	private Rect findTargetFaceInArrayOfFaces(Rect[] faces, Point targetLoc) 
	throws FormatterTargetNotFound {
		Rect targetsFace = null;
		for (Rect face: faces) {
			if (face.contains(targetLoc)) targetsFace = face;
		}
		if (targetsFace == null) 
			throw new FormatterTargetNotFound("Target face not found");
		return targetsFace;
	}

}
