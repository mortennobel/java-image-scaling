package com.mortennobel.imagescaling;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * However, when trying the library, I get messages saying that the resize is
 * still working and that it is over 100%. This seems a bit odd :-) If I don't
 * terminate the program myself, it keeps on running. My code is attached.
 *
 * I'm using Linux Ubuntu (although this should not matter) and Suns JDK
 * 1.6.0.16. Version of java-image-scaling is 0.8.1.
 *
 * Getting messages like:
 * Still working - 450,250580 percent
 * Still working - 450,278381 percent
 * Still working - 450,306213 percent
 * Still working - 450,334076 percent
 * Still working - 450,361908 percent
 *
 */
public class Issue9 extends TestCase {
	boolean inProgress;
	public void testForBug(){
		try {
			inProgress = true;
			BufferedImage image2D = ImageIO.read(getClass().getResourceAsStream("flower.jpg"));

			DimensionConstrain dc = DimensionConstrain.createMaxDimensionNoOrientation(1000,1000);

			ResampleOp  resampleOp = new ResampleOp (dc);
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
			resampleOp.addProgressListener(new ProgressListener() {
				public void notifyProgress(float fraction) {
					assertTrue(inProgress);
					System.out.printf("Still working - %f percent %n",fraction*100);
				}
			});
			BufferedImage rescaledTomato = resampleOp.filter(image2D, null);
			inProgress = false;
			System.out.println("Done scaling");
			//ImageIO.write(rescaledTomato, "jpg", new File("/home/frederik/Afbeeldingen/temp4.JPG"));
			try{
				System.out.println("Waiting");
				Thread.sleep(10000);
			}
			catch (Exception e){
				// e
			}
		} catch (Exception e) {

		}

	}
}
