/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Random;

/**
 * The idea of this test, is to ensure that the subsampling method
 * works correct
 */
public class Issue7 extends TestCase {
	public void testSimpleCaseDecrease() throws Exception {
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

	public void testSimpleCaseIncrease() throws Exception {
		int srcSize = 50;
		int dstSize = 100;
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
			assertEquals("All pixels should only be read exactly twice "+Arrays.toString(readFreq),2,readFreq[i]);
		}
	}
}