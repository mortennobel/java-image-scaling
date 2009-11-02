package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Problem when scaling 1 channel (Grayscale) image 
 */
public class Issue10 extends TestCase {
	public void testIssue10() throws Exception {
		BufferedImage image2D = ImageIO.read(getClass().getResourceAsStream("test_issue10.jpg"));

			DimensionConstrain dc = DimensionConstrain.createMaxDimensionNoOrientation(50,50);

			ResampleOp  resampleOp = new ResampleOp (dc);
			BufferedImage rescaledTomato = resampleOp.filter(image2D, null);

	}
}
