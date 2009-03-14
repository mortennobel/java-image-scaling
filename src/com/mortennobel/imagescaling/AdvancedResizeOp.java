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

import com.jhlabs.image.UnsharpFilter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Morten Nobel-Joergensen
 */
public abstract class AdvancedResizeOp implements BufferedImageOp {
    private List<ProgressListener> listeners = new ArrayList<ProgressListener>();

    private final DimensionConstrain dimensionConstrain;
	private boolean unsharpenFilterAfterReduce = false;

	public AdvancedResizeOp(DimensionConstrain dimensionConstrain) {
		this.dimensionConstrain = dimensionConstrain;
	}

	public boolean isUnsharpenFilterAfterReduce() {
		return unsharpenFilterAfterReduce;
	}

	public void setUnsharpenFilterAfterReduce(boolean unsharpenFilterAfterReduce) {
		this.unsharpenFilterAfterReduce = unsharpenFilterAfterReduce;
	}

	protected void fireProgressChanged(float fraction){
        for (ProgressListener progressListener:listeners){
            progressListener.notifyProgress(fraction);
        }
    }

    public final void addProgressListener(ProgressListener progressListener) {
        listeners.add(progressListener);
    }

    public final boolean removeProgressListener(ProgressListener progressListener) {
        return listeners.remove(progressListener);
    }

    public final BufferedImage filter(BufferedImage src, BufferedImage dest){
		Dimension dstDimension = dimensionConstrain.getDimension(new  Dimension(src.getWidth(),src.getHeight()));
		int dstWidth = dstDimension.width;
		int dstHeight = dstDimension.height;
		BufferedImage bufferedImage = doFilter(src, dest, dstWidth, dstHeight);

		if (unsharpenFilterAfterReduce){
			float xscale= ((float)dstWidth) / src.getWidth();
			float yscale= ((float)dstHeight) / src.getHeight();
			float amount= bestUnsharpAmountAfterScale(Math.min(xscale, yscale), Math.max(dstWidth, dstHeight));
			//
			if (amount > 0) {
				UnsharpFilter unsharpFilter= new UnsharpFilter();
				unsharpFilter.setRadius(2f);
				unsharpFilter.setAmount(amount);
				unsharpFilter.setThreshold(10);
				return  unsharpFilter.filter(bufferedImage, null);
			}
		}

		return bufferedImage;
	}

	private float bestUnsharpAmountAfterScale(float scale, int size) {
        if (scale >= 0.7)
            return scale > 1.1 ? 0.5f : 0f;
        float amount= 0.1f;
        if (size < 400)
            amount*= size <= 160 ? 2.5 : 1.5;
        else if (size > 600)
            amount*= 0.5f;
        if (scale < 0.1)
            amount*= 1.5;
        else if (scale > 0.3)
            amount*= 0.5;
        return amount;
    }

	protected abstract BufferedImage doFilter(BufferedImage src, BufferedImage dest, int dstWidth, int dstHeight);

	/**
     * {@inheritDoc}
     */
    public final Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    public final BufferedImage createCompatibleDestImage(BufferedImage src,
                                                   ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }
        return new BufferedImage(destCM,
                                 destCM.createCompatibleWritableRaster(
                                         src.getWidth(), src.getHeight()),
                                 destCM.isAlphaPremultiplied(), null);
    }

    /**
     * {@inheritDoc}
     */
    public final Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return (Point2D) srcPt.clone();
    }

    /**
     * {@inheritDoc}
     */
    public final RenderingHints getRenderingHints() {
        return null;
    }
}
