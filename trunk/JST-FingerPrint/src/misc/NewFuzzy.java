/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andi Taru NNW
 */
public class NewFuzzy {
    private BufferedImage src, dst;

    public NewFuzzy(BufferedImage inputImage) {
        this.src = inputImage;
        process();
    }

    public void process() {
        BufferedImage im = new BufferedImage(300,300,BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                int rgb = src.getRGB(j, i);
                Color c = new Color(rgb);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                float lr = (float)r/(float)255;
                float lg = (float)g/(float)255;
                float lb = (float)b/(float)255;

                double newLR = (lr < 0.5)? 2*Math.pow(lr,2) : 1-(2*Math.pow(1-lr,2));
                double newLG = (lg < 0.5)? 2*Math.pow(lg,2) : 1-(2*Math.pow(1-lg,2));
                double newLB = (lb < 0.5)? 2*Math.pow(lb,2) : 1-(2*Math.pow(1-lb,2));

                int newR = Math.round((float)newLR * 255);
                int newG = Math.round((float)newLG * 255);
                int newB = Math.round((float)newLB * 255);

                // System.out.println("Before:"+r+" lr="+lr+" LR="+newLR+" After:"+newR);

                Color newColor = new Color(newR,newG,newB);
                im.setRGB(j, i, newColor.getRGB());
            }
        }

        this.dst = im;        
    }

    public BufferedImage getResult() {
        return this.dst;
    }
}
