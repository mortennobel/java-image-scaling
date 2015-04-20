# Introduction #

The ImageIO has some issues with sRGB on JRE 1.5. This has been filen in many bug-report such as [6372769](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6372769) and [4705399](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4705399). A user (mrsteve) has submitted the following workaround, used instead of ImageIO.read();


# Details #
```
       /**
	 * Workaround for sRGB color space bug on JRE 1.5
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4705399
	 *  
	 * @param source (inputstream or File)
	 * @return fixed buffered image
	 * @throws IOException
	 */
	public static BufferedImage readImage(Object source) throws IOException {
		ImageInputStream stream = ImageIO.createImageInputStream(source);
		ImageReader reader = ImageIO.getImageReaders(stream).next();
		reader.setInput(stream);
		ImageReadParam param =reader.getDefaultReadParam();

		ImageTypeSpecifier typeToUse = null;
		for (Iterator i = reader.getImageTypes(0);i.hasNext(); ) {
			ImageTypeSpecifier type = (ImageTypeSpecifier) i.next();
			if (type.getColorModel().getColorSpace().isCS_sRGB())
				typeToUse = type;
		}
		if (typeToUse!=null) param.setDestinationType(typeToUse);

		BufferedImage b = reader.read(0, param);

		reader.dispose();
		stream.close();
		return b;
	}
```