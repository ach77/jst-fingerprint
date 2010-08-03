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
        {1, 0, 1, 0, 1, 0,1,1},
        {0, 1, 0, 0, 1, 1,0,0},
    };

  public static void main(String[] args) {
//        float[][] x = {{0,0},{0,1},{1,0},{1,1}};
//        float[] target = {0,1,1,0};
//        JSTEngine engine = new JSTEngine(x, target, 1, 0.01f, 1000, 2,4, 0, 0.35f);
//        engine.trainingJST();
//        float [] input={1,0};
//        engine.recognizeJST(input, 1);
        float[][] x = {{10,12}};
        float[] target = {0};
        JSTEngine engine = new JSTEngine(x, target, 1, 0.01f, 1000, 2,4, 0, 0.35f);
        engine.trainingJST();
        float [] input={10,12};
        engine.recognizeJST(input, 1);
    }

}
