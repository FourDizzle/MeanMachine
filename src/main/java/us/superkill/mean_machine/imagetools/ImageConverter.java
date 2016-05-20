package us.superkill.mean_machine.imagetools;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageConverter {
	
	public Mat byteArrayToMat(byte[] image, int width, int height) {
		Mat mat = new Mat(width, height, CvType.CV_8UC3);
		mat.put(0, 0, image);
		return mat;
	}
	
	public byte[] matToByteArray(Mat image) {
		long size = image.total() * image.channels();
		byte[] buffer = new byte[(int)size];
		image.get(0, 0, buffer);
		
		return buffer;
	}
}
