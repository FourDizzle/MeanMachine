package us.superkill.mean_machine.train;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.INTER_NEAREST;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

import java.nio.IntBuffer;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.hibernate.Query;
import org.hibernate.Session;

import us.superkill.mean_machine.db.FriendPhoto;
import us.superkill.mean_machine.db.HibernateUtil;

public class Trainer {
	
	private static List<FriendPhoto> getAllPhotos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from FriendPhoto");
		List<FriendPhoto> list = query.list();
		session.close();
		return list;
	}
	
	public static FaceRecognizer trainFaceRecognizer() {
		List<FriendPhoto> photos = getAllPhotos();
		MatVector images = new MatVector(photos.size());
        Mat labels = new Mat(photos.size(), 1, CV_32SC1);
		IntBuffer labelsBuf = labels.getIntBuffer();
		
		int counter = 0;
        
		Size uniformSize = imread(photos.get(0).getImageName()).size();
		
		for (FriendPhoto photo: photos) {
			Mat img = imread(photo.getImageName());
			
			resize(img, img, uniformSize, 0, 0, INTER_NEAREST);
			
			images.put(counter, img);
			labelsBuf.put(counter, (int)photo.getFriend().getId());
			
			counter++;
		}
		
		FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
		faceRecognizer.train(images, labels);
//		
//		resize(testImg, testImg, uniformSize, 0, 0, INTER_NEAREST);
//		
//		System.out.println(faceRecognizer.predict(testImg));
		
		return faceRecognizer;
	}
}
