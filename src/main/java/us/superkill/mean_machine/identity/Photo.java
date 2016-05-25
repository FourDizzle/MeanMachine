package us.superkill.mean_machine.identity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Represents a single photo of an identity and the "photos" table.
 * @author ncassiani
 *
 */
@Entity
@Table(name="photos")
public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4003464372794344944L;

	private transient Mat image;
	
	@Transient
	public Mat getImage() {
		return image;
	}
	@Transient
	public void setImage(Mat image) {
		this.image = image;
		this.setWidth(image.width());
		this.setHeight(image.height());
	}
	
	/**
	 * Saves image to harddrive
	 * @param directoryPath Location where images will be save to
	 * @throws NullPointerException If no image or no filename is specified.
	 */
	@Transient
	public void saveImage(String directoryPath) throws NullPointerException {
		//throw exception with informative message in the event of an error
		if (this.image == null && this.fileName == null) {
			throw 
			new NullPointerException("No image or filename specified to save");
		} else if (this.image == null) {
			throw new NullPointerException("No image specified to save");
		} else if (this.fileName == null) {
			throw 
			new NullPointerException("No filename specified to save with");
		}
		Imgcodecs.imwrite(directoryPath + "/" + this.fileName, this.image);
	}
	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="photo_id", nullable=false, unique=true, length=18)
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	private Long photoId;
	
	@Column(name="filename", nullable=false)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	private String fileName;
	
	@Column(name="width", nullable=false)
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	private Integer width;
	
	@Column(name="height", nullable=false)
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	private Integer height;
	
	@Column(name="person_loc_x", nullable=false)
	public Integer getPersonLocX() {
		return personLocX;
	}
	public void setPersonLocX(Integer personLocX) {
		this.personLocX = personLocX;
	}
	private Integer personLocX;
	
	@Column(name="person_loc_y", nullable=false)
	public Integer getPersonLocY() {
		return personLocY;
	}
	public void setPersonLocY(Integer personLocY) {
		this.personLocY = personLocY;
	}
	private Integer personLocY;
	
}
