/*
 * Copyright 2013, Morten Nobel-Joergensen
 *
 * License: The BSD 3-Clause License
 * http://opensource.org/licenses/BSD-3-Clause
 */
package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: morten
 * Date: Dec 7, 2010
 * Time: 8:46:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Issue20 extends TestCase {
    public void testScaleImage() throws Exception {
        BufferedImage img = ImageIO.read(getClass().getResourceAsStream("issue20.jpg"));


        ResampleOp resampleOp = new ResampleOp(DimensionConstrain.createMaxDimension(200,200));
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledImage = resampleOp.filter(img, null);
        File file = new File("Issue20_scaled.jpeg");
        ImageIO.write(rescaledImage, "jpg", file);


        ImageOutputStream ios = new FileImageOutputStream(file);
        ImageUtils.copyJpegMetaData(getClass().getResourceAsStream("issue20.jpg"), new FileInputStream(file),ios);
    }

    public void testScaleImageByteArray() throws Exception {
        // read image data as byte array
        ByteArrayOutputStream source = new ByteArrayOutputStream();
        InputStream is = getClass().getResourceAsStream("issue20.jpg");
        int i;
        while ((i= is.read())!=-1){
            source.write(i);
        }
        is.close();
        // read source image
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(source.toByteArray()));

        // scale image
        ResampleOp resampleOp = new ResampleOp(DimensionConstrain.createMaxDimension(200,200));
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledImage = resampleOp.filter(img, null);
        // save scaled image as byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(rescaledImage, "jpg", baos);

        // copy exif informations
        byte[] res = ImageUtils.copyJpegMetaData(source.toByteArray(), baos.toByteArray());

        FileOutputStream fos = new FileOutputStream("issue20_byte.jpg");
        fos.write(res);
    }
}
