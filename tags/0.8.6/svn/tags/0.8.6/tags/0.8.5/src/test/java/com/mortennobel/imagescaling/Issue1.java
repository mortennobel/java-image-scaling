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
