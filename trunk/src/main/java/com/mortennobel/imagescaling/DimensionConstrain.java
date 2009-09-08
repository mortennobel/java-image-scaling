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

import java.awt.*;

/**
 * This class let you create dimension constrains based on a actual image
 */
public class DimensionConstrain {
	public static final int NO_ALIGNMENT = -1;

	private final float alignX;
	private final float alignY;

	private DimensionConstrain ()
	{
		alignX = NO_ALIGNMENT;
		alignY = NO_ALIGNMENT;
	}

	/*private DimensionConstrain(float alignX, float alignY) {
		assert alignX>=0 && alignX<=1:"Align x must be in the range [0:1]";
		assert alignY>=0 && alignY<=1:"Align y must be in the range [0:1]";
		this.alignX = alignX;
		this.alignY = alignY;
	}*/

	/**
	 * Will always return a dimension with positive width and height;
	 * @param dimension
	 * @return
	 */
	public Dimension getDimension(Dimension dimension){
		return dimension;
	}

	/*public final float getAlignX() {
		return alignX;
	}

	public final float getAlignY() {
		return alignY;
	}*/

	/**
	 * Used when the destination size is fixed. This may not keep the image aspect radio
	 * @param width destination dimension width
	 * @param height destination dimension height
	 * @return  destination dimension (width x height)
	 */
	public static DimensionConstrain createAbsolutionDimension(final int width, final int height){
		assert width>0 && height>0:"Dimension must be a positive integer";
		return new DimensionConstrain(){
			public Dimension getDimension(Dimension dimension) {
				return new Dimension(width, height);
			}
		};
	}

	/**
	 * Used when the destination size is fixed. This image aspect radio is keeped by cropping the image.
	 * <br>
	 * If the source image is too wide, alignx determine what part should be keeped:
	 * <ul>
	 * <li>0.0f means left part keeped (right part removed)
	 * <li>0.5f means center part keeped (left and right part removed)
	 * <li>1.0f means right part keeped (left part removed)
	 * </ul>
	 * If the source image is too high, aligny determines what part should be keeped:
	 * <ul>
	 * <li>0.0f means top part keeped (bottom part removed)
	 * <li>0.5f means center part keeped (top and bottom part removed)
	 * <li>1.0f means bottom part keeped (top part removed)
	 * </ul>
	 *
	 * @param width destination dimension width
	 * @param height destination dimension height
	 * @param alignX alignment of content when cropped horizontal
	 * @param alignY alignment of content when cropped vertical
	 * @return destination dimension (width x height)
	 */
	/*public static DimensionConstrain createAbsolutionDimensionKeepAspectRadio(final int width, final int height, float alignX, float alignY){
		assert width>0 && height>0:"Dimension must be a positive integer";
		return new DimensionConstrain(alignX,alignY){
			public Dimension getDimension(Dimension dimension) {
				return new Dimension(width, height);
			}
		};
	}*/

	/**
	 * Used when the destination size is fixed. This image aspect radio is keeped by cropping the image (center part is
	 * keeped).
	 *
	 * @param width destination dimension width
	 * @param height destination dimension height
	 * @return destination dimension (width x height)
	 */
	/*public static DimensionConstrain createAbsolutionDimensionKeepAspectRadio(final int width, final int height){
		assert width>0 && height>0:"Dimension must be a positive integer";
		final float KEEP_CENTER = 0.5f;
		return new DimensionConstrain(KEEP_CENTER,KEEP_CENTER){
			public Dimension getDimension(Dimension dimension) {
				return new Dimension(width, height);
			}
		};
	}*/
	

	/**
	 * Used when the destination size is relative to the source. This keeps the image aspect radio
	 * @param fraction resize fraction (must be a positive number)
	 * @return the new dimension (the input dimension times the fraction)
	 */
	public static DimensionConstrain createRelativeDimension(final float fraction){
		return createRelativeDimension(fraction,fraction);
	}

	/**
	 * Used when the destination size is relative to the source. This keeps the image aspect radio
	 * @param
	 * @return
	 */
	public static DimensionConstrain createRelativeDimension(final float fractionWidth, final float fractionHeight){
		assert fractionHeight>0 && fractionWidth>0:"Fractions must be larger than 0.0";
		return new DimensionConstrain(){
			public Dimension getDimension(Dimension dimension) {
				int width = Math.max(1,Math.round(fractionWidth*dimension.width));
				int height = Math.max(1,Math.round(fractionHeight*dimension.height));
				return new Dimension(width, height);
			}
		};
	}

	/**
	 * Forces the image to keep radio and be keeped within the width and height
	 * @param width
	 * @param height
	 * @return
	 */
	public static DimensionConstrain createMaxDimension(int width, int height){
		return createMaxDimension(width, height,false);
	}

	/**
	 * Forces the image to keep radio and be keeped within the width and height.
	 * @param width
	 * @param height
	 * @param neverEnlargeImage if true only a downscale will occour
	 * @return
	 */
	public static DimensionConstrain createMaxDimension(final int width, final int height, final boolean neverEnlargeImage){
		assert width >0 && height > 0 : "Dimension must be larger that 0";
		final double scaleFactor = width/(double)height;
		return new DimensionConstrain(){
			public Dimension getDimension(Dimension dimension) {
				double srcScaleFactor = dimension.width/(double)dimension.height;
				double scale;
				if (srcScaleFactor>scaleFactor){
					scale = width/(double)dimension.width;
				}
				else{
					scale = height/(double)dimension.height;
				}
				if (neverEnlargeImage){
					scale = Math.min(scale,1);
				}
				int dstWidth = (int)Math.round (dimension.width*scale);
				int dstHeight = (int) Math.round(dimension.height*scale);
				return new Dimension(dstWidth, dstHeight);
			}
		};
	}

	/**
	 * Forces the image to keep radio and be keeped within the width and height. Width and height are defined
	 * (length1 x length2) or (length2 x length1).
	 *
	 * This is usefull is the scaling allow a certain format (such as 16x9") but allow the dimension to be rotated 90
	 * degrees (so also 9x16" is allowed).
	 *
	 * @param length1
	 * @param length2
	 * @return
	 */
	public static DimensionConstrain createMaxDimensionNoOrientation(int length1, int length2){
		return createMaxDimensionNoOrientation(length1, length2,false);
	}

	/**
	 * Forces the image to keep radio and be keeped within the width and height. Width and height are defined
	 * (length1 x length2) or (length2 x length1).
	 *
	 * This is usefull is the scaling allow a certain format (such as 16x9") but allow the dimension to be rotated 90
	 * degrees (so also 9x16" is allowed).
	 *
	 * @param length1
	 * @param length2
	 * @param neverEnlargeImage if true only a downscale will occour
	 * @return
	 */
	public static DimensionConstrain createMaxDimensionNoOrientation(final int length1, final int length2, final boolean neverEnlargeImage){
		assert length1 >0 && length2 > 0 : "Dimension must be larger that 0";
		final double scaleFactor = length1/(double)length2;
		return new DimensionConstrain(){
			public Dimension getDimension(Dimension dimension) {
				double srcScaleFactor = dimension.width/(double)dimension.height;
				int width;
				int height;
				// swap length1 and length2
				if (srcScaleFactor>scaleFactor){
					width = length1;
					height = length2;
				}
				else{
					width = length2;
					height = length1;
				}


				final double scaleFactor = width/(double)height;
				double scale;
				if (srcScaleFactor>scaleFactor){
					scale = width/(double)dimension.width;
				}
				else{
					scale = height/(double)dimension.height;
				}
				if (neverEnlargeImage){
					scale = Math.min(scale,1);
				}
				int dstWidth = (int)Math.round (dimension.width*scale);
				int dstHeight = (int) Math.round(dimension.height*scale);
				return new Dimension(dstWidth, dstHeight);
			}
		};
	}


}
