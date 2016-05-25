package us.superkill.mean_machine.imagetools;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.junit.BeforeClass;
import org.junit.Test;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public class EyeLikeFacePreparerTest {
	
	@BeforeClass
	public static void loadOpenCv() {
//		String pathToOpenCvLib = 
//				"/Users/ncassiani/Projects/MeanMachine/opencv/build/lib/";
//		System.load(pathToOpenCvLib + "libopencv_java310.so");
	}
	
	@Test
	public void givenTestImagesPreparerCropsToFace() {
		EyeLikeFacePreparer preparer = new EyeLikeFacePreparer(new HaarFaceDetector());
		Mat image = getFaceImage("images/2.jpg");
		Point faceTarget = new Point(324, 162);
		Mat preparedImage = null;
		
		try {
			preparedImage = preparer.prepare(image, faceTarget);
		} catch (FormatterTargetNotFound e) {
			System.out.println(e.getMessage());
		}
		
		assertNotNull(preparedImage);
	}
	
	private Mat getFaceImage(String fileName) {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		
		Mat result = imread(file.getAbsolutePath());
		
		return result;
	}
}
