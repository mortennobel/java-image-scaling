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
