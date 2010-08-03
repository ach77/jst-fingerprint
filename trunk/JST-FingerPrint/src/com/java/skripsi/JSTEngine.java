/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.text.NumberFormat;
import java.util.Random;

/**
 *
 * @author Omega
 */
public class JSTEngine {

    //    public static final float E = 2.718f;               // konstanta e/bilangan Euler
    public float lr, targetError = 0, mse = 0;          // learning rate ,targetError, kuadrat target error/MSE(Mean Square Error
    public int epoch;                                   // jumlah pengulangan maksimum
    public int jmlHidden, jmlInput;                      // jumlah neuron bias yg digunakan
    public float[] target, v0, w;                       // input,bobot untuk bias-hidden, hidden-ouutput
    public float[][] v, x;                              // bobot input-hidden
    public float w0, y;                                 // bobot w bias-output, output, target
    public float batasMax, batasMin;                    // batas maksimum/minimum nilai random
    Random r = new Random();
    NumberFormat numberFormat;

    public JSTEngine(float[][] x, float[] target, float lr, float targetError, int epoch, int jmlInput, int jmlHidden, float batasMin, float batasMax) {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);
        this.x = x;
        this.target = target;
        this.lr = lr;
        this.targetError = targetError;
        this.epoch = epoch;
        this.jmlInput = jmlInput;
        this.jmlHidden = jmlHidden;
        this.batasMax = batasMax;
        this.batasMin = batasMin;
        inisialisasiBobot();
    }

    public void setParameter(float[][] x, float[] target, float lr, float targetError, int epoch, int jmlInput, int jmlHidden, float batasMin, float batasMax) {
        this.x = x;
        this.target = target;
        this.lr = lr;
        this.targetError = targetError;
        this.epoch = epoch;
        this.jmlInput = jmlInput;
        this.jmlHidden = jmlHidden;
        this.batasMax = batasMax;
        this.batasMin = batasMin;
        inisialisasiBobot();
    }

    public float random(float min, float max) {
        float randomNum = r.nextInt() % max;
        randomNum = randomNum < min ? randomNum + max : randomNum;
        return randomNum;
    }

    public float sigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    public float format(float num) {
        return Float.valueOf(numberFormat.format(num));
    }

    void inisialisasiBobot() {
        v = new float[jmlInput][jmlHidden];
        v[0][0] = 0.9562f;
        v[0][1] = 0.7762f;
        v[0][2] = 0.1623f;
        v[0][3] = 0.2886f;
        v[1][0] = 0.1962f;
        v[1][1] = 0.6133f;
        v[1][2] = 0.0311f;
        v[1][3] = 0.9711f;
//        //bobot input-hidden
//        v = new float[jmlInput][jmlHidden];
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
//                v[i][j] = random(batasMin, batasMax);
//                System.out.println("v[" + i + "][" + j + "]:" + v[i][j]);
            }
        }
        v0 = new float[jmlHidden];
        v0[0] = 0.7496f;
        v0[1] = 0.3796f;
        v0[2] = 0.7256f;
        v0[3] = 0.1628f;
//        //bobot bias-hidden
//        v0 = new float[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
//            v0[i] = random(batasMin, batasMax);
//            System.out.println("v0[" + i + "]:" + v0[i]);
        }
//
        w = new float[jmlHidden];
        w[0] = 0.2280f;
        w[1] = 0.9585f;
        w[2] = 0.6799f;
        w[3] = 0.0550f;
//        //bobot hidden-output
//        w = new float[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
//            w[i] = random(batasMin, batasMax);
//            System.out.println("w[" + i + "]:" + w[i]);
        }
//
        w0 = 0.9505f;
//        //bobot bias-output
//        w0 = random(batasMin, batasMax);
//        System.out.println("w0:" + w0);
    }

    public void trainingJST() {
        float[] zIn = new float[jmlHidden];
        float[] z = new float[jmlHidden];
        float yIn, error = 0;
        float gammaResult, deltaW0;
        float[] deltaW = new float[jmlHidden];
        float[] gammaIn = new float[jmlHidden];
        float[] gamma = new float[jmlHidden];
        float[] deltaV0 = new float[jmlHidden];
        float[][] deltaV = new float[jmlInput][jmlHidden];
        int counter = 0;
        float[] xTemp=null;

        // uji kondisi
        do {
            for (int n = 0; n < x.length; n++) {
//                System.out.println("///////////////////////BOBOT AWAL:" + n);
//                showBobot();
                if (x.length > 1) {
                    xTemp = x[n];   // x data ke - n
                }else{
                    xTemp=x[0];
                }
                /////////////////////// INPUT - HIDDEN - OUTPUT ////////////////
                //hitung zIn
                yIn = 0;
                for (int i = 0; i < jmlHidden; i++) {
                    zIn[i] = 0;
                    for (int j = 0; j < xTemp.length; j++) {
                        zIn[i] += xTemp[j] * v[j][i];
//                        System.out.println("zIn[" + i + "]=x[" + j + "]*v[" + j + "][" + i + "]=" + xTemp[j] + " * " + v[j][i] + "=" + zIn[i]);
                    }
                    zIn[i] = v0[i] + zIn[i];
//                    System.out.println("zIn[" + i + "] = v0[" + i + "] + zIn[" + i + "]=" + v0[i] + " + " + zIn[i] + " =  " + zIn[i]);
                    z[i] = sigmoid(zIn[i]);
//                    System.out.println("z[" + i + "]:" + z[i]);
                    yIn += z[i] * w[i];
                }
                //hitung yIn dan y
                yIn = w0 + yIn;
//                System.out.println("yIn:" + yIn);
                y = sigmoid(yIn);
//                System.out.println("y:" + y);

                //////////////////// ERROR, MSE , GAMMA  //////////////////////////
                //hitung targetError,kuadrat targetError, dan gamma
                error = target[n] - y;
                mse = (float) Math.pow(error, 2);
                gammaResult = (target[n] - y) * sigmoid(yIn) * (1 - sigmoid(yIn));
                System.out.println("mse:" + mse);
//                System.out.println("gamma result:"+gammaResult);

                ///////////////////// PERBAIKAN BOBOT BARU ////////////////////////
                //hitung deltaW,w baru,deltaW0,w0 baru
                for (int i = 0; i < jmlHidden; i++) {
                    deltaW[i] = lr * gammaResult * z[i];
//                    System.out.println("deltaW["+i+"]:"+ deltaW[i]);
                }
                deltaW0 = lr * gammaResult;
                w0 += deltaW0;
//                System.out.println("deltaW0:"+deltaW0);


                //hitung gammaIn, gamma dan deltaV0
                for (int i = 0; i < jmlHidden; i++) {
                    gammaIn[i] = gammaResult * w[i];
//                    System.out.println("gammaIn["+i+"]:"+ gammaIn[i]);
                    gamma[i] = gammaIn[i] * sigmoid(zIn[i]) * (1 - sigmoid(zIn[i]));
//                    gamma[i] = gammaIn[i] * sigmoid(z[i]) * (1 - sigmoid(z[i]));
//                    System.out.println("gamma["+i+"]:"+ gamma[i]);
                    deltaV0[i] = lr * gamma[i];
//                    System.out.println("deltaV0["+i+"]:"+ deltaV0[i]);
                }

                //hitung deltaV, v baru, w baru
                for (int i = 0; i < xTemp.length; i++) {
                    for (int j = 0; j < jmlHidden; j++) {
                        deltaV[i][j] = lr * gamma[i] * xTemp[i];
//                        System.out.println("deltaV["+i+"]["+j+"]:"+deltaV[i][j]);
                        v[i][j] += deltaV[i][j];
//                        System.out.println("v["+i+"]["+j+"]:"+v[i][j]);
                    }
                }

                //hitung w baru,v0 baru
                for (int i = 0; i < jmlHidden; i++) {
                    v0[i] += deltaV0[i];
//                    System.out.println("v0["+i+"]:"+v0[i]);
                    w[i] += deltaW[i];
//                    System.out.println("w["+i+"]:"+w[i]);
                }
//                System.out.println("w0:"+w0);
//                 System.out.println("////////////// BOBOT AKHIR:"+n);
//                 showBobot();
                if (mse < targetError) {
                    System.out.println("break epoch ke -" + epoch);
                    break;
                }
            }
            counter++;
            System.out.println("mse:" + mse);
        } while (mse > targetError && counter < epoch);
        System.out.println("epoch:" + counter);
        System.out.println("////////////////// BOBOT FINAL:");
        showBobot();
    }

    public boolean recognizeJST(float[] input, float target) {
//        v[0][0] = 5.8716f;
//        v[0][1] = 3.6067f;
//        v[0][2] = 3.4877f;
//        v[0][3] = -0.0704f;
//        v[1][0] = -4.8532f;
//        v[1][1] = 2.8028f;
//        v[1][2] = -5.1943f;
//        v[1][3] = 0.7636f;
//
//        v0[0] = 2.4618f;
//        v0[1] = -0.3884f;
//        v0[2] = -1.4258f;
//        v0[3] = -0.6994f;
//
//        w[0] = -7.0997f;
//        w[1] = 3.5782f;
//        w[2] = 6.9212f;
//        w[3] = -0.7503f;
//
//        w0 = 0.6571f;

        float[] zIn = new float[jmlHidden];
        float[] z = new float[jmlHidden];
        float yIn = 0, yResult;
        boolean valid = false;
        //hitung zIn, z,yIn, y result
        for (int i = 0; i < jmlHidden; i++) {
            zIn[i] = 0;
            for (int j = 0; j < jmlInput; j++) {
                zIn[i] += input[j] * v[j][i];
            }
            zIn[i] = v0[i] + zIn[i];
            z[i] = sigmoid(zIn[i]);
            yIn += z[i] * w[i];
        }
        yIn = w0 + yIn;
        yResult = sigmoid(yIn);

        //menentukan cocok atau tidak cocok
        System.out.println("yResult=" + yResult);
        float result = Math.abs(yResult - target);
        if (result < 0.5f) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    public void showBobot() {
        System.out.println("lihat bobot:");
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                System.out.println("v[" + i + "][" + j + "]:" + v[i][j]);
            }
        }
        for (int i = 0; i < jmlHidden; i++) {
            System.out.println("v0[" + i + "]:" + v0[i]);
        }
        for (int i = 0; i < jmlHidden; i++) {
            System.out.println("w[" + i + "]:" + w[i]);
        }
        System.out.println("w0:" + w0);
    }
}
