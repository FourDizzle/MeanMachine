package us.superkill.mean_machine.identity;

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

/**
 * Represents a single person with their photos and posts. Also represents
 * "identity" table.
 * @author ncassiani
 *
 */
@Entity
@Table(name="identity")
public class Identity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3657508346528302293L;

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="person_id", nullable=false, unique=true, length=18)
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	private Long personId;
	
	@Column(name="first_name", nullable=false, length=255)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=255)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	private String lastName;
	
	@OneToMany(mappedBy="identity", cascade = CascadeType.ALL)
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	private List<Photo> photos;
	
	@OneToMany(mappedBy="identity", cascade = CascadeType.ALL)
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	private List<Post> posts;
}
