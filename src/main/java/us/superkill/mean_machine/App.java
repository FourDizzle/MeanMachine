package us.superkill.mean_machine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.superkill.mean_machine.db.HibernateUtil;

public class App {
	//get logger instance
	private static final Logger log = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {
		log.trace("Starting application");
		//loading OpenCV lib
		OpenCVLoader.load();
		

        //terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();

	}
}
