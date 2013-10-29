/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.awt.*;


public class RescaleWithDimensionConstrainTest {
	public int getAverageRGB(BufferedImage image){
		long r = 0;
		long g = 0;
		long b = 0;
		int count = 0;
		for (int x = 0;x<image.getWidth();x++){
			for (int y=0;y<image.getHeight();y++){
				int rgb = image.getRGB(x,y);
				Color c = new Color(rgb);
				r += c.getRed();
				g += c.getGreen();
				b += c.getBlue();
				count++;
			}
		}
		float resR = r/(float)count;
		float resG = g/(float)count;
		float resB = b/(float)count;
		return new Color((int)resR, (int)resG, (int)resB).getRGB();
	}

	@Test
	public void testScaleWhiteImage(){
		BufferedImage bi = new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setPaint(new GradientPaint(0,0,Color.white, 0,50, Color.black));
		g.fillRect(0,0,50,50);
		g.dispose();

	}
}
