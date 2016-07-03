package us.superkill.meanmachine.identity;

import us.superkill.meanmachine.processor.CompositePreprocessor;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class IdentitySourceBuilderImpl extends IdentitySourceBuilder {

  private CompositePreprocessor processor = new CompositePreprocessor();
  private CompositePostFilter filter = new CompositePostFilter();

  public IdentitySourceBuilderImpl(IdentityDAO identityDao) {
    super(identityDao);
  }

  @Override
  public IdentitySourceBuilder addPreprocessor(PhotoPreprocessor preprocessor) {
    processor.add(preprocessor);
    return this;
  }

  @Override
  public IdentitySourceBuilder addPostFilter(PostFilter postFilter) {
    filter.add(filter);
    return this;
  }

  @Override
  public IdentitySource build() {
    return this.source;
  }
}
