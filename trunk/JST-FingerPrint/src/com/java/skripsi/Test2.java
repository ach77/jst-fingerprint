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
public class Test {

    Random r = new Random();
    static Test test;

    public float random(int seed) {
        return r.nextInt() % seed;
    }

    public float sigmoid(float u) {
        return (float) (1 / (1 + Math.exp(u)));
    }

    public static void main(String[] args) {
        test = new Test();
        test.multiPerceptron();
    }

    public void multiPerceptron() {
        float z, deltaO, g1,
                deltaH[] = new float[2],
                f1[] = new float[2];
        float y[] = {0, 0, 1f};
        int x[][] = {
            {0, 0, 1},
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        float t[] = {0, 1f, 1f, 0};
        float w[][] = new float[2][3],
                O[] = new float[2],
                s[] = new float[3],
                LR = 0.1f, init = 0.15f, error;

        //inisialisasi bobot
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                w[j][i] = test.random(15) * 0.1f;
            }
        }

        for (int j = 0; j < 3; j++) {
            s[j] = test.random(15) * 0.1f;
        }

        //training
        for (int l = 0; l < 15000; l++) {
            error = 0;
            for (int p = 0; p < 4; p++) {
                for (int j = 0; j < 2; j++) {
                    O[j] = 0;
                    for (int i = 0; i < 3; i++) {
                        O[j] = O[j] + x[p][i] * w[j][i];
                    }
                    y[j] = sigmoid(O[j]);
                }
                O[0] = 0;
                for (int i = 0; i < 3; i++) {
                    O[0] = O[0] + y[i] * s[i];
                }
                z = sigmoid(O[0]);
                g1 = z * (1 - z);
                deltaO = (t[p] - z) * g1;
                for (int j = 0; j < 2; j++) {
                    f1[j] = y[j] * (1 - y[j]);
                }
                for (int j = 0; j < 2; j++) {
                    deltaH[j] = f1[j] * deltaO * s[j];
                }
                for (int i = 0; i < 3; i++) {
                    s[i] = s[i] + LR * deltaO * y[i];
                }
                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < 3; i++) {
                        w[j][i] = w[j][i] + LR * deltaH[j] * x[p][i];
                    }
                }
                error = error + ((t[p] - z) * (t[p] - z)) / 2;
            }
            error = error / 4;
            System.out.println("error-" + l + ":" + error);
        }
        //running
        x[0][0] = 1;
        x[0][1] = 1;
        for (int j = 0; j < 2; j++) {
            O[j] = 0;
            for (int i = 0; i < 3; i++) {
                O[j] = O[j] + x[0][i] * w[j][i];
            }
            y[j] = sigmoid(O[j]);
        }
        O[0] = 0;
        for (int i = 0; i < 3; i++) {
            O[0] = O[0] + y[i] * s[i];
        }
        z = sigmoid(O[0]);
        System.out.println("z:" + z);
        if (z <0) {
            System.out.println("Output = 0");
        } else {
            System.out.println("Output = 1");
        }
    }
}
