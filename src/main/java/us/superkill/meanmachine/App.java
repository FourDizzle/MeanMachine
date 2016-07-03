package us.superkill.meanmachine;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.superkill.meanmachine.facebook.FacebookConnector;
import us.superkill.meanmachine.facebook.FacebookIdentityDAO;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.identity.Identity;
import us.superkill.meanmachine.identity.IdentitySource;

public class App {
  // get logger instance

  private static final Logger log = LogManager.getLogger(App.class);

  public static void main(String[] args) {
    log.trace("Starting application");
    if (args[0].equalsIgnoreCase("download")) {
      downloadPhotos(args[1]);
    }
  }

  private static void downloadPhotos(String apiKey) {
    log.trace("Downloading identities...");
    // IdentityDaoFactory daoFactory = new FacebookDaoFactory(apiKey);
   // PhotoPreprocessor preprocessor = new EyeAlignProcessorFactory().makeProcessor();

    FacebookConnector con = new FacebookConnector(apiKey);

    IdentitySource source = new IdentitySource(new FacebookIdentityDAO(con.getFbClient()));




    List<Identity> people = source.getIdentities(10, 20, 20);
    log.trace("Finished downloading!");
    log.trace("Saving images!");
    for (Identity person : people) {
      for (Face pic : person.getPhotos()) {
        pic.saveImage("/Users/ncassiani/Projects/MeanMachine/images");
      }
    }
  }
}
