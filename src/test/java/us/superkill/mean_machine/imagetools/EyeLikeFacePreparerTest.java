package us.superkill.mean_machine.imagetools;

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public class EyeLikeFacePreparerTest {
	
	@BeforeClass
	public static void loadOpenCv() {
		String pathToOpenCvLib = 
				"/Users/ncassiani/Projects/MeanMachine/opencv/build/lib/";
		System.load(pathToOpenCvLib + "libopencv_java310.so");
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
		BufferedImage image = null;
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		
		Mat result = Imgcodecs.imread(file.getAbsolutePath());
		
		return result;
	}
}
