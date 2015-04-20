# Introduction #

A description of why this library should be used.

# Details #

The core Java libraries has been capable of scaling images since the very first release of Java. In general there is two ways of scaling a image object:
  * Image.getScaledInstance() (You hint the scaling quality as a parameter to the method).
  * Scaling by creating a new Image object and draw a scaled version of the image to that (Graphics2D let you set the interpolater method used)

The Image.getScaledInstance() approach creates a quite good scaling quality but is terrible slow. The second approach is fast but to create good looking result, you scaling must be between factor 2.0 and 0.5. (If your scaling is outside this range, you should run the algorithm multiple times - See [Chris Campbell's blog The Perils of Image.getScaledInstance()](http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html)).

This library improve both methods. First of all the library has a abstract class AdvancedResizeOp that add the following features:
  * You can specify you target size using a DimensionConstrain object. This helps you with maintaining the aspect radio in your resizing.
  * You can add a progress changed listener to get feedback of how your resizing calculation is doing
  * You can run a unsharpen filter when reducing the size of the image. This will improve the quality of the rescaled image. (This depends on the third-party library [JHLabs: Java Image Filters](http://www.jhlabs.com/ip/filters/index.html)).

## The MultiStepRescaleOp class ##

This class is based on Chris Cambell's work, so it generally just encapsulate the algorithm Chris described.
  * Low memory usage

## The ResampleOp class ##

  * Add multicore support
  * Choose between different interpolator algorithms (such as BiCubic and Lanczos3).
  * Very high memory usage

## Quality ##

The following shows you the result of a image scaled using this library.

<img src='http://java-image-scaling.googlecode.com/files/ImageScalingResult.png'>