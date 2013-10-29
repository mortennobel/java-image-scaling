/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

/**
 *
 */
public class Issue2 extends TestCase {
	public void testBug(){

		int srcWidth = 100;
		int srcHeight = 100;
		int dstWidth = 2;
		int dstHeight = 2;
		ResampleOp resampleOp = new ResampleOp(dstWidth, dstHeight);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage rescaledImage = resampleOp.filter(new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_BGR), null);
	}
}
