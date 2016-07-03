package us.superkill.meanmachine.facebook;

import us.superkill.meanmachine.identity.IdentityDAO;
import us.superkill.meanmachine.identity.IdentityDaoFactory;

public class FacebookDaoFactory extends IdentityDaoFactory {

  private final String apiKey;

  public FacebookDaoFactory(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public IdentityDAO makeDao(String daoType) {
    // TODO Auto-generated method stub
    return null;
  }


}
