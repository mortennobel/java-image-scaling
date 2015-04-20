# Details #
Version 0.8.6 Released (29-10-13)
  * Change license to BSD 3-Clause License
Version 0.8.5 Released (21-08-10)
  * Fix minor rounding error ([Issue 16](https://code.google.com/p/java-image-scaling/issues/detail?id=16))
  * Change DimensionConstrain constructor from private to protected ([Issue 19](https://code.google.com/p/java-image-scaling/issues/detail?id=19))
  * Project is now exist in maven repositories ([Issue 15](https://code.google.com/p/java-image-scaling/issues/detail?id=15))
Version 0.8.4 Released (28-12-09)
  * Fix color conversion bug ([Issue 13](https://code.google.com/p/java-image-scaling/issues/detail?id=13))
  * Maven support
Version 0.8.3 Released (03-10-09)
  * Fix spelling error in class name (ThumpnailRescaleOp -> ThumbnailRescaleOp)
Version 0.8.2 Released (02-10-09)
  * Added ThumbnailRescaleOp, for generating thumbnails fast
  * Fixed bug with scaling grayscale images
  * Add check if destination image is compatible with source image
Version 0.8.1 Released (20-08-09)
  * Code compiled for JRE 1.5
Version 0.8.0 Released (15-08-09)
  * Performance improvements on ResampleOp. Performance gain about 20-30% (!)
Version 0.7.2 Released (09-06-09)
  * Fixed ArrayIndexOutOfBoundsException ([Issue #1](https://code.google.com/p/java-image-scaling/issues/detail?id=#1))
Version 0.7.1 Released (22-03-09)
  * Changed unsharpedmask from a boolean to a enum
Version 0.7 Released (14-03-09)
  * Introduced DimensionConstrain class, to easy the image scaling calculation
  * Set ResampleFilter implementation classes to package only. They should now be referred to using the ResampleFilters class.
  * Improved java doc
  * Added experimental ResampleOpSingleThread

Version 0.6 Released (25-01-09)
  * Improved performance
  * Added unsharpen mask option (Using Java Image Filters from http://www.jhlabs.com/)