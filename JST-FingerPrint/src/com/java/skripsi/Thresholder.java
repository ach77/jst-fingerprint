/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Thresholder {
    private BufferedImage image,dst;

    private final int CONSTANT_BLACK = 0xff000000;
    private final int CONSTANT_WHITE = 0xffffffff;

    public Thresholder(BufferedImage image) {
        this.image = image;
        threshold();
    }

    public void threshold() {
        BufferedImage im = new BufferedImage(image.getWidth(),
                image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = im.getRaster();
        
        int thresholdvalues[] = new int[256];
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int color = image.getRGB(x, y);
                int cred = (color & 0x00ff0000) >> 16;
                int cgreen = (color & 0x0000ff00) >> 8;
                int cblue = (color & 0x000000ff);
                int cc = ((int) (cred + cgreen + cblue) / 3);
                thresholdvalues[cc]++; //System.out.printf("%d %d : %d\n", x, y, cc);
            }
        }
        int laststate = 0;
        int currentstate = 0;
        int max1 = thresholdvalues[0];
        int nmax1 = 0;
        int max2 = 0;
        int nmax2 = 0;
        for (int i = 1; i < 256; i++) {
            currentstate = (thresholdvalues[i] > thresholdvalues[i - 1]) ? 1 : (thresholdvalues[i] == thresholdvalues[i - 1]) ? 0 : -1;
            if (currentstate != laststate || i == 255) {
                if (thresholdvalues[i] > max1) {
                    max2 = max1;
                    nmax2 = nmax1;
                    max1 = thresholdvalues[i];
                    nmax1 = i;
                } else if (thresholdvalues[i] > max2) {
                    max2 = thresholdvalues[i];
                    nmax2 = i;
                }
            }
            laststate = currentstate;
        }

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int color = image.getRGB(x, y);
                int cred = (color & 0x00ff0000) >> 16;
                int cgreen = (color & 0x0000ff00) >> 8;
                int cblue = (color & 0x000000ff);
                int cc = ((int) (cred + cgreen + cblue) / 3);
                if (cc == nmax2) {
                    raster.setSample(x, y, 0, CONSTANT_BLACK);
                    // image.setRGB(x, y, CONSTANT_BLACK);
                } else if (cc == nmax1) {
                    raster.setSample(x, y, 0, CONSTANT_WHITE);
                    // image.setRGB(x, y, CONSTANT_WHITE);
                } else {
                    raster.setSample(x, y, 0, CONSTANT_WHITE);
                    // image.setRGB(x, y, CONSTANT_WHITE);
                }
            }
        }

       this.dst = im;
    }

    public BufferedImage getResult() {
        return this.dst;
    }
}
