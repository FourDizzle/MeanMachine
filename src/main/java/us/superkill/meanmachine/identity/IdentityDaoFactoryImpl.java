package us.superkill.meanmachine.identity;

public class IdentityDaoFactoryImpl extends IdentityDaoFactory {

  @Override
  public IdentityDAO makeDao(String daoType) {
    IdentityDAO dao;
    if (daoType.equalsIgnoreCase("facebook")) {
    }
    return null;
  }

}
