/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * This test should make sure that can be rescaled in multiple dimensions (without errors) and still look great.
 */
public class MultipleResizeTest extends JFrame {
	private BufferedImage sourceImage;
	private BufferedImage scaledImage;


	public MultipleResizeTest(final BufferedImage sourceImage)  {
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		this.sourceImage = this.scaledImage = sourceImage;

		Thread t = new Thread(){
			public void run() {
				while (true){
					int seconds = (int) (System.currentTimeMillis()/1000);
					final int MAX_WIDTH = 800;
					final int MAX_HEIGHT = 800;
					int width = (int) ((Math.sin(seconds/60.0)+1)*MAX_WIDTH);
					int height = (int) ((Math.sin(seconds/40.0)+1)*MAX_HEIGHT);
					width = Math.max(width, 3);
					height = Math.max(height,3);
					System.out.println("width = " + width);
					System.out.println("height = " + height);
					rescaleImage(width,height);
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	private void rescaleImage(int width, int height){
		ResampleOp resampleOp = new ResampleOp(width,height);
		scaledImage = resampleOp.filter(sourceImage,null);
		repaint();
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,0,getWidth(),getHeight());
		g.drawImage(scaledImage, 0,0,null);
	}

	public static void main(String[] args){
		try {
			BufferedImage image = ImageIO.read(MultipleResizeTest.class.getResource("/com/mortennobel/imagescaling/largeimage.jpg"));

			new MultipleResizeTest(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
