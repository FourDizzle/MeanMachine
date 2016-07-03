package us.superkill.eyelike;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class EyeLikeConfigTest {
  
  private EyeLikeConfig config;
  
  @Before
  public void setup() {
    config = EyeLikeConfig.getInstance();
  }
  
  @Test
  public void getConfigInstanceTest() {
    assertNotNull(config);
  }
  
  @Test
  public void DetectConfigValuesMatchConfigFile() {
    Properties p = new Properties();
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("eyelike.properties");
    
    try {
      p.load(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    boolean kPlotVectorField = p.getProperty("kPlotVectorField").equalsIgnoreCase("true");
    boolean isDrawEyeRegionEnabled = p.getProperty("isDrawEyeRegionEnabled").equalsIgnoreCase("true");
    int kEyePercentTop = Integer.parseInt(p.getProperty("kEyePercentTop"));
    int kEyePercentSide = Integer.  parseInt(p.getProperty("kEyePercentSide"));
    int kEyePercentHeight = Integer.parseInt(p.getProperty("kEyePercentHeight"));
    int kEyePercentWidth = Integer.parseInt(p.getProperty("kEyePercentWidth"));
    boolean kSmoothFaceImage = p.getProperty("kSmoothFaceImage").equalsIgnoreCase("true");
    float kSmoothFaceFactor = Float.parseFloat(p.getProperty("kSmoothFaceFactor"));
    int kFastEyeWidth = Integer.parseInt(p.getProperty("kFastEyeWidth"));
    int kWeightBlurSize = Integer.parseInt(p.getProperty("kWeightBlurSize"));
    boolean kEnableWeight = p.getProperty("kEnableWeight").equalsIgnoreCase("true");
    float kWeightDivisor = Float.parseFloat(p.getProperty("kWeightDivisor"));
    double kGradientThreshold = Float.parseFloat(p.getProperty("kGradientThreshold"));
    boolean kEnablePostProcess = p.getProperty("kEnablePostProcess").equalsIgnoreCase("true");
    float kPostProcessThreshold = Float.parseFloat(p.getProperty("kPostProcessThreshold"));
    boolean kEnableEyeCorner = p.getProperty("kEnableEyeCorner").equalsIgnoreCase("true");
    
    assertEquals(kPlotVectorField, config.kPlotVectorField);
    assertEquals(isDrawEyeRegionEnabled, config.isDrawEyeRegionEnabled);
    assertEquals(kEyePercentTop, config.kEyePercentTop);
    assertEquals(kEyePercentSide, config.kEyePercentSide);
    assertEquals(kEyePercentHeight, config.kEyePercentHeight);
    assertEquals(kEyePercentWidth, config.kEyePercentWidth);
    assertEquals(kSmoothFaceImage, config.kSmoothFaceImage);
    assertEquals(kSmoothFaceFactor, config.kSmoothFaceFactor, .001f);
    assertEquals(kFastEyeWidth, config.kFastEyeWidth);
    assertEquals(kWeightBlurSize, config.kWeightBlurSize);
    assertEquals(kEnableWeight, config.kEnableWeight);
    assertEquals(kWeightDivisor, config.kWeightDivisor, .001f);
    assertEquals(kGradientThreshold, config.kGradientThreshold, .001f);
    assertEquals(kEnablePostProcess, config.kEnablePostProcess);
    assertEquals(kPostProcessThreshold, config.kPostProcessThreshold, .001f);
    assertEquals(kEnableEyeCorner, config.kEnableEyeCorner);
  }
}
