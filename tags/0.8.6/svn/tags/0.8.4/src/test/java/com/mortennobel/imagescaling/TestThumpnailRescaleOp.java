package com.mortennobel.imagescaling;

import com.mortennobel.imagescaling.ThumpnailRescaleOp;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TestThumpnailRescaleOp{
	@Test
	public void test1Sample(){
		BufferedImage bi = new BufferedImage(3,3,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,3,3);
		g2d.setColor(Color.red);
		g2d.fillRect(1,1,1,1);
		g2d.dispose();

		ThumpnailRescaleOp rescaleOp = new ThumpnailRescaleOp(1,1);
		rescaleOp.setSampling(ThumpnailRescaleOp.Sampling.S_1SAMPLE);
		BufferedImage scaledImage = rescaleOp.filter(bi, null);
		assertNotNull(scaledImage);
		assertEquals(scaledImage.getRGB(0,0), Color.red.getRGB());
	}

	@Test
	public void test1SampleLarge(){
		BufferedImage bi = new BufferedImage(7,7,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,bi.getWidth(),bi.getHeight());
		g2d.setColor(Color.red);
		g2d.fillRect(1,1,1,1);
		g2d.dispose();

		ThumpnailRescaleOp rescaleOp = new ThumpnailRescaleOp(2,2);
		rescaleOp.setSampling(ThumpnailRescaleOp.Sampling.S_1SAMPLE);
		BufferedImage scaledImage = rescaleOp.filter(bi, null);
		assertNotNull(scaledImage);
		for (int x=0;x<scaledImage.getWidth();x++){
			for (int y=0;y<scaledImage.getHeight();y++){
				Color color = new Color(scaledImage.getRGB(x,y));
				assertEquals(color.getBlue(),0);
				assertEquals(color.getGreen(),0);
				assertEquals(color.getRed(),x+y==0?255:0);
			}
		}
		new Color(255,255,255);
	}

	@Test
	public void test2x2RGSS(){
		BufferedImage bi = new BufferedImage(4,4,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,bi.getWidth(),bi.getHeight());
		g2d.setColor(Color.red);
		g2d.fillRect(2,0,1,1);
		g2d.dispose();

		ThumpnailRescaleOp rescaleOp = new ThumpnailRescaleOp(1,1);
		rescaleOp.setSampling(ThumpnailRescaleOp.Sampling.S_2X2_RGSS);
		BufferedImage scaledImage = rescaleOp.filter(bi, null);
		assertNotNull(scaledImage);
		Color color = new Color(scaledImage.getRGB(0,0));
		assertEquals(color.getBlue(),0);
		assertEquals(color.getGreen(),0);
		assertEquals(color.getRed(),255/4);
	}

	@Test
	public void test8Rooks(){
		BufferedImage bi = new BufferedImage(6,6,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0,0,bi.getWidth(),bi.getHeight());
		g2d.setColor(Color.red);
		g2d.fillRect(2,0,1,1);
		g2d.dispose();

		ThumpnailRescaleOp rescaleOp = new ThumpnailRescaleOp(1,1);
		rescaleOp.setSampling(ThumpnailRescaleOp.Sampling.S_8ROCKS);
		BufferedImage scaledImage = rescaleOp.filter(bi, null);
		assertNotNull(scaledImage);
		Color color = new Color(scaledImage.getRGB(0,0));
		assertEquals(color.getBlue(),0);
		assertEquals(color.getGreen(),0);
		assertEquals(color.getRed(),255/8);
	}

	static BufferedImage bufferedImage;

	@Before
	public void setup(){
		if (bufferedImage==null){
			try {
				bufferedImage = ImageIO.read(getClass().getResourceAsStream("largeimage.jpg"));
				System.out.println("Loaded image "+bufferedImage.getWidth()+"x"+bufferedImage.getHeight());
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
	}

	@Test
	public void largeImageTestMultistep(){
		// used for measure speed
		MultiStepRescaleOp multiStepRescale = new MultiStepRescaleOp(50,50);
		multiStepRescale.filter(bufferedImage, null);
	}

	@Test
	public void largeThumpRescale(){
		// used for measure speed
		ThumpnailRescaleOp multiStepRescale = new ThumpnailRescaleOp(50,50);
		BufferedImage bufferedImage2 = multiStepRescale.filter(bufferedImage, null);
	}

	@Test
	public void mediumThumpRescale(){
		BufferedImage bi = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(bufferedImage, 0,0,10,10, Color.white,  null);
		g2d.dispose();

		int destWidth = 7;
		int destHeight = 8;
		ThumpnailRescaleOp multiStepRescale = new ThumpnailRescaleOp(destWidth, destHeight);
		BufferedImage bufferedImage2 = multiStepRescale.filter(bi, null);
		assertEquals(bufferedImage2.getWidth(), destWidth);
		assertEquals(bufferedImage2.getHeight(), destHeight);

	}


}
