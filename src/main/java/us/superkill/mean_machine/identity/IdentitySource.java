package us.superkill.mean_machine.identity;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public class IdentitySource {
	
	private static final Logger log = 
			LogManager.getLogger(IdentitySource.class);
	
	private final IdentityDAO identitySource;
	private final int maxNumIdentities;
	
	private final PhotoFormatter photoFormatter;
	private final boolean shouldFormatPhotoWhileDownloading;
	private final int photosPerPerson;
	
	private final PostFilter postFilter;
	private final boolean shouldFilterPostsWhileDownloading;
	private final int postsPerPerson;
	
	IdentitySource(IdentityDownloaderBuilder builder) {
		this.identitySource = builder.identitySource;
		this.maxNumIdentities = builder.maxNumIdentities;
		
		this.photoFormatter = builder.photoFormatter;
		this.photosPerPerson = builder.photosPerPerson;
		this.shouldFormatPhotoWhileDownloading = 
				builder.shouldFormatPhotoWhileDownloading;
		
		this.postFilter = builder.postFilter;
		this.postsPerPerson = builder.postsPerPerson;
		this.shouldFilterPostsWhileDownloading = 
				builder.shouldFilterPostsWhileDownloading;
	}
	
	public List<? extends Identity> getIdentities() {
		List<? extends Identity> identities = 
				this.identitySource.getListOfIdentities(maxNumIdentities);
		
		for (Identity identity: identities) {
			getPhotosOfPersonToTrainIdentity(identity);
			getPosts(identity);
		}
		return identities;
	}
	
	private Identity getPhotosOfPersonToTrainIdentity(Identity identity) {
		List<Photo> photos = new ArrayList<Photo>();
		int numberOfPhotosPreviouslyDownloaded = 0;
		while (photos.size() < photosPerPerson) {
			//calculate how many more photos are needed
			int numOfPhotosToTry = photosPerPerson - photos.size();
			//download a batch of potentials
			List<? extends Photo> potentialPhotos = 
					this.identitySource.downloadIdentitysPhotos(identity,
							numOfPhotosToTry,
							numberOfPhotosPreviouslyDownloaded);
			//update numberOfPhotosPreviouslyDownloaded
			numberOfPhotosPreviouslyDownloaded += numOfPhotosToTry;
			//if appropriate format each potential
			if (shouldFormatPhotoWhileDownloading) {
				potentialPhotos = formatBatchOfPhotos(potentialPhotos);
			}
			//Add them to the list
			photos.addAll(potentialPhotos);
		}
		
		identity.setPhotos(photos);
		return identity;
	}
	
	private List<Photo> formatBatchOfPhotos(List<? extends Photo> potentialPhotos) {
		ArrayList<Photo> formattedPhotos = new ArrayList<Photo>();
		for (Photo photo: potentialPhotos) {
			//if photo can be formatted add it to the list
			try {
				formattedPhotos.add(photoFormatter.format(photo));
			} catch (FormatterTargetNotFound e) {
				log.debug("Bad Photo for Training: " + e.getMessage());
			}
		}
		return formattedPhotos;
	}
	
	private List<Post> getPosts(Identity identity) {
		List<Post> posts = new ArrayList<Post>();
		int numberOfPostsPreviouslyDownloaded = 0;
		while (posts.size() < postsPerPerson) {
			int numOfPostsToTry = postsPerPerson - posts.size();
			//download a batch of potentials
			List<? extends Post> potentialPosts = 
					this.identitySource.downloadIdentitysPosts(identity,
							numOfPostsToTry,
							numberOfPostsPreviouslyDownloaded);
			//update numberOfPhotosPreviouslyDownloaded
			numberOfPostsPreviouslyDownloaded += numOfPostsToTry;
			//if appropriate format each potential
			if (shouldFilterPostsWhileDownloading) {
				potentialPosts = postFilter.filter(potentialPosts);
			}
			//Add them to the list
			posts.addAll(potentialPosts);
		}
		return posts;
	}
	
	public class IdentityDownloaderBuilder {
		
		private final IdentityDAO identitySource;
		private int maxNumIdentities = 100;
		
		private PhotoFormatter photoFormatter;
		private boolean shouldFormatPhotoWhileDownloading = false;
		private int photosPerPerson = 20;
		
		private PostFilter postFilter;
		private boolean shouldFilterPostsWhileDownloading = false;
		private int postsPerPerson = 100;
		
		public IdentityDownloaderBuilder(IdentityDAO identitySource) {
			this.identitySource = identitySource;
		}
		
		public IdentityDownloaderBuilder maxNumIdentities(int maxNumIdentities) {
			this.maxNumIdentities = maxNumIdentities;
			return this;
		}
		
		public IdentityDownloaderBuilder 
		photoFormatter(PhotoFormatter photoFormatter) {
			this.photoFormatter = photoFormatter;
			this.shouldFormatPhotoWhileDownloading = true;
			return this;
		}
		
		public IdentityDownloaderBuilder postFilter(PostFilter postFilter) {
			this.postFilter = postFilter;
			this.shouldFilterPostsWhileDownloading = true;
			return this;
		}
		
		public IdentityDownloaderBuilder photosPerPerson(int photosPerPerson) {
			this.photosPerPerson = photosPerPerson;
			return this;
		}
		
		public IdentityDownloaderBuilder postsPerPerson(int postsPerPerson) {
			this.postsPerPerson = postsPerPerson;
			return this;
		}
		
		public IdentitySource build() throws IllegalArgumentException {
			IdentitySource dl = new IdentitySource(this);
			validateDownloader();
			return dl;
		}
		
		public void validateDownloader() throws IllegalArgumentException {
		}
	}
}
