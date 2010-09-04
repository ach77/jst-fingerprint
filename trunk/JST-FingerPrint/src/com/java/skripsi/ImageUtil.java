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

    /**
     * mengubah image menjadi bufferedimage
     * @param imageIn
     * @param comp
     * @return
     */
    public static BufferedImage ImageToBufferedImage(Image imageIn, Component comp) {
        return createBufferedImage(imageIn, BufferedImage.TYPE_INT_ARGB, comp);
    }

    /**
     * mengubah buffered image menjadi image
     * @param bufferedImage
     * @return
     */
    public static Image BufferedImagetoImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    /**
     * membuat buffered image dari image
     * @param imageIn
     * @param imageType
     * @param comp
     * @return
     */
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

    /**
     * mengubah image menjadi bilangan biner
     * @param image image
     * @return bilangan biner int[][]
     */
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

    /**
     * mengubah array integer menjadi bufferedimage
     * @param array
     * @return
     */
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

    /**
     * mengubah file menjadi byte array
     * digunakan untuk menyimpan gambar di database dalam format byte
     * @param file
     * @return
     */
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
