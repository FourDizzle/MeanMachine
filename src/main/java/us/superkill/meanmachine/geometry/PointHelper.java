package us.superkill.meanmachine.geometry;

import org.bytedeco.javacpp.opencv_core.Point;

public class PointHelper {

  public static Point rescalePoint(Point p, Point offset) {
    return new Point(p.x() + offset.x(), p.y() + offset.y());
  }

  public static double distBetweenPoints(Point a, Point b) {
    return Math.sqrt(Math.pow((a.x() - b.x()), 2) + Math.pow((a.y() - b.y()), 2));
  }

  public static Point getMidpoint(Point a, Point b) {
    return new Point(a.x() / 2 + b.x() / 2, a.y() / 2 + b.y() / 2);
  }

  public static double getAngleInDegrees(Point a, Point b) {
    double rad = Math.atan((a.y() - b.y()) / (a.x() - b.x()));
    return Math.toDegrees(rad);
  }

  public static Point rotatePoint(Point pivot, Point toBeRotated, double angle) {
    angle = PointHelper.getAngleInDegrees(pivot, toBeRotated) + angle;
    double rad = angle * Math.PI / 180;
    double hypotenuse = PointHelper.distBetweenPoints(pivot, toBeRotated);
    int y = (int) Math.round(hypotenuse * Math.sin(rad));
    int x = (int) Math.round(hypotenuse * Math.cos(rad));
    return new Point(x, y);
  }
}
