/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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

    public static int[][] getPixel(Image image) {
        int result[][] = null;
        byte[] pixels;
        int w, h;
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        DataBufferByte dbb = (DataBufferByte) bi.getRaster().getDataBuffer();
        pixels = dbb.getData(); // masukkan nilai pixel image ke dalam variabel pixels
        // Ciptakan variabel array 2 dimensi untuk menampung nilai pixel suatu image
        w = bi.getWidth();
        h = bi.getHeight();
        result = new int[h][w];

        int i = 0, j = 0;
        // Membuat array 2 dimensi untuk setiap image, sesuai dengan ukuran tinggi dan lebar image
        for (byte pix : pixels) {
            if (i % w == 0 && i != 0) {
                j++;
                i = 0;
            }
            int data = ((int) pix) & 0xff;
            result[j][i] = data;
            i++;
        }
        return result;
    }

    public static int[][] getPixel(BufferedImage bi) {
        int result[][] = null;
        byte[] pixels;
        int w, h;
        DataBufferByte dbb = (DataBufferByte) bi.getRaster().getDataBuffer();
        pixels = dbb.getData(); // masukkan nilai pixel image ke dalam variabel pixels
        // Ciptakan variabel array 2 dimensi untuk menampung nilai pixel suatu image
        w = bi.getWidth();
        h = bi.getHeight();
        result = new int[h][w];

        int i = 0, j = 0;
        // Membuat array 2 dimensi untuk setiap image, sesuai dengan ukuran tinggi dan lebar image
        for (byte pix : pixels) {
            if (i % w == 0 && i != 0) {
                j++;
                i = 0;
            }
            int data = ((int) pix) & 0xff;
            result[j][i] = data;
            i++;
        }
        return result;
    }

    public static int[][] ImageToBiner(Image image) {
        int result[][] = null;
        byte[] pixels;
        int w, h;
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        DataBufferByte dbb = (DataBufferByte) bi.getRaster().getDataBuffer();
        pixels = dbb.getData();
        w = bi.getWidth();
        h = bi.getHeight();
        result = new int[h][w];

        int i = 0, j = 0;
        for (byte pix : pixels) {
            if (i % w == 0 && i != 0) {
                j++;
                i = 0;
            }
            int data = ((int) pix) & 0xff;
            if (data <= 127) {
                result[j][i] = 1;
            } else {
                result[j][i] = 0;
            }
            i++;
        }
        return result;
    }

    public static BufferedImage arrayToBufferedImage(int[][] array) {
        // Ciptakan Buffered Image.
        BufferedImage im = new BufferedImage(array[0].length, array.length, BufferedImage.TYPE_BYTE_BINARY);
        // raster digunakan untuk mengeset nilai pixel
        WritableRaster raster = im.getRaster();
        // Meletakkan nilai pixel yang telah diubah ke dalam raster
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                array[y][x] = array[y][x] == 1 ? 0 : 1;
                raster.setSample(x, y, 0, array[y][x]);
            }
        }
        return im;
    }

    public static  byte[] fileToByteArray(File file) {
        ByteArrayOutputStream os = null;
        try {
            BufferedImage bi = ImageIO.read(file);
            os = new ByteArrayOutputStream();
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bi);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os.toByteArray();
    }
}
