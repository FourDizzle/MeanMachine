package us.superkill.meanmachine.detect;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

import us.superkill.meanmachine.exceptions.DetectorTargetNotFound;
import us.superkill.meanmachine.identity.Face;

public class TaggedFaceDetector extends TargetFaceDetector {

  private static final Logger log = LogManager.getLogger(TaggedFaceDetector.class);

  @Override
  public Rect detectTargetFace(Face photo, Rect[] faces) {
    Rect face = null;

    for (int i = 1; i < 10; i++) {
      List<Rect> possibleTargets = getAllFacesContainingPoint(photo, faces, i);
      if (possibleTargets.size() == 1) {
        face = possibleTargets.get(0);
        break;
      } else if (possibleTargets.size() > 1) {
        face = getSmallestFace(possibleTargets);
      } else if (possibleTargets.size() == 0) {
        break;
      }
    }
    
    return face;
  }

  private static List<Rect> getAllFacesContainingPoint(Face photo, Rect[] faces,
      int centerTolerance) {
    List<Rect> pointContainingFaces = new ArrayList<Rect>();
    Point target = new Point(photo.getFaceLocX(), photo.getFaceLocY());
    for (Rect face : faces) {
      Rect centerBox = calculateCenterRect(face, 3);
      if (centerBox.contains(target)) {
        pointContainingFaces.add(face);
      }
    }
    return pointContainingFaces;
  }

  private static Rect calculateCenterRect(Rect face, int scaleFactor) {
    int cBoxW = (int) Math.round(face.width() / scaleFactor);
    int cBoxH = (int) Math.round(face.height() / scaleFactor);

    return new Rect(face.x() - cBoxW, face.y() - cBoxH, cBoxW, cBoxH);
  }

  private static Rect getSmallestFace(List<Rect> faces) {
    Rect smallest = faces.remove(0);
    for (Rect face : faces) {
      if (face.sizeof() < smallest.sizeof()) {
        smallest = face;
      }
    }
    return smallest;
  }
}
