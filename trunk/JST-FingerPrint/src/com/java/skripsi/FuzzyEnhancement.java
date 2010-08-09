/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Andi Taru NNW
 */
public class FuzzyEnhancement {

    private int BLACK = 0xff000000, WHITE = 0xffffffff;
    private BufferedImage oriImage;
    private BufferedImage modifiedImage;
    private int pixels[][];

    public FuzzyEnhancement(BufferedImage theImage) {
        this.oriImage = theImage;
        this.modifiedImage = new BufferedImage(theImage.getWidth(), 
                theImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);               

        pixels = new int[theImage.getWidth()][theImage.getHeight()];

        for (int i = 0; i < theImage.getWidth(); i++) {
            for (int j = 0; j < theImage.getHeight(); j++) {
                Color color = new Color(theImage.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int rgb = (r+g+b)/3;                
                pixels[i][j] = rgb;                
            }
        }
    }

    public void fuzzyfication() {
        for (int i = 1; i < oriImage.getWidth(); i += 3) {
            for (int j = 1; j < oriImage.getHeight(); j += 3) {
                defuzzyfication(i, j);
            }
        }
    }

    private int getColor(int x, int y) {
        if (pixels[x][y] > 200) {
            return WHITE;
        }
        return BLACK;
    }

    private void defuzzyfication(int i, int j) {
        if (rule1(i, j)) applyRule1(i, j);
        else if (rule2(i, j)) applyRule2(i, j);
        else if (rule3(i, j)) applyRule3(i, j);
        else if (rule4(i, j)) applyRule4(i, j);
        else if (rule5(i, j)) applyRule5(i, j);
        else if (rule6(i, j)) applyRule6(i, j);
        else if (rule7(i, j)) applyRule7(i, j);
        else if (rule8(i, j)) applyRule8(i, j);
        else applyRuleException(i, j);
    }    

    private boolean rule1(int i, int j) {
        if (getColor(i - 1, j - 1) == WHITE && getColor(i - 1, j) == WHITE && getColor(i - 1, j + 1) == WHITE
                && getColor(i, j - 1) == WHITE && getColor(i, j) == WHITE && getColor(i, j + 1) == WHITE
                && getColor(i + 1, j - 1) == BLACK && getColor(i + 1, j) == BLACK && getColor(i + 1, j + 1) == BLACK) {
            return true;
        }
        return false;
    }

    private boolean rule2(int i, int j) {
        if (getColor(i - 1, j - 1) == BLACK && getColor(i - 1, j) == BLACK && getColor(i - 1, j + 1) == BLACK
                && getColor(i, j - 1) == WHITE && getColor(i, j) == WHITE && getColor(i, j + 1) == WHITE
                && getColor(i + 1, j - 1) == WHITE && getColor(i + 1, j) == WHITE && getColor(i + 1, j + 1) == WHITE) {
            return true;
        }
        return false;
    }

    private boolean rule3(int i, int j) {
        if (getColor(i - 1, j - 1) == BLACK && getColor(i - 1, j) == WHITE && getColor(i - 1, j + 1) == WHITE
                && getColor(i, j - 1) == BLACK && getColor(i, j) == WHITE && getColor(i, j + 1) == WHITE
                && getColor(i + 1, j - 1) == BLACK && getColor(i + 1, j) == WHITE && getColor(i + 1, j + 1) == WHITE) {
            return true;
        }
        return false;
    }

    private boolean rule4(int i, int j) {
        if (getColor(i - 1, j - 1) == WHITE && getColor(i - 1, j) == WHITE && getColor(i - 1, j + 1) == BLACK
                && getColor(i, j - 1) == WHITE && getColor(i, j) == WHITE && getColor(i, j + 1) == BLACK
                && getColor(i + 1, j - 1) == WHITE && getColor(i + 1, j) == WHITE && getColor(i + 1, j + 1) == BLACK) {
            return true;
        }
        return false;
    }

    private boolean rule5(int i, int j) {
        if (getColor(i - 1, j - 1) == BLACK && getColor(i - 1, j) == BLACK && getColor(i - 1, j + 1) == WHITE
                && getColor(i, j - 1) == BLACK && getColor(i, j) == WHITE && getColor(i, j + 1) == WHITE
                && getColor(i + 1, j - 1) == BLACK && getColor(i + 1, j) == WHITE && getColor(i + 1, j + 1) == WHITE) {
            return true;
        }
        return false;
    }

    private boolean rule6(int i, int j) {
        if (getColor(i - 1, j - 1) == WHITE && getColor(i - 1, j) == WHITE && getColor(i - 1, j + 1) == BLACK
                && getColor(i, j - 1) == WHITE && getColor(i, j) == WHITE && getColor(i, j + 1) == BLACK
                && getColor(i + 1, j - 1) == WHITE && getColor(i + 1, j) == BLACK && getColor(i + 1, j + 1) == BLACK) {
            return true;
        }
        return false;
    }

    private boolean rule7(int i, int j) {
        if (getColor(i - 1, j - 1) == BLACK && getColor(i - 1, j) == WHITE && getColor(i - 1, j + 1) == WHITE
                && getColor(i, j - 1) == BLACK && getColor(i, j) == WHITE && getColor(i, j + 1) == WHITE
                && getColor(i + 1, j - 1) == BLACK && getColor(i + 1, j) == BLACK && getColor(i + 1, j + 1) == WHITE) {
            return true;
        }
        return false;
    }

    private boolean rule8(int i, int j) {
        if (getColor(i - 1, j - 1) == WHITE && getColor(i - 1, j) == BLACK && getColor(i - 1, j + 1) == BLACK
                && getColor(i, j - 1) == WHITE && getColor(i, j) == WHITE && getColor(i, j + 1) == BLACK
                && getColor(i + 1, j - 1) == WHITE && getColor(i + 1, j) == WHITE && getColor(i + 1, j + 1) == BLACK) {
            return true;
        }
        return false;
    }

    private void applyRule1(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, WHITE);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j, BLACK);
        this.modifiedImage.setRGB(i + 1, j + 1, BLACK);
    }

    private void applyRule2(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i - 1, j, BLACK);
        this.modifiedImage.setRGB(i - 1, j + 1, BLACK);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, WHITE);
    }

    private void applyRule3(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, WHITE);
        this.modifiedImage.setRGB(i, j - 1, BLACK);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, WHITE);
    }

    private void applyRule4(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, BLACK);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, BLACK);
    }

    private void applyRule5(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i - 1, j, BLACK);
        this.modifiedImage.setRGB(i - 1, j + 1, WHITE);
        this.modifiedImage.setRGB(i, j - 1, BLACK);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, WHITE);
    }

    private void applyRule6(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, BLACK);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j, BLACK);
        this.modifiedImage.setRGB(i + 1, j + 1, BLACK);
    }

    private void applyRule7(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, WHITE);
        this.modifiedImage.setRGB(i, j - 1, BLACK);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j, BLACK);
        this.modifiedImage.setRGB(i + 1, j + 1, WHITE);
    }

    private void applyRule8(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i - 1, j, BLACK);
        this.modifiedImage.setRGB(i - 1, j + 1, BLACK);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, BLACK);
        this.modifiedImage.setRGB(i + 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, BLACK);
    }

    private void applyRuleException(int i, int j) {
        this.modifiedImage.setRGB(i - 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i - 1, j, WHITE);
        this.modifiedImage.setRGB(i - 1, j + 1, WHITE);
        this.modifiedImage.setRGB(i, j - 1, WHITE);
        this.modifiedImage.setRGB(i, j, WHITE);
        this.modifiedImage.setRGB(i, j + 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j - 1, WHITE);
        this.modifiedImage.setRGB(i + 1, j, WHITE);
        this.modifiedImage.setRGB(i + 1, j + 1, WHITE);
    }

    public BufferedImage getFuzzyImage() {
        return this.modifiedImage;
    }

    public static void main(String[] args) {
        try {            
            BufferedImage buffImage = ImageIO.read(new File("test.jpg"));
            
            FuzzyEnhancement fe = new FuzzyEnhancement(buffImage);
            fe.fuzzyfication();

            ImageIO.write(fe.modifiedImage, "jpg", new File("result.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(FuzzyEnhancement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
