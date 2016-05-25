package us.superkill.mean_machine.imagetools;

import static org.bytedeco.javacpp.opencv_core.copyMakeBorder;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.bytedeco.javacpp.opencv_imgproc.getRotationMatrix2D;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;

import us.superkill.mean_machine.detect.FaceDetector;
import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;
import us.superkill.mean_machine.eyelike.EyeFinder;
import us.superkill.mean_machine.eyelike.PupilFinder;

public class EyeLikeFacePreparer extends RecognizerPreparer {
	
	private static final Logger log = 
			LogManager.getLogger(EyeLikeFacePreparer.class);
	
	private static final int    HEIGHT_TARGET = 720;
	private static final int    WIDTH_TARGET = 720;
	//target ratio between eye width and height
	private static final double EYEW_RATIO_TARGET = .40;
	private static final double EYEW_TARGET = EYEW_RATIO_TARGET*HEIGHT_TARGET;
	//midpoint target
	private static final double MID_X_TARGET_RATIO = .5;
	private static final double MID_Y_TARGET_RATIO = .4;
	private static final double MID_X_TARGET = WIDTH_TARGET*MID_X_TARGET_RATIO;
	private static final double MID_Y_TARGET = HEIGHT_TARGET*MID_Y_TARGET_RATIO;
	private static final int    GAP_BORDER = 1;
	//debug
	private static final boolean DEBUG = true;
	
	private FaceDetector faceDetector;
	
	public EyeLikeFacePreparer(FaceDetector faceDetector) {
		this.faceDetector = faceDetector;
	}
	
	private static Point testAndRescalePupil(Point pupil, Rect eye) 
			throws FormatterTargetNotFound {
		log.debug("Rescalling pupil coordinates");
		if (pupil.x() == 0.0 && pupil.y() == 0.0) {
			throw new FormatterTargetNotFound("Pupil not found");
		}
		Point offset = eye.tl();
		return new Point(pupil.x() + offset.x(), pupil.y() + offset.y());
	}
	
	private static double distBetweenPoints(Point a, Point b) {
		return Math.sqrt(Math.pow((a.x() - b.x()), 2) + Math.pow((a.y() - b.y()), 2));
	}
	
	private static Mat translate(Mat image, Point offset, Size size) {
		Mat translatedImage = null;
		Rect cropBox = null;
		Size imageSize = new Size(image.cols(), image.rows());
		
		if (offset.x() > 0
				|| offset.y() > 0
				|| offset.x() + imageSize.width() < size.width()
				|| offset.y() + imageSize.height() < size.height()) {
			
			int offTop = (int) Math.round(offset.y());
			int offBottom = (int) Math.round(
					-(offset.y() + imageSize.height() - size.height()));
			int offLeft = (int) Math.round(offset.x());
			int offRight = (int) Math.round(
					-(offset.x() + imageSize.width() - size.width()));
			
			cropBox = new Rect(
					Math.max(0, -offLeft),
					Math.max(0, -offTop),
					(int) Math.round(Math.min(size.width(), 
							                  imageSize.width())),
					(int) Math.round(Math.min(size.height(), 
							                  imageSize.height())));
			
			image = new Mat(image, cropBox);
			
			copyMakeBorder(
					image, 
					translatedImage, 
					Math.max(0, offTop),
					Math.max(0, offBottom),
					Math.max(0, offLeft), 
					Math.max(0, offRight), 
					GAP_BORDER);
			
		} else {
			cropBox = new Rect(
					(int) Math.round(-offset.x()),
					(int) Math.round(-offset.y()),
					(int) Math.round(size.width()),
					(int) Math.round(size.height()));
			translatedImage = new Mat(image, cropBox);
		}
		
		//close
		imageSize.close();
		
		return translatedImage;
	}
	
	private Rect[] rescaleEyes(Rect[] eyes, Rect face) {
		Rect[] rescaledEyes = new Rect[eyes.length];
		
		for (int i = 0; i < eyes.length; i++) { 
			rescaledEyes[i] = 
					new Rect(new Point(eyes[i].tl().x() + face.tl().x(), 
							           eyes[i].tl().y() + face.tl().y()), 
							 new Point(eyes[i].br().x() + face.tl().x(), 
									   eyes[i].br().y() + face.tl().y()));
		}
		
		return rescaledEyes;
	}

	@Override
	public Mat prepare(Mat image, Point centerOfFace) 
			throws FormatterTargetNotFound {
		Rect face = findTargetFace(image, centerOfFace);
		Mat preparedImage = crop(image, face);
		return preparedImage;
	}
	
	private Mat crop(Mat image, Rect face) throws FormatterTargetNotFound {
		Mat croppedImage = new Mat();
		
		log.debug("Finding pupils.");
		Point[] pupils = findPupils(image, face);
		if (DEBUG) {
			circle(image, pupils[0], 3, new Scalar(0, 255, 0, 1));
			circle(image, pupils[1], 3, new Scalar(0, 0, 255, 1));
		}
		
		Point midpoint = calculateMidpointBetweenPupils(pupils);
		Double eyeWidth = distBetweenPoints(pupils[0], pupils[1]);
		Double eyeAngle = Math.atan((pupils[0].y() - pupils[1].y()) /
	                                (pupils[0].x() - pupils[1].x()));
		
		
		//Calculate scaling
		double scaleF = EYEW_TARGET/eyeWidth;
		Size scSize = new Size((int) Math.round(image.cols() * scaleF), 
				               (int) Math.round(image.rows() * scaleF)); 
		Point scMid = new Point((int)Math.round(midpoint.x() * scaleF), (int) Math.round(midpoint.y() * scaleF));
		Point2f scMid2f = new Point2f((int)Math.round(midpoint.x() * scaleF), (int) Math.round(midpoint.y() * scaleF));
		//rescale
		resize(image, croppedImage, scSize);
		
		//rotate
		if (eyeAngle != 0) {
			log.debug("Rotating image");
			Mat rotMatrix = getRotationMatrix2D(
					scMid2f, 
					Math.toDegrees(eyeAngle), 1.0);
			warpAffine(croppedImage, croppedImage, rotMatrix, 
					croppedImage.size());
		}
		
		//Calculate offset
		Point offset = 
				new Point((int)Math.round(MID_X_TARGET - scMid.x()), (int)Math.round(MID_Y_TARGET - scMid.y()));
		croppedImage = translate(croppedImage, offset, new Size(WIDTH_TARGET, HEIGHT_TARGET));
		
		//close points
		scMid.close();
		
		return croppedImage;
	}
	
	private Point[] findPupils(Mat image, Rect face) 
			throws FormatterTargetNotFound {
		Rect[] eyes = EyeFinder.findEyes(image, face);
		
		if (DEBUG) {
			for (Rect eye: eyes) {
				rectangle(image, eye.br(), eye.tl(), new Scalar(255, 0, 0, 1));
			}
		}
		
		Point rightPupil = 
				PupilFinder.findEyeCenter(new Mat(image, face), eyes[0]);
		Point leftPupil = 
				PupilFinder.findEyeCenter(new Mat(image, face), eyes[1]);
		
		//rescale everything to be relative to original image
		eyes = rescaleEyes(eyes, face);
		log.debug("testing right pupil");
		rightPupil = testAndRescalePupil(rightPupil, eyes[0]);
		log.debug("right pupil found: (" + rightPupil.x() + ", " + rightPupil.y() + ")");
		log.debug("testing left pupil");
		leftPupil = testAndRescalePupil(leftPupil, eyes[1]);
		log.debug("left pupil found: (" + leftPupil.x() + ", " + leftPupil.y() + ")");
		Point[] pupils = {rightPupil, leftPupil};
		return pupils;
	}
	
	private Rect findTargetFace(Mat image, Point centerOfFace) 
			throws FormatterTargetNotFound {
		Rect[] allFaces = faceDetector.detectFaces(image);
		Rect targetFace = null;
		
		for (Rect face: allFaces) {
			if (face.contains(centerOfFace)) {
				targetFace = face;
			}
		}
		
		if (targetFace == null) {
			throw new FormatterTargetNotFound("No face found at target Point");
		}
		return targetFace;
	}
	
	private Point calculateMidpointBetweenPupils(Point[] pupils) {
		return new Point(pupils[0].x() / 2 + pupils[1].x() / 2, 
				         pupils[0].y() / 2 + pupils[1].y() / 2);
	}
}
