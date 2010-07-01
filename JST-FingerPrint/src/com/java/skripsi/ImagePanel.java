/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.skripsi;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andi Taru NNW
 */
public class ImagePanel extends Panel {
    BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        if (image != null) g.drawImage(image, 0, 0, null);
    }    
}
