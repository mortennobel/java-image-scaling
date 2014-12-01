/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */

package com.mortennobel.imagescaling;

public class SpeedDualThreadTest extends SpeedSingleThreadTest {
	public int getNumberOfThreads() {
		return 2;
	}

	public void testHasMoreThanOneCore(){
		assertTrue("This test does only make sence when more than one processor is available to the system",
				Runtime.getRuntime().availableProcessors()>=2);
	}
}
