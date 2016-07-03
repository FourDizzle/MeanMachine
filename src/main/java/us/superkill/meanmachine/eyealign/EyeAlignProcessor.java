package us.superkill.meanmachine.eyealign;

import us.superkill.meanmachine.detect.FaceLandmarkDetector;
import us.superkill.meanmachine.exceptions.PreprocessFailedException;
import us.superkill.meanmachine.identity.Face;
import us.superkill.meanmachine.imagetools.GeneralTransformer;
import us.superkill.meanmachine.processor.CompositePreprocessor;
import us.superkill.meanmachine.processor.PhotoPreprocessor;

public class EyeAlignProcessor implements PhotoPreprocessor {
  
  private FaceLandmarkDetector landmarkDetector;
  private PhotoPreprocessor processor;
  
  public EyeAlignProcessor(FaceLandmarkDetector landmarkDetector, 
                           int finalImageWidth,
                           int finalImageHeight) {
    
    CompositePreprocessor processor = new CompositePreprocessor();
    processor.add(new EyeLevelProcessor(new GeneralTransformer()));
    processor.add(new CropToEyePositionProcessor());
    processor.add(new ResizeProcessor(finalImageWidth, finalImageHeight));
    
    this.landmarkDetector = landmarkDetector;
    this.processor = processor;
  }

  @Override
  public Face process(Face face) throws PreprocessFailedException {
    face = this.landmarkDetector.detect(face);
    face = this.process(face);
    return face;
  }
  
  
}
