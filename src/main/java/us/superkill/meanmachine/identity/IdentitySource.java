package us.superkill.meanmachine.identity;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.processor.CompositePreprocessor;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class IdentitySource {

  private static final Logger log = LogManager.getLogger(IdentitySource.class);

  private final IdentityDAO identityDao;
  private CompositePreprocessor preprocessor = new CompositePreprocessor();
  private CompositePostFilter postFilter = new CompositePostFilter();

  public IdentitySource(IdentityDAO identityDao) {
    this.identityDao = identityDao;
  }

  public List<Identity> getIdentities(int numIdentities, int photoPerId, int postsPerId) {
    List<Identity> identities = this.identityDao.getIdentities(numIdentities);
    for (Identity identity : identities) {
      identity.setPhotos(getPhotosOfPerson(identity, photoPerId));
//      identity.setPosts(getPosts(identity, postsPerId));
    }
    return identities;
  }

  private List<Face> getPhotosOfPerson(Identity identity, int photosPerPerson) {
    List<Face> photos = new ArrayList<Face>();
    int numberOfPhotosPreviouslyDownloaded = 0;
    while (photos.size() < photosPerPerson) {
      int numOfPhotosToTry = photosPerPerson - photos.size();
      List<Face> potentialPhotos = this.identityDao.getIdentitysPhotos(identity, numOfPhotosToTry,
          numberOfPhotosPreviouslyDownloaded);
      numberOfPhotosPreviouslyDownloaded += numOfPhotosToTry;
      if (potentialPhotos != null)
        photos.addAll(formatBatchOfPhotos(potentialPhotos));
      if (potentialPhotos.isEmpty())
        break;
    }
    return photos;
  }

  private List<Face> formatBatchOfPhotos(List<Face> potentialPhotos) {
    ArrayList<Face> formattedPhotos = new ArrayList<Face>();
    for (Face photo : potentialPhotos) {
      try {
        Face formatted = preprocessor.process(photo);
        formattedPhotos.add(formatted);
        log.debug("Saving as test");
        formatted.saveImage("/Users/ncassiani/Projects/MeanMachine/images");
      } catch (PreprocessFailedException e) {
        log.debug("Bad Photo for Training: " + e.getMessage());
      }
    }
    return formattedPhotos;
  }

  private List<Post> getPosts(Identity identity, int postsPerPerson) {
    List<Post> posts = new ArrayList<Post>();
    int numberOfPostsPreviouslyDownloaded = 0;
    while (posts.size() < postsPerPerson) {
      int numOfPostsToTry = postsPerPerson - posts.size();
      List<Post> potentialPosts = this.identityDao.getIdentitysPosts(identity, numOfPostsToTry,
          numberOfPostsPreviouslyDownloaded);
      numberOfPostsPreviouslyDownloaded += numOfPostsToTry;
      potentialPosts = postFilter.filter(potentialPosts);
      if (potentialPosts != null)
        posts.addAll(potentialPosts);
    }
    return posts;
  }

  public void setPhotoPreprocessor(PhotoPreprocessor processor) {
    this.preprocessor.add(processor);
  }

  public void setPostFilter(PostFilter filter) {
    this.postFilter.add(filter);
  }
}
