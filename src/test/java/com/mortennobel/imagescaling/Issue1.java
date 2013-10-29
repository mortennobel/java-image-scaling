/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import junit.framework.TestCase;
import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.experimental.ResampleOpSingleThread;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * http://code.google.com/p/java-image-scaling/issues/detail?id=1
 *
 * Test image is downloaded from:
 * http://www.koskue.net/test.jpg
 */
public class Issue1 extends TestCase {
	public void testIssue() throws Exception {
		BufferedImage inputImage = ImageIO.read(getClass().getResourceAsStream("test.jpg"));
		new ResampleOp(51, 51).filter(inputImage, null);
	}

	public void testIssueSingleThread() throws Exception {
		BufferedImage inputImage = ImageIO.read(getClass().getResourceAsStream("test.jpg"));
		new ResampleOpSingleThread(51, 51).filter(inputImage, null);
	}


}
