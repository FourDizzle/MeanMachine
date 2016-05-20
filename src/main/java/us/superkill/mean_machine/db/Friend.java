package us.superkill.mean_machine.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="friend")
public class Friend implements Serializable {
	
	/**
	 * TODO:need to change i think
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String facebookId;
	private String firstName;
	private String lastName;
	private List<FriendPhoto> friendPhotos;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="friend_id", nullable=false, unique=true, length=18)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Id
	@Column(name="facebook_id", nullable=false, length=128)
	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	@Column(name="first_name", nullable=false, length=255)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="last_name", nullable=false, length=255)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@OneToMany(mappedBy="friend", cascade = CascadeType.ALL)
	public List<FriendPhoto> getFriendPhotos() {
		return friendPhotos;
	}

	public void setFriendPhotos(List<FriendPhoto> friendPhotos) {
		this.friendPhotos = friendPhotos;
	}
	
	@Override
	public String toString() {
		String output = "";
		output += "Friend { \n";
		output += "id: " + this.id + ",\n";
		output += "facebook_id: " + this.facebookId + ",\n";
		output += "firstName: " + this.firstName + ",\n";
		output += "lastName: " + this.lastName + ",\n";
		output += "friendPhotos: [\n";
		for (FriendPhoto photo: this.friendPhotos) {
			output += photo.toString();
			output += ",\n"; 
		}
		output += "]";
		output += "}";
		return output;
	}
}
