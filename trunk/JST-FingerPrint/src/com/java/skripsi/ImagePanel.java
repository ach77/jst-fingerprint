/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    Image image;

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
