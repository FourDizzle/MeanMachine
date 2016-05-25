package us.superkill.mean_machine.facebook;

import us.superkill.mean_machine.identity.Identity;

/**
 * Allows FacebookDAO to use each person's Facebook ID
 * @author ncassiani
 *
 */
public class FacebookIdentity extends Identity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -440028697497964114L;
	private String facebookId;

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
}
