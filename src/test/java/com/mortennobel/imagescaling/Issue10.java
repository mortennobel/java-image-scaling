package com.mortennobel.imagescaling;

import junit.framework.TestCase;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Problem when scaling 1 channel (Grayscale) image 
 */
public class Issue10 extends TestCase {
	/**
	 * Test that the library can scale a grayscale image
	 * @throws Exception should not occur
	 */
	public void testIssue10() throws Exception {
		BufferedImage image2D = ImageIO.read(getClass().getResourceAsStream("test_issue10.jpg"));

		DimensionConstrain dc = DimensionConstrain.createAbsolutionDimension(50,50);

		ResampleOp  resampleOp = new ResampleOp (dc);
		BufferedImage rescaledTomato = resampleOp.filter(image2D, null);

	}

	/**
	 * Test that the library throws an exception if a destination image is not compatible with source img
	 * @throws Exception should not occur
	 */
	public void testIssue10Exception() throws Exception {
		RuntimeException re = null;
		try{
			BufferedImage image2D = ImageIO.read(getClass().getResourceAsStream("test_issue10.jpg"));

			DimensionConstrain dc = DimensionConstrain.createAbsolutionDimension(50,50);
			BufferedImage out = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB);
			ResampleOp  resampleOp = new ResampleOp (dc);
			BufferedImage rescaledTomato = resampleOp.filter(image2D, out);
		}
		catch (RuntimeException e){
			re = e;
		}
		assertNotNull("ISL should throw an exception since out image is not compatible", re);
	}
}
