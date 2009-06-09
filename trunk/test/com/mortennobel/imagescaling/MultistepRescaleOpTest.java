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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;

import com.mortennobel.imagescaling.experimental.ImprovedMultistepRescaleOp;

public class MultistepRescaleOpTest extends TestCase {

	protected BufferedImage image;

	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
	protected void setUp() throws Exception {
		image = ImageIO.read(getClass().getResource("/com/mortennobel/imagescaling/largeimage-thump.jpg"));
	}

	public void testCorrect(){
		BufferedImage reference = new MultiStepRescaleOp(image.getWidth()/4, image.getHeight()/4,RenderingHints.VALUE_INTERPOLATION_BILINEAR).filter(image, null);
		BufferedImage result = new ImprovedMultistepRescaleOp(image.getWidth()/4, image.getHeight()/4,RenderingHints.VALUE_INTERPOLATION_BILINEAR).filter(image, null);
		assertEquals(result, reference);
	}

	public void assertEquals(BufferedImage image1, BufferedImage image2){
		assertSame(image1.getWidth(), image2.getWidth());
		assertSame(image1.getHeight(), image2.getHeight());

		int pixels = image1.getWidth()*image2.getHeight();
		int errors = 0;
		for (int x = 0;x<image1.getWidth();x++){
			for (int y=0;y<image1.getHeight();y++){
				int rgb = image1.getRGB(x,y);
				int rgb2 = image2.getRGB(x,y);
				if (rgb!=rgb2){
					errors++;
				}
			}
		}
		assertTrue("Pixels : "+pixels+" errors: "+errors, errors==0);
	}
}
