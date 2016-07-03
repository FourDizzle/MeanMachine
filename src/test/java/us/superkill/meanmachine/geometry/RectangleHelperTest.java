package us.superkill.meanmachine.geometry;

import static org.junit.Assert.*;

import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.junit.Test;

public class RectangleHelperTest {
  @Test
  public void findCenterOfRectangle() {
    int topLeftX = 10;
    int topLeftY = 10;
    int bottomRightX = 60;
    int bottomRightY = 60;
    int expectedCenterX = 35;
    int expectedCenterY = 35;
    Rect r = new Rect(new Point(topLeftX, topLeftY), new Point(bottomRightX, bottomRightY));
    Point center = RectangleHelper.getCenterPoint(r);
    assertEquals(expectedCenterX, center.x());
    assertEquals(expectedCenterY, center.y());
  }
}
