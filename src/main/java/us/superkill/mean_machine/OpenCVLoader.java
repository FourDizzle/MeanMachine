package us.superkill.mean_machine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton
 * @author ncassiani
 */
public class OpenCVLoader {
	
	private static final Logger log = LogManager.getLogger(OpenCVLoader.class);
	private static OpenCVLoader instance = null;
	
	protected OpenCVLoader() {
		String lib = OpenCVLoader.class
						.getResource("libopencv_java310.so")
						.getPath();
		System.load(lib);
		log.info("Finished loaing ");
	}
	
	public static void load() {
		log.info("Loading OpenCV..");
		if (instance == null) {
			instance = new OpenCVLoader();
		}
		log.info("Already loaded!");
	}
}
