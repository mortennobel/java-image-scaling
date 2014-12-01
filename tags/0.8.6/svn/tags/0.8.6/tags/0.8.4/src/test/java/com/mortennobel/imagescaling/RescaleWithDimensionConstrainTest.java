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
