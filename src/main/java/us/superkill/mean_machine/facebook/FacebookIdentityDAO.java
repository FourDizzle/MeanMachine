package us.superkill.mean_machine.facebook;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;

import us.superkill.mean_machine.identity.Identity;
import us.superkill.mean_machine.identity.IdentityDAO;
import us.superkill.mean_machine.identity.Photo;
import us.superkill.mean_machine.identity.Post;

/**
 * Methods for getting people identity from Facebook. Uses fbRest
 * @author ncassiani
 *
 */
public class FacebookIdentityDAO extends IdentityDAO {
	private static final Logger log = 
			LogManager.getLogger(FacebookIdentityDAO.class);
	
	private FacebookClient fbClient;
	
	public FacebookIdentityDAO(FacebookClient fbClient) {
		this.fbClient = fbClient;
	}
	
	@Override
	protected List<FacebookIdentity> getListOfIdentities(int maxNumIdentities) {
		//Grab friends from Graph API
		Connection<User> friends = 
				fbClient.fetchConnection(
						"me/friends", 
						User.class,
						Parameter.with("fields", "first_name,last_name,id"),
						Parameter.with("limit", maxNumIdentities));
		
		log.debug("List of Friends:");
		
		List<FacebookIdentity> result = new ArrayList<FacebookIdentity>();
		
		for(User friend: friends.getData()) {
			log.debug(friend.getFirstName() + " " + friend.getLastName());
			FacebookIdentity identity = new FacebookIdentity();
			//Set all but photos
			identity.setFirstName(friend.getFirstName());
			identity.setLastName(friend.getLastName());
			identity.setFacebookId(friend.getId());
			
			result.add(identity);
		}
		
		return result;
	}

	@Override
	protected List<Photo> 
	downloadIdentitysPhotos(Identity identity, int numberOfPhotos, int offset) {
		FacebookIdentity friend = (FacebookIdentity) identity;
		
		Connection<com.restfb.types.Photo> photos = 
				fbClient.fetchConnection(friend.getFacebookId() + "/photos", 
						com.restfb.types.Photo.class, 
						Parameter.with("fields", "tags{id,x,y},images"),
							Parameter.with("limit", numberOfPhotos),
							Parameter.with("offset", offset));
		List<com.restfb.types.Photo> fbPhotos = photos.getData();
		
		return convertFbPhotoToIdentityPhoto(fbPhotos);
	}
	
	private List<Photo> 
	convertFbPhotoToIdentityPhoto(List<com.restfb.types.Photo> fbPhotos) {
		//TODO: flesh out method
		return null;
	}

	@Override
	protected List<Post> 
	downloadIdentitysPosts(Identity identity, int numberOfPosts, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
