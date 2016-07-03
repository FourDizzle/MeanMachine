package us.superkill.meanmachine.detect;

import java.util.ArrayList;
import java.util.List;

import us.superkill.meanmachine.identity.Face;

public class CompositeFaceLandmarkDetector implements FaceLandmarkDetector {
  
  private List<FaceLandmarkDetector> childDetectors = new ArrayList<FaceLandmarkDetector>();
  
  @Override
  public Face detect(Face face) {
    if (!childDetectors.isEmpty()) {
      for(FaceLandmarkDetector d: childDetectors) {
        face = d.detect(face);
      }
    }
    return face;
  }
  
  public void addDetector(FaceLandmarkDetector d) {
    childDetectors.add(d);
  }
  
  public void removeDetector(FaceLandmarkDetector d) {
    childDetectors.remove(d);
  }

}
