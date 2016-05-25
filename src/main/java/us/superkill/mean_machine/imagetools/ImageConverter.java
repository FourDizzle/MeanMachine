package us.superkill.mean_machine.imagetools;

import static org.bytedeco.javacpp.opencv_core.CV_8UC3;

import org.bytedeco.javacpp.opencv_core.Mat;

public class ImageConverter {
	
	public Mat byteArrayToMat(byte[] image, int width, int height) {
		Mat mat = new Mat(width, height, CV_8UC3);
		mat.data().put(image);
		return mat;
	}
	
	public byte[] matToByteArray(Mat image) {
		return image.data().asByteBuffer().array();
	}
}
