package us.superkill.mean_machine.identity;

import java.util.List;

public abstract class IdentityDAO {

	protected abstract List<? extends Identity> getListOfIdentities(int maxNumIdenities);
	
	protected abstract List<? extends Photo> 
	downloadIdentitysPhotos(Identity identity, int numberOfPhotos, int offset);
	
	protected abstract List<? extends Post> 
	downloadIdentitysPosts(Identity identity, int numberOfPosts, int offset);
}
