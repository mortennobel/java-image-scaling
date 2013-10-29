/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

 

import com.mortennobel.imagescaling.experimental.ResampleOpSingleThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CorrectnessTest extends JFrame implements ActionListener {
	private JPanel contentPanel = new JPanel(new GridLayout(0,6));
	public CorrectnessTest(){
		setUp();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel,BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton save = new JButton("Save");
		save.addActionListener(this);
		buttonPanel.add(save);
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);

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
		ResampleOpSingleThread resampleOp = new ResampleOpSingleThread (200,200);
		resampleOp.setFilter(filter);
		//resampleOp.setNumberOfThreads(1);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);

		BufferedImage image = resampleOp.filter(this.image, null);
		addImage(filter.getName()+" Unsharpen", image);
		resampleOp.addProgressListener(new ProgressListener() {
			public void notifyProgress(float fraction) {
				System.out.printf("Still working - %f percent %n",fraction*100);
			}
		});
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		image = resampleOp.filter(this.image, null);
		addImage(filter.getName(), image);

	}

	private void doMultiStepRescaleOp(String name, Object renderingHint){
		MultiStepRescaleOp mro = new MultiStepRescaleOp(200,200, renderingHint);
		mro.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage res = mro.filter(image, null);
		addImage(name+" Unsharpen", res);

		mro.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		res = mro.filter(image, null);
		addImage(name, res);
	}

	private void addImage(String name, BufferedImage image) {
		System.out.println("Adding "+name);
		JLabel label = new JLabel( new ImageIcon(image), JLabel.CENTER);
		label.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY),name));
		contentPanel.add(label);
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

	/**
	 * Invoked when an action occurs.
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		int res = fileChooser.showSaveDialog(this);
		if (res==JFileChooser.APPROVE_OPTION){
			BufferedImage bi = new BufferedImage(contentPanel.getWidth(),contentPanel.getHeight(),BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bi.createGraphics();
			contentPanel.paint(g2d);
			g2d.dispose();
			try {
				ImageIO.write(bi, "png", fileChooser.getSelectedFile());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		}
	}
}
