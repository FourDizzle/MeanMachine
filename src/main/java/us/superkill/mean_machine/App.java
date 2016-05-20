package us.superkill.mean_machine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.superkill.mean_machine.db.HibernateUtil;
import us.superkill.mean_machine.db.ImageSource;
import us.superkill.mean_machine.facebook.FacebookConnector;
import us.superkill.mean_machine.facebook.FacebookImageSource;
import us.superkill.mean_machine.imagetools.EyeLikeFacePreparer;
import us.superkill.mean_machine.imagetools.HaarFaceDetector;

public class App {
	//get logger instance
	private static final Logger log = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {
		//Loads opencv java lib. Jar is just a wrapper
		//TODO: need to make this relative possibly add to resources
		String pathToOpenCvLib = 
				"/Users/ncassiani/Projects/MeanMachine/opencv/build/lib/";
		System.load(pathToOpenCvLib + "libopencv_java310.so");
		log.info("Loaded OpenCV Native Code");
		
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("download")) {
				if (args.length > 1) {
					ImageSource source = new FacebookImageSource(
							new FacebookConnector(args[1]).getFbClient(),
							new EyeLikeFacePreparer(new HaarFaceDetector()),
							"/Users/ncassiani/Projects/MeanMachine/images");
					source.getImages();
				}
			}
			
			if (args[0].equalsIgnoreCase("train")) {
				trainRecognizer();
			}
		}

        //terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();

	}
	
	private static void trainRecognizer() {
//		FaceRecognizer recognizer = Trainer.trainFaceRecognizer();
	}
}
