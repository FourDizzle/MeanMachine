package us.superkill.eyelike;

import java.io.InputStream;
import java.util.Properties;

public class EyeLikeConfig {
  
  private static EyeLikeConfig instance = null;
  // Debugging
  protected final boolean kPlotVectorField;
  protected final boolean isDrawEyeRegionEnabled;

  // Size constants
  protected final int kEyePercentTop;
  protected final int kEyePercentSide;
  protected final int kEyePercentHeight;
  protected final int kEyePercentWidth;

  // Preprocessing
  protected final boolean kSmoothFaceImage;
  protected final float kSmoothFaceFactor;

  // Algorithm Parameters
  protected final int kFastEyeWidth;
  protected final int kWeightBlurSize;
  protected final boolean kEnableWeight;
  protected final float kWeightDivisor;
  protected final double kGradientThreshold;

  // Postprocessing
  protected final boolean kEnablePostProcess;
  protected final float kPostProcessThreshold;

  // Eye Corner
  protected final boolean kEnableEyeCorner;  
//  // Debugging
//  protected final static boolean kPlotVectorField = false;
//
//  // Size constants
//  protected final static int kEyePercentTop = 25;
//  protected final static int kEyePercentSide = 13;
//  protected final static int kEyePercentHeight = 30;
//  protected final static int kEyePercentWidth = 35;
//
//  // Preprocessing
//  protected final static boolean kSmoothFaceImage = false;
//  protected final static float kSmoothFaceFactor = 0.005f;
//
//  // Algorithm Parameters
//  protected final static int kFastEyeWidth = 50;
//  protected final static int kWeightBlurSize = 5;
//  protected final static boolean kEnableWeight = true;
//  protected final static float kWeightDivisor = 1.0f;
//  protected final static double kGradientThreshold = 50.0;
//
//  // Postprocessing
//  protected final static boolean kEnablePostProcess = true;
//  protected final static float kPostProcessThreshold = 0.97f;
//
//  // Eye Corner
//  protected final static boolean kEnableEyeCorner = false;
  
  protected EyeLikeConfig() {
    Properties p = loadProperties();
    kPlotVectorField = p.getProperty("kPlotVectorField").equalsIgnoreCase("true");
    isDrawEyeRegionEnabled = p.getProperty("isDrawEyeRegionEnabled").equalsIgnoreCase("true");
    kEyePercentTop = Integer.parseInt(p.getProperty("kEyePercentTop"));
    kEyePercentSide = Integer.  parseInt(p.getProperty("kEyePercentSide"));
    kEyePercentHeight = Integer.parseInt(p.getProperty("kEyePercentHeight"));
    kEyePercentWidth = Integer.parseInt(p.getProperty("kEyePercentWidth"));
    kSmoothFaceImage = p.getProperty("kSmoothFaceImage").equalsIgnoreCase("true");
    kSmoothFaceFactor = Float.parseFloat(p.getProperty("kSmoothFaceFactor"));
    kFastEyeWidth = Integer.parseInt(p.getProperty("kFastEyeWidth"));
    kWeightBlurSize = Integer.parseInt(p.getProperty("kWeightBlurSize"));
    kEnableWeight = p.getProperty("kEnableWeight").equalsIgnoreCase("true");
    kWeightDivisor = Float.parseFloat(p.getProperty("kWeightDivisor"));
    kGradientThreshold = Float.parseFloat(p.getProperty("kGradientThreshold"));
    kEnablePostProcess = p.getProperty("kEnablePostProcess").equalsIgnoreCase("true");
    kPostProcessThreshold = Float.parseFloat(p.getProperty("kPostProcessThreshold"));
    kEnableEyeCorner = p.getProperty("kEnableEyeCorner").equalsIgnoreCase("true");
  }
  
  public static EyeLikeConfig getInstance() {
    if(instance == null) {
      instance = new EyeLikeConfig();
    }
    return instance;
  }
  
  private Properties loadProperties() {
    Properties p = new Properties();
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("eyelike.properties");
    
    try {
      p.load(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return p;
  }
}
