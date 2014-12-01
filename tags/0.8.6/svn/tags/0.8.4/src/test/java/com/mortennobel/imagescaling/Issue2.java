/*
 * Copyright 2009, Morten Nobel-Joergensen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
