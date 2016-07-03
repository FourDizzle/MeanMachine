package us.superkill.meanmachine.facebook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

public class FacebookConnector {
  private static final Logger log = LogManager.getLogger(FacebookConnector.class);

  private final FacebookClient fbClient;

  private String accessToken;

  public FacebookConnector(String accessToken) {
    log.debug("Connecting to Facebook...");
    fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_3);
    log.debug("Connected!");
  }

  public FacebookClient getFbClient() {
    log.trace("Fetching Facebook client.");
    // TODO: add check that client is still valid
    return fbClient;
  }

  public void setAccessToken(String token) {
    log.trace("Setting access token to: " + token);
    this.accessToken = token;
  }

  public String getAccessToken() {
    String token = "";
    if (this.accessToken != null) {
      token = this.accessToken;
    }
    return token;
  }
}
