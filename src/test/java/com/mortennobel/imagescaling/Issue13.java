package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;

public class Issue13 extends TestCase {
	public void testScalePng() throws Exception {
		BufferedImage img = ImageIO.read(getClass().getResourceAsStream("issue13.png"));
		int width = img.getWidth()/2;
		int height = img.getHeight()/2;
		ResampleOp resampleOp = new ResampleOp(width, height);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledImage = resampleOp.filter(img, null);
		ImageIO.write(rescaledImage, "png", new File("Issue13_rescaled.png"));


		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		System.out.println("src "+rescaledImage.getColorModel().getTransferType());
		System.out.println("dest "+bi.getColorModel().getTransferType());
		ColorConvertOp colorConv = new ColorConvertOp(rescaledImage.getColorModel().getColorSpace(), bi.getColorModel().getColorSpace(), null);
		colorConv.filter(rescaledImage, bi);
		ImageIO.write(bi, "jpg", new File("Issue13_rescaled.jpg"));
	}
	public void testScalePngNoColorConv() throws Exception {
		BufferedImage img = ImageIO.read(getClass().getResourceAsStream("issue13.png"));
		int width = img.getWidth()/2;
		int height = img.getHeight()/2;
		ResampleOp resampleOp = new ResampleOp(width, height);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledImage = resampleOp.filter(img, null);
		ImageIO.write(rescaledImage, "jpg", new File("Issue13_rescaled_no_color_conv.jpg"));

	}

}
