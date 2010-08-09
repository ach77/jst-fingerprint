/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.skripsi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

/**
 *
 * @author Andi Taru NNW
 */
public class FuzzyEnhancementInt {
    private BufferedImage src, dst;
    private int width = 0, height = 0;
    private double maxLevel = 0, minLevel = 0, middleLevel = 0;
    private ArrayList<Double> listLevel = new ArrayList<Double>();
    
    public FuzzyEnhancementInt(BufferedImage image) {
        this.src   = image;
        this.width      = image.getWidth();
        this.height     = image.getHeight();
        convertToGray();
    }    

    private void convertToGray() {
        /*BufferedImage bi = new BufferedImage(
                this.src.getWidth(),
                this.src.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics bg = bi.getGraphics();
        bg.drawImage(this.src, 0, 0, null);
        bg.dispose();
        this.dst = bi;*/
        int c, r, g, b;

        BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = im.getRaster();

        for(int i=0; i<this.src.getWidth(); i++){
            for(int j=0; j<this.src.getHeight(); j++){
                c =  this.src.getRGB(i,j);
                r = (c & 0x00ff0000) >> 16;
                g = (c & 0x0000ff00) >> 8;
                b = c & 0x000000ff;
                float gray = (float) (0.3 * r + 0.59 * g + 0.11 * b);
                raster.setSample(i, j, 0, gray);
            }
        }
        this.dst = im;
    }

    private void detectListLevel() {
        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height;y++) {

                Color color = new Color(this.src.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                double rgb = (r+g+b)/3;

                boolean newData = true;

                for (Double i:listLevel) {
                    if (i==rgb) {
                        newData = false;
                        break;
                    }
                }

                if (newData) listLevel.add(rgb);
            }
        }
    }

    private void sortListLevel() {
        Collections.sort(listLevel);
        this.minLevel = listLevel.get(0);
        this.maxLevel = listLevel.get(listLevel.size()-1);

        double jarak = maxLevel - minLevel;
        this.middleLevel = minLevel+jarak;
    }   

    public void processEnhancement() {
        detectListLevel();
        sortListLevel();
        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height;y++) {
                
                Color color = new Color(this.dst.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                double rgb = (r+g+b)/3;

                /*
                Color c = new Color((int)rgb);
                this.dst.setRGB(x, y, src.getRGB(x,y));
                */

                /*if (rgb < middleLevel)
                    this.dst.setRGB(x, y, Color.BLACK.getRGB());
                else
                    this.dst.setRGB(x, y, Color.WHITE.getRGB());
                */
            }
        }
    }

    public BufferedImage getEnhancementImage() {
        return this.dst;
    }    

    public static void main(String[] args) {
        try {
            BufferedImage buffImage = ImageIO.read(new File("finta3.jpg"));

            FuzzyEnhancementInt fei = new FuzzyEnhancementInt(buffImage);            
            fei.processEnhancement();

            ImageIO.write(fei.getEnhancementImage(), "jpg", new File("finta3_result.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
