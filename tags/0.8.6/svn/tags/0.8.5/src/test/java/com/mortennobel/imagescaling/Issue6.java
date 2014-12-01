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

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * The idea of this test, is to ensure that the subsampling method
 * works correct
 */
public class Issue6 extends TestCase {
	public void testSimpleCase() throws Exception {
		int srcSize = 100;
		int dstSize = 50;
		ResampleOp.SubSamplingData subSamplingData = ResampleOp.createSubSampling(ResampleFilters.getBoxFilter(), srcSize,dstSize);
		int[] readFreq = new int[srcSize];

		for (int i=0;i<dstSize;i++){
			int offset = i* subSamplingData.getNumContributors();
			int max= subSamplingData.getArrN()[i];
			for (int j=0;j<max;j++,offset++){
				int valueLocation = subSamplingData.getArrPixel()[offset];
				readFreq[valueLocation]++;
			}
		}
		for (int i=0;i<readFreq.length;i++){
			assertEquals("All pixels should only be read exactly once "+Arrays.toString(readFreq),1,readFreq[i]);
		}
	}
}
