package us.superkill.meanmachine.geometry;

import static org.junit.Assert.*;

import org.bytedeco.javacpp.opencv_core.Point;
import org.junit.Test;

public class PointHelperTest {

  private Point origin = new Point(0, 0);
  
  @Test
  public void rotatePointBy90Deg() {
    Point rotatedPoint = PointHelper.rotatePoint(origin, new Point(1,0), 90.0);
    assertEquals(0, rotatedPoint.x());
    assertEquals(1, rotatedPoint.y());
  }
  
  @Test
  public void rotatePointBy180Deg() {
    Point rotatedPoint = PointHelper.rotatePoint(origin, new Point(1,0), 180.0);
    assertEquals(-1, rotatedPoint.x());
    assertEquals(0, rotatedPoint.y());
  }
  
  @Test
  public void rotatePointWithAngleGreaterThanZeroBy90Deg() {
    Point rotatedPoint = PointHelper.rotatePoint(origin, new Point(1,1), 90.0);
    assertEquals(-1, rotatedPoint.x());
    assertEquals(1, rotatedPoint.y());
  }
  
  @Test
  public void getAngleInDegreesTest() {
    Point pointAt45DegreeAngle = new Point(1, 1);
    double angle = PointHelper.getAngleInDegrees(origin, pointAt45DegreeAngle);
    assertEquals(45, angle, 0.4);
  }

  @Test
  public void getMidpointTest() {
    Point testPoint = new Point(2, 0);

    Point midpoint = PointHelper.getMidpoint(origin, testPoint);
    assertEquals(1, midpoint.x());
    assertEquals(0, midpoint.y());
  }

  @Test
  public void distanceBetweenPointsTest() {
    Point testPoint = new Point(2, 0);
    double distance = PointHelper.distBetweenPoints(origin, testPoint);

    assertEquals(2.0, distance, 0.01);
  }

  @Test
  public void rescalePointTest() {
    Point offset = new Point(1, 1);
    Point rescaled = PointHelper.rescalePoint(origin, offset);

    assertEquals(1, rescaled.x());
    assertEquals(1, rescaled.y());
  }
}
