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
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;

public class CorrectnessTest extends JFrame {
	public CorrectnessTest(){
		setUp();
		getContentPane().setLayout(new GridLayout(0,6));
		addContent();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private BufferedImage image;

	public int getNumberOfThreads(){
		return 1;
	}

	protected void setUp() {
		try {
			image = ImageIO.read(getClass().getResource("/com/mortennobel/imagescaling/largeimage.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doRescale(ResampleFilter filter) {
		ResampleOp  resampleOp = new ResampleOp (200,200);
		resampleOp.setFilter(filter);
		//resampleOp.setNumberOfThreads(1);
		resampleOp.setUnsharpenFilterAfterReduce(true);

		BufferedImage image = resampleOp.filter(this.image, null);
		addImage(filter.getName()+" Unsharpen", image);
		resampleOp.addProgressListener(new ProgressListener() {
			public void notifyProgress(float fraction) {
				System.out.printf("Still working - %f percent %n",fraction*100);
			}
		});
		resampleOp.setUnsharpenFilterAfterReduce(false);
		image = resampleOp.filter(this.image, null);
		addImage(filter.getName(), image);

	}

	private void doMultiStepRescaleOp(String name, Object renderingHint){
		MultiStepRescaleOp mro = new MultiStepRescaleOp(200,200, renderingHint);
		mro.setUnsharpenFilterAfterReduce(true);
		BufferedImage res = mro.filter(image, null);
		addImage(name+" Unsharpen", res);

		mro.setUnsharpenFilterAfterReduce(false);
		res = mro.filter(image, null);
		addImage(name, res);
	}

	private void addImage(String name, BufferedImage image) {
		System.out.println("Adding "+name);
		JLabel label = new JLabel( new ImageIcon(image), JLabel.CENTER);
		label.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY),name));
		getContentPane().add(label);
	}

	public void addContent()  {
		doRescale(new BellFilter());
		doRescale(new BiCubicFilter());
		doRescale(new BiCubicHighFreqResponse());
		doRescale(new BoxFilter());
		doRescale(new BSplineFilter());
		doRescale(new HermiteFilter());
		doRescale(new Lanczos3Filter());
		doRescale(new MitchellFilter());
		doRescale(new TriangleFilter());

		doMultiStepRescaleOp("BILINEAR", RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		doMultiStepRescaleOp("BICUBIC", RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	}

	public static void main(String[] args) {
		new CorrectnessTest();
	}
}
