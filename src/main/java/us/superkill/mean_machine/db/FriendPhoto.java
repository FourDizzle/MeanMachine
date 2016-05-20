package us.superkill.mean_machine.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="friend_photo")
public class FriendPhoto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long friendPhotoId;
	private String facebookId;
//	private byte[] image;
	private int imageWidth;
	private int imageHeight;
	private String imageName;
	private String imageExtension;
	
//	@Column(name="friend_id_fk ", nullable=false)
//	protected long friendIdFk;
//	
//	@Column(name="friend_facebook_id_fk ", nullable=false)
//	protected String friendFacebookIdFk;


	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="friend_photo_id", nullable=false, unique=true, length=18)
	public long getFriendPhotoId() {
		return friendPhotoId;
	}

	public void setFriendPhotoId(long friendPhotoId) {
		this.friendPhotoId = friendPhotoId;
	}
	
	@Id
	@Column(name="facebook_id", nullable=false)
	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
//	@Column(name="image", nullable=false)
//	public byte[] getImage() {
//		return image;
//	}
//
//	public void setImage(byte[] image) {
//		this.image = image;
//	}
	
	@Column(name="image_width", nullable=false)
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	@Column(name="image_height", nullable=false)
	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	@Column(name="image_name", nullable=false)
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	@Column(name="image_extension", nullable=false)
	public String getImageExtension() {
		return imageExtension;
	}

	public void setImageExtension(String imageExtension) {
		this.imageExtension = imageExtension;
	}

//	public long getFriendIdFk() {
//		return friendIdFk;
//	}
//
//	public void setFriendIdFk(long friendIdFk) {
//		this.friendIdFk = friendIdFk;
//	}
//
//	public String getFriendFacebookIdFk() {
//		return friendFacebookIdFk;
//	}
//
//	public void setFriendFacebookIdFk(String friendFacebookIdFk) {
//		this.friendFacebookIdFk = friendFacebookIdFk;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ 
		@JoinColumn(name="friend_id_fk", referencedColumnName="friend_id"),
		@JoinColumn(name="friend_facebook_id_fk", referencedColumnName="facebook_id")
	})
	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}
	private Friend friend;
	
	@Override
	public String toString() {
		String output = "";
		output += "FriendPhoto {\n";
		output += "friendPhotoId: " + this.friendPhotoId + ",\n";
		output += "facebookId: " + this.facebookId + ",\n";
//		output += "image: " + this.image.length +" bytes,\n";
		output += "imageWidth: " + this.imageWidth + ",\n";
		output += "imageHeight: " + this.imageHeight + ",\n";
		output += "imageName: " + this.imageName + ",\n";
		output += "imageExtension: " + this.imageExtension + "\n";
		output += "}";
		return output;
	}
}
