package us.superkill.meanmachine.identity;

import us.superkill.meanmachine.processor.PhotoPreprocessor;

public abstract class IdentitySourceBuilder {

  protected final IdentitySource source;

  public IdentitySourceBuilder(IdentityDAO identityDao) {
    source = new IdentitySource(identityDao);
  }

  public abstract IdentitySource build();

  public abstract IdentitySourceBuilder addPreprocessor(PhotoPreprocessor preprocessor);

  public abstract IdentitySourceBuilder addPostFilter(PostFilter postFilter);
}
