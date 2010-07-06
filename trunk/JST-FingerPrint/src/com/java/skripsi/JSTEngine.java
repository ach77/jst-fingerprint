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
public class JSTEngine {

    private float LR, error;
    private double elapseTime;
    Random r = new Random();

    public float random(int seed) {
        return r.nextInt() % seed;
    }

    public float sigmoid(float u) {
        return (float) (1 / (1 + Math.exp(u)));
    }

    public void inisialisasiBobot() {
    }

    public void training() {
    }
    //pola minuitae bifurcation
    int[][] pola = {
        //output 00
        {1, 0, 1, 0, 1, 0, 0, 1, 0},
        {0, 0, 1, 1, 1, 0, 0, 0, 1},
        {0, 1, 0, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 1, 0, 0},
        //output 01
        {0, 1, 0, 0, 1, 1, 1, 0, 0},
        {1, 0, 0, 0, 1, 1, 0, 1, 0},
        {0, 0, 1, 1, 1, 0, 0, 1, 0},
        {0, 1, 0, 1, 1, 0, 0, 0, 1},
        //output 10
        {0, 1, 0, 1, 1, 1, 0, 0, 0},
        {0, 1, 0, 0, 1, 1, 0, 1, 0},
        {0, 0, 0, 1, 1, 1, 0, 1, 0},
        {0, 1, 0, 1, 1, 0, 0, 1, 0},
        //output 11
        {1, 0, 1, 0, 1, 0, 0, 0, 1},
        {1, 0, 0, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 0},
        {0, 0, 1, 0, 1, 0, 1, 0, 1}
    };

    /**
     * menenutukan minutiae yg terdapat dalam sidik jari
     */
    public void ekstrasiMinutiae(int[][] pixel) {
        int[] minutiae = new int[9];
        int k = 0;
        for (int m = 0; m < pixel.length; m += 3) {
            for (int n = 0; n < pixel[m].length; n += 3) {
                k = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
//                        minutiae[k] = pixel[m + i][n + j];
//                        k++;
                    }
                }
                //mencocokan arr minutiae dengan pola
//                boolean match;
//                for (int i = 0; i < pola.length; i++) {
//                    match = false;
//                    for (int j = 0; j < pola[i].length; j++) {
//                        if (minutiae[j] == pola[i][j]) {
//                            match = true;
//                        } else {
//                            match = false;
//                        }
//                    }
//                    if (match) {
//                        System.out.println("pola baris ke :" + i);
//                        break;
//                    }
//                }
            }
        }
    }
}
