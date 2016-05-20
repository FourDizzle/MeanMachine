package us.superkill.mean_machine.facebook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Photo;
import com.restfb.types.User;

import us.superkill.mean_machine.db.Friend;
import us.superkill.mean_machine.db.FriendPhoto;
import us.superkill.mean_machine.db.ImageSource;
import us.superkill.mean_machine.imagetools.ImageDownloader;
import us.superkill.mean_machine.imagetools.RecognizerPreparer;

public class FacebookImageSource extends ImageSource {
	
	private static final Logger log = 
			LogManager.getLogger(FacebookImageSource.class);
	
	private FacebookClient fbClient;
//	private ImageCropper cropper;
//	private FaceDetector faceDetector;
//	private String imageDir;
	
	public FacebookImageSource(FacebookClient fbClient, 
			RecognizerPreparer preparer, String imageDir) {
		super (preparer, imageDir);
		log.trace("Instantiated new FriendPhotoDownloader.");
		this.fbClient = fbClient;
	}
	
	private List<FriendPhoto> getAndProcessFriendsPhotos(Friend friend, int numOfPhotos) {
		List<Photo> photos = getFriendsPhotos(friend, numOfPhotos);
		
		List<FriendPhoto> friendsPhotos = new ArrayList<FriendPhoto>();
		for (Photo photo: photos) {
			//Get tag of friend in question
			Photo.Tag tempTag = null;
			for(Photo.Tag tag: photo.getTags()) {
				if (tag.getId() != null) {
					if (tag.getId().equals(friend.getFacebookId())) {
						tempTag = tag;
					}
				}
			}
			
			//If friend isn't tagged skip image
			if (tempTag == null) {
				continue;
			}
			
			FriendPhoto tempPhoto = new FriendPhoto();
			
			List<Photo.Image> thumbs = photo.getImages();
			String imageUrl = "";
			int width = 0;
			int height = 0;
			for (Photo.Image image: thumbs) {
				if (image.getWidth() > width) {
					width = image.getWidth();
					height = image.getHeight();
					imageUrl = image.getSource();
				}
			}
			
			String destinationFile = this.imageDir +
						"/" + friend.getFirstName() + 
						"-" + photo.getId() + ".jpg";
			try {
				ImageDownloader.downloadImageToFile(imageUrl, destinationFile);
			} catch (Exception e) {
				log.error("Image failed to save: " + e);
			}
			
			
			//crop image for just face
			Mat image = Imgcodecs.imread(destinationFile);
			
			//Delete file only save later if there is a face
			File file = new File(destinationFile);
			file.delete();
			
			Point tagPoint = new Point(image.width() * tempTag.getX() / 100, 
					image.height() * tempTag.getY() / 100);
			
			try {
				image = preparer.prepare(image, tagPoint);
				Imgcodecs.imwrite(destinationFile, image);
				
				tempPhoto.setImageWidth(width);
				tempPhoto.setImageHeight(height);
				tempPhoto.setImageName(destinationFile);
				tempPhoto.setImageExtension("jpg");
				tempPhoto.setFriend(friend);
				tempPhoto.setFacebookId(photo.getId());
				
				log.debug("Adding " + tempPhoto.toString());
				friendsPhotos.add(tempPhoto);
			} catch (Exception e) {
				log.error("Bad canidate to train: " + e.getMessage());
			}
			
			if (friendsPhotos.size() >= 20) {
				log.debug("GOT 20!!!!!!!");
				break;
			}
		}
		return friendsPhotos;
	}

	private List<Photo> getFriendsPhotos(Friend friend, int numOfPhotos) {
		Connection<Photo> photos = 
				fbClient.fetchConnection(friend.getFacebookId() + "/photos", 
						Photo.class, 
						Parameter.with("fields", "tags{id,x,y},images"),
							Parameter.with("limit", numOfPhotos));
		return photos.getData();
	}

	@Override
	public void getImages() {
		Connection<User> friends = 
				fbClient.fetchConnection(
						"me/friends", 
						User.class,
						Parameter.with("fields", "first_name,last_name,id"),
						Parameter.with("limit", 30));
		
		log.debug("List of Friends:");
		List<Friend> result = new ArrayList<Friend>();
		for(User friend: friends.getData()) {
			log.debug(friend.getFirstName() + " " + friend.getLastName());
			Friend person = new Friend();
			//Set all but photos
			person.setFirstName(friend.getFirstName());
			person.setLastName(friend.getLastName());
			person.setFacebookId(friend.getId());
			person.setFriendPhotos(getAndProcessFriendsPhotos(person, 200));
			
			result.add(person);
		}
		
		addToDatabase(result);
	}
}
