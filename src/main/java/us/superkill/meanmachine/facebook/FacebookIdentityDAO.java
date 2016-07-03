package us.superkill.meanmachine.facebook;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Photo;
import com.restfb.types.User;

import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.identity.Identity;
import us.superkill.meanmachine.identity.IdentityDAO;
import us.superkill.meanmachine.identity.Post;
import us.superkill.meanmachine.imagetools.ImageDownloader;

public class FacebookIdentityDAO extends IdentityDAO {
  private static final Logger log = LogManager.getLogger(FacebookIdentityDAO.class);

  private FacebookClient fbClient;

  public FacebookIdentityDAO(FacebookClient fbClient) {
    this.fbClient = fbClient;
  }

  @Override
  public List<Identity> getIdentities(int maxNumIdentities) {
    Connection<User> friends = fbClient.fetchConnection("me/friends", User.class,
        Parameter.with("fields", "first_name,last_name,id"),
        Parameter.with("limit", maxNumIdentities));
    log.debug("List of Friends:");
    List<Identity> result = getIdentitiesFromFbFriends(friends);
    return result;
  }

  private List<Identity> getIdentitiesFromFbFriends(Connection<User> friends) {
    List<Identity> result = new ArrayList<Identity>();
    for (User friend : friends.getData()) {
      log.debug(friend.getFirstName() + " " + friend.getLastName());
      result.add((Identity) setIdenityFeilds(friend));
    }
    return result;
  }

  private FacebookIdentity setIdenityFeilds(User friend) {
    FacebookIdentity identity = new FacebookIdentity();
    identity.setFirstName(friend.getFirstName());
    identity.setLastName(friend.getLastName());
    identity.setFacebookId(friend.getId());
    return identity;
  }

  @Override
  public List<Face> getIdentitysPhotos(Identity identity, int numberOfPhotos, int offset) {
    List<Face> photos = new ArrayList<Face>();
    FacebookIdentity friend = (FacebookIdentity) identity;

    List<Photo> fbPhotos = fbClient.fetchConnection(friend.getFacebookId() + "/photos",
        Photo.class, Parameter.with("fields", "tags{id,x,y},images,id"),
        Parameter.with("limit", numberOfPhotos), Parameter.with("offset", offset)).getData();
    
    if (fbPhotos.size() > 0)
      fbPhotos = filterForTaggedPhotos(fbPhotos, friend.getFacebookId());
      photos = convertToFace(fbPhotos, friend.getFacebookId());
    return photos;
  }
  
  private List<Photo> filterForTaggedPhotos(List<Photo> photos, String userId) {
    List<Photo> filtered = new ArrayList<Photo>();
    for (Photo photo: photos) {
      for (Photo.Tag tag : photo.getTags()) {
        if (tag.getId().equalsIgnoreCase(userId))
          filtered.add(photo);
      }
    }
    return filtered;
  }
  
  private List<Face> convertToFace(List<Photo> fbPhotos, String userId) {
    List<Face> photos = new ArrayList<Face>();
    for (Photo fbPhoto : fbPhotos) {
      photos.add(convertToFace(fbPhoto, userId));
    }
    return photos;
  }

  private Face convertToFace(Photo fbPhoto, String userId) {
    Face photo = new Face();
    Photo.Image image = fbPhoto.getImages().get(0);

    photo.setFileName(fbPhoto.getId() + ".jpg");
    photo.setWidth(image.getWidth());
    photo.setHeight(image.getHeight());
    photo.setImage(
        ImageDownloader.downloadMat(image.getSource(), image.getWidth(), image.getHeight()));

    for (Photo.Tag tag : fbPhoto.getTags()) {
      if (tag.getId().equalsIgnoreCase(userId)) {
        photo.setFaceLocX(tagValToPix(tag.getX(), image.getWidth()));
        photo.setFaceLocY(tagValToPix(tag.getY(), image.getHeight()));
      }
    }

    return photo;
  }

  private int tagValToPix(double tag, int dimension) {
    return (int) Math.round((tag * dimension) / 100);
  }

  @Override
  public List<Post> getIdentitysPosts(Identity identity, int numberOfPosts, int offset) {
    // TODO Auto-generated method stub
    return null;
  }

}
