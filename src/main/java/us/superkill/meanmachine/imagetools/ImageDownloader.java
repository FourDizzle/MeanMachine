package us.superkill.meanmachine.imagetools;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;

public class ImageDownloader {
  private static final Logger log = LogManager.getLogger(ImageDownloader.class);

  public static Mat downloadMat(String link, int width, int height) {
    log.debug("Downloading " + link);
    // TODO change file path or something
    String tempfile = "/Users/ncassiani/Projects/MeanMachine/tempimages/tempimage.jpg";
    try {
      downloadImageToFile(link, tempfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Mat img = imread(tempfile);
    deleteImage(tempfile);
    return img;
  }

  public static byte[] downloadImage(String link) {
    byte[] response = {};
    try {
      URL url = new URL(link);
      InputStream in = new BufferedInputStream(url.openStream());
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int n = 0;
      while (-1 != (n = in.read(buf))) {
        out.write(buf, 0, n);
      }

      out.close();
      in.close();

      response = out.toByteArray();
    } catch (Throwable ev) {

    }
    return response;
  }

  public static void downloadImageToFile(String imageUrl, String destinationFile)
      throws IOException {
    URL url = new URL(imageUrl);
    InputStream is = url.openStream();
    OutputStream os = new FileOutputStream(destinationFile);

    byte[] b = new byte[2048];
    int length;

    while ((length = is.read(b)) != -1) {
      os.write(b, 0, length);
    }

    is.close();
    os.close();
  }

  private static void deleteImage(String filename) {
    File file = new File(filename);
    try {
      Files.deleteIfExists(file.toPath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
