package us.superkill.mean_machine.db;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import us.superkill.mean_machine.imagetools.RecognizerPreparer;

public abstract class ImageSource {
	
	Logger log = LogManager.getLogger(ImageSource.class);
	
	protected RecognizerPreparer preparer;
	protected String imageDir;
	
	public ImageSource (RecognizerPreparer preparer, String imageDir) {
		this.preparer = preparer;
		this.imageDir = imageDir;
	}
	
	public abstract void getImages();
	
	protected void addToDatabase(List<Friend> friends) {
		log.debug("Pushing friends to db.");
        for (Friend friend: friends) {
        	//Get Session
        	Session session = HibernateUtil.getSessionFactory().openSession();
        	log.debug("Start hibernate session");
	        //start transaction
	        session.beginTransaction();
	        log.debug("Saving " + friend.toString());
	        //Save the Model object
	        session.save(friend);
	        //Commit transaction
	        session.getTransaction().commit();
	        log.debug("Success.");
	        session.close();
        }
	}
	
	protected void saveImage(Mat image, String filename) {
		Imgcodecs.imwrite(filename, image);
	}
}
