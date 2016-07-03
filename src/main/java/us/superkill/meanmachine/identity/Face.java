package us.superkill.meanmachine.identity;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;

public class Face {

  private String fileName;

  private Mat image;
  private Integer width;
  private Integer height;

  private Integer faceLocX;
  private Integer faceLocY;

  private transient Rect face;
  
  private Rect rightEye;  
  private Point rightPupil;
  private Rect leftEye;
  private Point leftPupil;

  private Point tipOfNose;

  private Point mouthRightCorner;
  private Point mouthLeftCorner;

  public void saveImage(String directoryPath) throws NullPointerException {
    // throw exception with informative message in the event of an error
    if (this.image == null && this.fileName == null) {
      throw new NullPointerException("No image or filename specified to save");
    } else if (this.image == null) {
      throw new NullPointerException("No image specified to save");
    } else if (this.fileName == null) {
      throw new NullPointerException("No filename specified to save with");
    }
    imwrite(directoryPath + "/" + this.fileName, this.image);
  }

  public Mat getImage() {
    return image;
  }

  public void setImage(Mat image) {
    this.image = image;
    this.setWidth(image.cols());
    this.setHeight(image.rows());
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getFaceLocX() {
    return faceLocX;
  }

  public void setFaceLocX(Integer personLocX) {
    this.faceLocX = personLocX;
  }

  public Integer getFaceLocY() {
    return faceLocY;
  }

  public void setFaceLocY(Integer personLocY) {
    this.faceLocY = personLocY;
  }

  public Rect getFace() {
    return face;
  }

  public void setFace(Rect face) {
    this.face = face;
  }

  public Point getRightEyeCenter() {
    return rightPupil;
  }

  public void setRightEyeCenter(Point rightEyeCenter) {
    this.rightPupil = rightEyeCenter;
  }

  public Point getLeftEyeCenter() {
    return leftPupil;
  }

  public void setLeftEyeCenter(Point leftEyeCenter) {
    this.leftPupil = leftEyeCenter;
  }

  public Point getTipOfNose() {
    return tipOfNose;
  }

  public void setTipOfNose(Point tipOfNose) {
    this.tipOfNose = tipOfNose;
  }

  public Point getMouthRightCorner() {
    return mouthRightCorner;
  }

  public void setMouthRightCorner(Point mouthRightCorner) {
    this.mouthRightCorner = mouthRightCorner;
  }

  public Point getMouthLeftCorner() {
    return mouthLeftCorner;
  }

  public void setMouthLeftCorner(Point mouthLeftCorner) {
    this.mouthLeftCorner = mouthLeftCorner;
  }

  public Rect getRightEyeRegion() {
    return rightEye;
  }

  public void setRightEyeRegion(Rect rightEyeRegion) {
    this.rightEye = rightEyeRegion;
  }

  public Rect getLeftEyeRegion() {
    return leftEye;
  }

  public void setLeftEyeRegion(Rect leftEyeRegion) {
    this.leftEye = leftEyeRegion;
  }
}
