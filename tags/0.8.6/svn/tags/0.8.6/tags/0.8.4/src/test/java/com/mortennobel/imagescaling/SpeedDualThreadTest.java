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

public class SpeedDualThreadTest extends SpeedSingleThreadTest {
	public int getNumberOfThreads() {
		return 2;
	}

	public void testHasMoreThanOneCore(){
		assertTrue("This test does only make sence when more than one processor is available to the system",
				Runtime.getRuntime().availableProcessors()>=2);
	}
}
