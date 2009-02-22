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
 */

package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * May have to adjust heap using the Xmx vm option
 */
public class SpeedSingleThreadTest extends TestCase {

	protected BufferedImage image;

	public int getNumberOfThreads(){
		return 1;
	}

	protected void setUp() throws Exception {
		super.setUp();
		image = ImageIO.read(getClass().getResource("/com/mortennobel/imagescaling/largeimage.jpg"));
	}

	protected void doRescale(ResampleFilter filter) throws Exception{
		ResampleOp resampleOp = new ResampleOp(200,200);
		resampleOp.setNumberOfThreads(getNumberOfThreads());
		resampleOp.setFilter(filter);
		resampleOp.filter(image, null);
	}

	public void testBellFilter() throws Exception {
		doRescale(new BellFilter());
	}

	public void testBiCubicFilter() throws Exception {
		doRescale(new BiCubicFilter());
	}

	public void testBiCubicHighFreqResponse() throws Exception {
		doRescale(new BiCubicHighFreqResponse());
	}

	public void testBoxFilter() throws Exception {
		doRescale(new BoxFilter());
	}

	public void testBSplineFilter() throws Exception {
		doRescale(new BSplineFilter());
	}

	public void testHermiteFilter() throws Exception {
		doRescale(new HermiteFilter());
	}

	public void testLanczos3Filter() throws Exception {
		doRescale(new Lanczos3Filter());
	}

	public void testMitchellFilter() throws Exception {
		doRescale(new MitchellFilter());
	}

	public void testTriangleFilter() throws Exception {
		doRescale(new TriangleFilter());
	}

	public void testMultiStepRescaleOpLinear(){
		MultiStepRescaleOp mro = new MultiStepRescaleOp(200,200, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		mro.filter(image, null);
	}

	public void testMultiStepRescaleOpBicubic(){
		MultiStepRescaleOp mro = new MultiStepRescaleOp(200,200, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		mro.filter(image, null);
	}
}
