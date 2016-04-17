package us.superkill.mean_machine;

public class App {
	
	public void main(String[] args) {
		//Loads opencv java lib. Jar is just a wrapper
		//TODO: need to make this relative possibly add to resources
		String pathToOpenCvLib = "~/Projects/MeanMachine/opencv/build/lib/";
		System.load(pathToOpenCvLib + "libopencv_java310.so");
	}
}
