/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
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
