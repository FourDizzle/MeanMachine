package us.superkill.meanmachine.geometry;

import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

public class RectangleHelper {
  public static Point getCenterPoint(Rect r) {
    int centerX = (r.br().x() - r.tl().x()) / 2 + r.tl().x();
    int centerY = (r.br().y() - r.tl().y()) / 2 + r.tl().y();
    return new Point(centerX, centerY);
  }
}
