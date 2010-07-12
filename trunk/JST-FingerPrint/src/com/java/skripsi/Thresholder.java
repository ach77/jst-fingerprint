/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.image.BufferedImage;

/**
 *
 * @author petra, omega
 */
public class Thresholder {

    private static final int CONSTANT_BLACK = 0xff000000;
    private static final int CONSTANT_WHITE = 0xffffffff;

    public static void threshold(BufferedImage image) {
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
                    image.setRGB(x, y, CONSTANT_BLACK);
                } else if (cc == nmax1) {
                    image.setRGB(x, y, CONSTANT_WHITE);
                } else {
                    image.setRGB(x, y, CONSTANT_WHITE);
                }
            }
        }
    }
}
