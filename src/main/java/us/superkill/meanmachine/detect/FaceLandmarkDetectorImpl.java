package us.superkill.meanmachine.detect;

import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.identity.Face;

public class FaceLandmarkDetectorImpl implements FaceLandmarkDetector {
  
  private FaceFinder faceFinder;
  private TargetFaceDetector faceDetector;
  private EyeDetector eyeDetector;
  private PupilDetector pupilDetector;
  

  public FaceLandmarkDetectorImpl(FaceFinder faceFinder, TargetFaceDetector faceDetector,
      EyeDetector eyeDetector, PupilDetector pupilDetector) {
    super();
    this.faceFinder = faceFinder;
    this.faceDetector = faceDetector;
    this.eyeDetector = eyeDetector;
    this.pupilDetector = pupilDetector;
  }


  @Override
  public Face detect(Face face) {
    Rect[] faces = faceFinder.detectFaces(face.getImage());
    faceDetector.detectTargetFace(face, faces);
    return null;
  }
  
  
}
