package us.superkill.meanmachine.identity;

import java.util.List;

public abstract class IdentityDAO {

  public abstract List<Identity> getIdentities(int maxNumIdenities);

  public abstract List<Face> getIdentitysPhotos(Identity identity, int numberOfPhotos, int offset);

  public abstract List<Post> getIdentitysPosts(Identity identity, int numberOfPosts, int offset);
}
