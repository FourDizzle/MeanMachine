package us.superkill.meanmachine.facebook;

import javax.persistence.Transient;

import us.superkill.meanmachine.identity.Identity;

/**
 * Allows FacebookDAO to use each person's Facebook ID
 * 
 * @author ncassiani
 *
 */
public class FacebookIdentity extends Identity {

  /**
   * 
   */
  private static final long serialVersionUID = -440028697497964114L;
  private transient String facebookId;

  @Transient
  public String getFacebookId() {
    return facebookId;
  }

  @Transient
  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }
}
