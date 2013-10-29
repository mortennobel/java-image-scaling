/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import static com.mortennobel.imagescaling.DimensionConstrain.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;

public class DimensionConstrainTest {
	@Test
	public void testCreateMaxDimension(){
		DimensionConstrain constains = createMaxDimension(200,100);
		Dimension res = constains.getDimension(new Dimension(400,400));
		assertEquals( new Dimension(100,100),res);
	}

	@Test
	public void testCreateMaxDimension2(){
		DimensionConstrain constains = createMaxDimension(200,100);
		Dimension res = constains.getDimension(new Dimension(400,800));
		assertEquals(new Dimension(50,100),res);
	}

	@Test
	public void testCreateMaxDimensionEnlarge(){
		DimensionConstrain constains = createMaxDimension(400,200);
		Dimension res = constains.getDimension(new Dimension(100,10));
		assertEquals(new Dimension(400,40),res);
	}

	@Test
	public void testCreateMaxDimensionNoEnlarge(){
		DimensionConstrain constains = createMaxDimension(400,200,true);
		Dimension res = constains.getDimension(new Dimension(100,10));
		assertEquals(new Dimension(100,10),res);
	}

	@Test
	public void testCreateMaxDimensionNoOriontation(){
		DimensionConstrain constains = createMaxDimensionNoOrientation(200,100);
		Dimension res = constains.getDimension(new Dimension(400,400));
		assertEquals( new Dimension(100,100),res);
	}

	@Test
	public void testCreateMaxDimensionNoOriontation2(){
		DimensionConstrain constains = createMaxDimensionNoOrientation(200,100);
		Dimension res = constains.getDimension(new Dimension(400,800));
		assertEquals(new Dimension(100,200),res);
	}

	@Test
	public void testCreateMaxDimensionNoOriontationEnlarge(){
		DimensionConstrain constains = createMaxDimensionNoOrientation(400,200);
		Dimension res = constains.getDimension(new Dimension(100,10));
		assertEquals(new Dimension(400,40),res);
	}

	@Test
	public void testCreateMaxDimensionNoOriontationNoEnlarge(){
		DimensionConstrain constains = createMaxDimensionNoOrientation(400,200,true);
		Dimension res = constains.getDimension(new Dimension(100,10));
		assertEquals(new Dimension(100,10),res);
	}




}
