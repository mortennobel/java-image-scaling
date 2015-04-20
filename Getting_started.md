# Introduction #

This will guide you through how to rescale images using this library.


## A simple rescale ##

Lets say that you have a BufferedImage instance called tomato that should be rescaled to 100x200. If you want a high quality rescale, you should choose the ResampleOp class:

```
ResampleOp  resampleOp = new ResampleOp (100,200);
BufferedImage rescaledTomato = resampleOp.filter(tomato, null);
```

Tip: You might need to increase your maximum heap. This can be done using the -Xmx parameter, such as: java -Xmx512m MyJavaClass

## Using Unsharpen Filter ##
Let's improve the quality a bit more adding a unsharpen filter to the final image. This will remove some of blur, that a rescale operation creates.
```
ResampleOp  resampleOp = new ResampleOp (100,200);
resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
BufferedImage rescaledTomato = resampleOp.filter(tomato, null);
```


## Adding a listener ##
If your tomato image was very large, this might take some time. The following code add a change listener so the user can see that the computer is actually is working.
```
ResampleOp  resampleOp = new ResampleOp (100,200);
resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
resampleOp.addProgressListener(new ProgressListener() {
  public void notifyProgress(float fraction) {
    System.out.printf("Still working - %f percent %n",fraction*100);
  }
});
BufferedImage rescaledTomato = resampleOp.filter(tomato, null);
```