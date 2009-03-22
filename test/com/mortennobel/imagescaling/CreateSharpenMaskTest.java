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

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 */
public class CreateSharpenMaskTest {
	private BufferedImage image;
	private DimensionConstrain dimensionConstrain = DimensionConstrain.createMaxDimension(100,100);

	public CreateSharpenMaskTest() {
		setUp();
		Dimension dim = dimensionConstrain.getDimension(new Dimension(image.getWidth(),image.getHeight()));
		BufferedImage res = new BufferedImage(dim.width*5,dim.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = res.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for (ResampleOp.UnsharpenMask unsharpenMask : ResampleOp.UnsharpenMask.values()){
			saveSubImageType(scale(unsharpenMask),g,unsharpenMask);
		}
		g.dispose();
		saveImage(res, "SharpenMask.png");
	}


	private void saveSubImageType(BufferedImage source, Graphics2D dest, ResampleOp.UnsharpenMask unsharpenMash){
		int x = source.getWidth()*unsharpenMash.ordinal();
		int height = dest.getFontMetrics().getHeight();
		int margin = 5;
		dest.drawImage(source, x ,0,null);
		dest.setColor(new Color(1,1,1,0.5f));
		dest.fillRect(x,0,source.getWidth(), height+2*margin);
		dest.setColor(Color.black);
		dest.drawString(unsharpenMash.name(),x+2,height+margin);
	}

	private void saveImage(BufferedImage bufferedImage, String s) {
		File file = new File(System.getProperty("user.dir"),s);
		try{
			ImageIO.write(bufferedImage, "png", file);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private BufferedImage scale(ResampleOp.UnsharpenMask mask) {
		ResampleOp op = new ResampleOp(dimensionConstrain);
		op.setUnsharpenMask(mask);
		return op.filter(image, null);
	}

	protected void setUp() {
		try {
			image = ImageIO.read(getClass().getResource("/com/mortennobel/imagescaling/flower.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new CreateSharpenMaskTest();
	}
}
