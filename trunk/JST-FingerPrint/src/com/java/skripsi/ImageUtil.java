/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Andi Taru NNW
 */
public class ImageUtil {

    public static BufferedImage ImageToBufferedImage(Image imageIn, Component comp) {
        return createBufferedImage(imageIn, BufferedImage.TYPE_INT_ARGB, comp);
    }

    public static Image BufferedImagetoImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    
    public static BufferedImage createBufferedImage(Image imageIn, int imageType, Component comp) {
        MediaTracker mt = new MediaTracker(comp);
        mt.addImage(imageIn, 0);
        try {
            mt.waitForID(0);
        } catch (InterruptedException ie) {
        }
        BufferedImage bufferedImageOut = new BufferedImage(imageIn.getWidth(null), imageIn.getHeight(null), imageType);
        Graphics g = bufferedImageOut.getGraphics();
        g.drawImage(imageIn, 0, 0, null);

        return bufferedImageOut;
    }

    public static BufferedImage processImage(BufferedImage inputImage) {
        // Create a binary image for the results of processing
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_BINARY);

        // Work on a copy of input image because it is modified by diffusion
        WritableRaster input = inputImage.copyData(null);
        WritableRaster output = outputImage.getRaster();

        final int threshold = 128;
        float value, error;

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {

                value = input.getSample(x, y, 0);

                // Threshold value and compute error
                if (value < threshold) {
                    output.setSample(x, y, 0, 0);
                    error = value;
                } else {
                    output.setSample(x, y, 0, 1);
                    error = value - 255;
                }

                // Spread error amongst neighbouring pixels
                if ((x > 0) && (y > 0) && (x < (w - 1)) && (y < (h - 1))) {
                    value = input.getSample(x + 1, y, 0);
                    input.setSample(x + 1, y, 0, clamp(value + 0.4375f * error));
                    value = input.getSample(x - 1, y + 1, 0);
                    input.setSample(x - 1, y + 1, 0, clamp(value + 0.1875f * error));
                    value = input.getSample(x, y + 1, 0);
                    input.setSample(x, y + 1, 0, clamp(value + 0.3125f * error));
                    value = input.getSample(x + 1, y + 1, 0);
                    input.setSample(x + 1, y + 1, 0, clamp(value + 0.0625f * error));
                }

            }
        }
        return outputImage;

    }

    // Forces a value to a 0-255 integer range
    public static int clamp(float value) {
        return Math.min(Math.max(Math.round(value), 0), 255);
    }
}
