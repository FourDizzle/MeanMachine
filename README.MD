#The Mean Machine

## About This Project
This a project designed to make a computer behave in non-useful human manner. 
Specifically, this is designed to use computer vision and natural language
processing in conjunction with Facebook to generate a personalize insult for
the person standing in front of a webcam.

## Requirements
- MySQL (I used 5.7.12)
- OpenCV 3.1.0
- opencv_contrib 3.1.0
- Apache Maven
- Java JDK 1.8

## Build Instructions
1. Follow all instructions to build OpenCV 3.1.0 with opencv_contrib 3.1.0 to 
   generate the jar opencv-310.jar
2. In the directory where that jar resides run:
```
mvn install:install-file \
    -Dfile=opencv-310.jar  \
    -DgroupId=org.opencv  \
    -DartifactId=opencv-java  \
    -Dversion=3.1.0  \
    -Dpackaging=jar
```
3. In the root directory of this project (mean-machine) run:
```
mvn clean package
```
