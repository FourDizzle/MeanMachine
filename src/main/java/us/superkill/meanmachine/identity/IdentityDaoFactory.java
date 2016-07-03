package us.superkill.meanmachine.identity;

public abstract class IdentityDaoFactory {

  protected IdentityDaoFactory() {}

  public static IdentityDaoFactory getInstance() {
    IdentityDaoFactoryImpl instance = new IdentityDaoFactoryImpl();
    return instance;
  }

  public abstract IdentityDAO makeDao(String daoType);
}
