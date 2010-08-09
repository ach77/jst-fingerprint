/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Omega
 */
public class Test2 {

    Random r = new Random();
    static Test2 test;
    static int pixel[][] = {
        {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1},
        {0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0},
        {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1},
        {1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
        {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
        {1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0},
        {1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1}};

    public static void main(String[] args) {
//        ImageProcessor ip = new ImageProcessor();
//        double[] result = ip.divideImageArray(pixel);
//        for (int i = 0; i < result.length; i++) {
//            System.out.println(i + ":" + result[i]);
//        }
//        ArrayList list = new ArrayList();
//        list = ImageProcessor.slice(pixel, 3, 3, list);
//        for (int i = 0; i < list.size(); i++) {
//            int[][] tmp = (int[][]) list.get(i);
//            System.out.println("arr ke -" + i);
//            for (int j = 0; j < tmp.length; j++) {
//                for (int k = 0; k < tmp[j].length; k++) {
//                    System.out.print(tmp[j][k]);
//                }
//                System.out.println("");
//            }
//        }

        float[][] x = {{-10, 3.75f, -17.3f, -15, 15, 7.5f, -11.25f, -13.5f, -5, 0, -22.5f, -22.5f, 45, 0, 0, 0, 0, -22.5f, 45}};
        float[][] target = {{0, 0, 0, 0, 0, 1}};
        JSTEngine engine = new JSTEngine(x, target, 1, 0.01f, 1000, 19, 6, 6, 0, 0.35f);
        engine.trainingJST();
        float[] input = {-10, 3.75f, -17.3f, -15, 15, 7.5f, -11.25f, -13.5f, -5, 0, -22.5f, -22.5f, 45, 0, 0, 0, 0, -22.5f, 45};
        float [] result =engine.recognizeJST(input, 1);
        for (int i = 0; i < result.length; i++) {
            System.out.println("result["+i+"]="+result[i]);
        }


    }
}
