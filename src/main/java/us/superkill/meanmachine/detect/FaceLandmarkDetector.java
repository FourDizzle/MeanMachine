package us.superkill.meanmachine.detect;

import us.superkill.meanmachine.identity.Face;

public interface FaceLandmarkDetector {

  public Face detect(Face face);

}
