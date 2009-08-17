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
package com.mortennobel.imagescaling.issues;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp;

/**
 * Test case on bugreport and code by Murugesh Ramachandran 
 */
public class Issue5 extends TestCase {
	public void testSRGB() throws Exception{
		try {  
			// Read the original image from the Server Location

			String[] fileTypes = {"jpg","png","JPG","GIF"};
			boolean bFlag = false;

			//File inputFile = new File(imageLoc);
			bFlag = false;

			int fixlength = 1600;
			int calcHeight = 0;
			int calcWidth = 0;

			BufferedImage bufferedImage = ImageIO.read(new URL("http://lh4.ggpht.com/_9ZodCKpdHRc/SlduqK90ZpI/AAAAAAAAAXU/qQb8G549oy4/pic_010b%2817MB%29.jpg").openStream());
			// Calculate the new Height if not specified
			if(bufferedImage.getHeight() > bufferedImage.getWidth()){
				if(bufferedImage.getHeight() > 1600){
					calcWidth = fixlength * bufferedImage.getWidth() / bufferedImage.getHeight();
					calcHeight = 1600;
					bFlag = true;
				}
				else{
					calcWidth = bufferedImage.getWidth();
					calcHeight  = bufferedImage.getHeight();
				}
			}
			else{
				if( bufferedImage.getWidth() > 1600){
					calcHeight = fixlength * bufferedImage.getHeight() / bufferedImage.getWidth();
					calcWidth = 1600;
					bFlag = true;
				}
				else{
					calcWidth = bufferedImage.getWidth();
					calcHeight  = bufferedImage.getHeight();
				}
			}

			System.out.println(calcHeight);
			BufferedImage rescaledImage = createResizedCopy(bufferedImage, calcWidth, calcHeight);
			// ImageIO.write(rescaledImage, "jpg", new File("/Users/morten/Programmering/java/ImageScaling/test/com/mortennobel/imagescaling/issues/rescaled.jpg"));
			int res = JOptionPane.showConfirmDialog(null, "Do you see a scaled image?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(rescaledImage));
			assertEquals(JOptionPane.YES_OPTION ,res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public static BufferedImage createResizedCopy(BufferedImage originalImage, int scaledWidth, int scaledHeight) {
		ResampleOp  resampleOp = new ResampleOp (scaledWidth,scaledHeight);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.VerySharp);
		BufferedImage rescaledTomato = resampleOp.filter(originalImage, null);
		return rescaledTomato;
	}

}
