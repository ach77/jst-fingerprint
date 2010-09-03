/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.awt.image.BufferedImage;

/**
 *
 * @author Andi Taru NNW
 */
public class NewThreshold {

    private final int CONSTANT_BLACK = 0xff000000;
    private final int CONSTANT_WHITE = 0xffffffff;
    private BufferedImage src, dst;

    public NewThreshold(BufferedImage inputImage) {
        this.src = inputImage;
        process();
    }

    public void process() {
        BufferedImage im = new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_GRAY);        

        for (int i = 0; i < 300; i += 10) {
            for (int j = 0; j < 300; j += 10) {
                int total = 0;
                int jumlah = 0;

                for (int x = i; x < i + 10; x++) {
                    for (int y = j; y < j + 10; y++) {
                        int rgb = src.getRGB(x, y);
                        total += rgb;
                        jumlah++;
                    }
                }

                double rata = total / (double) jumlah;                

                for (int x = i; x < i + 10; x++) {
                    for (int y = j; y < j + 10; y++) {
                        int rgb = src.getRGB(x, y);
                        if (rgb < rata) {
                            im.setRGB(x, y, CONSTANT_BLACK);
                        } else {
                            im.setRGB(x, y, CONSTANT_WHITE);
                        }
                    }
                }
            }
        }

        this.dst = im;        
    }

    public BufferedImage getResult() {
        return this.dst;
    }
}
