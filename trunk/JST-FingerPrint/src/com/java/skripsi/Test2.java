/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

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

        // menandakan 3 input i[0],i[1],i[2]
        double[][] x = {
            {-10, 3.75f, -17.3f, -15, 15, 7.5f, -11.25f, -13.5f, -5, 0, -22.5f, -22.5f, 45, 0, 0, 0, 0, -22.5f, 45},
            {10, 2, 4, -15, 3, 14, -6.4, 0.2, 0.5, 0.6, -19, -18, 13, 14, 15, -14, -12, 14, 19},
            {14, 4, 8, -2, 7, 24, -16.4, 0.8, 0.9, 0.5, -11, -15, 12, 19, 25, -24, -22, 18, -19},
        };
        // menandakan 3 target t[0],t[1],t[2] berupa bilangan biner
        // i[0] punya target t[0], i[1] punya target t[1], i[2] punya target t[2]
        double[][] target = {
            {0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1}
        };
        //set parameter
        JSTEngine engine = new JSTEngine(x, target, 1, 0.01f, 1000, 19, 6, 6, 0, 0.35f);
        //proses pembelajaran 3 input dan 3 target
        engine.trainingJST();

        //hasil = 0.5< menjadi 0 >0.5 menjadi 1
        //ex: result3[0]=0.019202959054511776
        //result3[1]=0.018194101148517952 -> 0
        //result3[2]=0.01918051956173074  -> 0
        //result3[3]=0.01975842541945473  -> 0
        //result3[4]=0.9669412925943478   -> 1
        //result3[5]=0.8736282519345553   -> 1
        //recognition dengan input asal
        double[] input =   {19, 35, 10, 24, 25, 34, -15.4, 1.8, -0.78, -6.5, 21, -28, -22, -17, 25, 34, 22, -38.4, 19.4};
        //didapatkan hasilnya, tapi tidak valid
        double[] result = engine.recognizeJST(input);
        for (int i = 0; i < result.length; i++) {
            System.out.println("result[" + i + "]=" + result[i]);
        }
        System.out.println("");
        //recognition dengan input i[0]
        double[] input1 = {-10, 3.75f, -17.3f, -15, 15, 7.5f, -11.25f, -13.5f, -5, 0, -22.5f, -22.5f, 45, 0, 0, 0, 0, -22.5f, 45};
        //hasilnya sesuai
        result =engine.recognizeJST(input1);
         for (int i = 0; i < result.length; i++) {
            System.out.println("result2[" + i + "]=" + result[i]);
        }
        System.out.println("");
        //recognition dengan input i[3]
        double[] input3 ={14, 4, 8, -2, 7, 24, -16.4, 0.8, 0.9, 0.5, -11, -15, 12, 19, 25, -24, -22, 18, -19};
        //hasilnya sesuai
        result =engine.recognizeJST(input3);
         for (int i = 0; i < result.length; i++) {
            System.out.println("result3[" + i + "]=" + result[i]);
        }


    }
}
