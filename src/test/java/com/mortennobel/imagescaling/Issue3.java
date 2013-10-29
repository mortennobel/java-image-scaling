/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import junit.framework.TestCase;
import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp;

import java.awt.image.BufferedImage;

/**
 * Turned out to be a dublicate of Issue2
 */
public class Issue3 extends TestCase {
	public void testBug(){

		int srcWidth = 1920;
		int srcHeight = 1200;
		int dstWidth = (int) (srcWidth * 0.6);
		int dstHeight = (int) (srcHeight*0.6);
		ResampleOp resampleOp = new ResampleOp(dstWidth, dstHeight);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage rescaledImage = resampleOp.filter(new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_BGR), null);
		System.out.println("rescaledImage "+rescaledImage.getWidth()+"x"+rescaledImage.getHeight());
	}
}
