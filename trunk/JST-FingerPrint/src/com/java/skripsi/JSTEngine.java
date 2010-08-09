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

    //    public static final float E = 2.718f;             // konstanta e/bilangan Euler
    public float lr, targetError = 0, mse = 0;              // learning rate ,targetError, kuadrat target error/MSE(Mean Square Error
    public int epoch, jmlInput, jmlHidden, jmlOutput;       // jumlah pengulangan maksimum,jumlah neuron input,bias,output yg digunakan
    public float[] v0, w0;                               // bobot bobot bias input-hidden, bobot bias hidden-output,output
    public float[][] v, w, x, target;                       // bobot input-hidden, bobot hidden-output, input, target
    public float batasMax, batasMin;                        // batas maksimum/minimum nilai random
    private Random r = new Random();
    private NumberFormat numberFormat;

    public JSTEngine(float[][] x, float[][] target, float lr, float targetError, int epoch, int jmlInput, int jmlHidden, int jmlOutput, float batasMin, float batasMax) {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);
        this.x = x;
        this.target = target;
        this.lr = lr;
        this.targetError = targetError;
        this.epoch = epoch;
        this.jmlInput = jmlInput;
        this.jmlHidden = jmlHidden;
        this.jmlOutput = jmlOutput;
        this.batasMax = batasMax;
        this.batasMin = batasMin;
        inisialisasiBobot();
    }

    public void setParameter(float[][] x, float[][] target, float lr, float targetError, int epoch, int jmlInput, int jmlHidden, int jmlOutput, float batasMin, float batasMax) {
        this.x = x;
        this.target = target;
        this.lr = lr;
        this.targetError = targetError;
        this.epoch = epoch;
        this.jmlInput = jmlInput;
        this.jmlHidden = jmlHidden;
        this.jmlOutput = jmlOutput;
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
        //bobot input-hidden
        v = new float[jmlInput][jmlHidden];
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                v[i][j] = random(batasMin, batasMax);
            }
        }

        //bobot bias input-hidden
        v0 = new float[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
            v0[i] = random(batasMin, batasMax);
        }

        //bobot hidden-output
        w = new float[jmlHidden][jmlOutput];
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                w[i][j] = random(batasMin, batasMax);
            }
        }

        //bobot bias hidden-output
        w0 = new float[jmlOutput];
        for (int i = 0; i < jmlOutput; i++) {
            w0[i] = random(batasMin, batasMax);
        }
    }

    public void trainingJST() {
        float error = 0;
        float[] zIn = new float[jmlHidden];
        float[] z = new float[jmlHidden];
        float[] yIn = new float[jmlOutput];
        float[] y = new float[jmlOutput];
        float[] gammaOut = new float[jmlOutput];
        float[][] deltaW = new float[jmlHidden][jmlOutput];
        float[] deltaW0 = new float[jmlOutput];
        float[] gammaIn = new float[jmlHidden];
        float[] gamma = new float[jmlHidden];
        float[] deltaV0 = new float[jmlHidden];
        float[][] deltaV = new float[jmlInput][jmlHidden];
        int counter = 0;
        float[] xTemp = null;
        float[] tTemp = null;
        int jmlData = x.length;     // bisa juga dengan target.length

        // uji kondisi
        do {
            for (int n = 0; n < jmlData; n++) {
                // input dan target data ke n
                xTemp = x[n];
                tTemp = target[n];
                /////////////////////// FEED FORWARD PROPAGATION ////////////////
                //hitung zIn dan z
                for (int i = 0; i < jmlHidden; i++) {
                    zIn[i] = 0;
                    for (int j = 0; j < jmlInput; j++) {
                        zIn[i] += xTemp[j] * v[j][i];
//                        System.out.println("zIn[" + i + "]=x[" + j + "]*v[" + j + "][" + i + "]=" + xTemp[j] + " * " + v[j][i] + "=" + zIn[i]);
                    }
                    zIn[i] = v0[i] + zIn[i];
//                    System.out.println("zIn[" + i + "] = v0[" + i + "] + zIn[" + i + "]=" + v0[i] + " + " + zIn[i] + " =  " + zIn[i]);
                    z[i] = sigmoid(zIn[i]);
//                    System.out.println("z[" + i + "]:" + z[i]);
                }

                //hitung yIn dan y
                for (int i = 0; i < jmlOutput; i++) {
                    yIn[i] = 0;
                    for (int j = 0; j < jmlHidden; j++) {
                        yIn[i] += z[j] * w[j][i];
                    }
                    yIn[i] = w0[i] + yIn[i];
                    y[i] = sigmoid(yIn[i]);
                }

                //////////////////// FEED BACK PROPAGATION  ////////////////////
                //hitung SSE (Sum Squared Error) dan MSE (Mean Squared Error). MSE = SSE/degree of freedom for error
                error=0;
                for (int i = 0; i < jmlOutput; i++) {
                    error += Math.pow(tTemp[i] - y[i], 2) * 0.5;    //0.5 = degree of freedom for error
                }
                mse = error;
                System.out.println("mse:" + mse);
                // hitung gammaOut
                for (int i = 0; i < jmlOutput; i++) {
                    gammaOut[i] = (tTemp[i] - y[i]) * sigmoid(yIn[i]) * (1 - sigmoid(yIn[i]));
                }

                ///////////////////// PERBAIKAN BOBOT BARU ////////////////////////
                //hitung deltaW
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        deltaW[i][j] = lr * gammaOut[j] * z[i];
                    }
                }
                //hitung w0
                for (int i = 0; i < jmlOutput; i++) {
                    deltaW0[i] = lr * gammaOut[i];
                }
                //hitung gammaIn, gamma dan deltaV0
                gammaIn=new float[jmlHidden];
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        gammaIn[i] += gammaOut[j] * w[i][j];
                    }
                    gamma[i] = gammaIn[i] * sigmoid(zIn[i]) * (1 - sigmoid(zIn[i]));
                }

                //hitung deltaV
                for (int i = 0; i < jmlInput; i++) {
                    for (int j = 0; j < jmlHidden; j++) {
                        deltaV[i][j] = lr * gamma[j] * xTemp[i];
                    }
                }

                // hitung deltaV0
                for (int i = 0; i < jmlHidden; i++) {
                    deltaV0[i] = lr * gamma[i];

                }
                //hitung w baru
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        w[i][j] += deltaW[i][j];
                    }
                }
                //hitung v baru
                for (int i = 0; i < jmlInput; i++) {
                    for (int j = 0; j < jmlHidden; j++) {
                        v[i][j] += deltaV[i][j];
                    }
                }
                if (mse < targetError) {
                    System.out.println("break epoch ke -" + epoch);
                    break;
                }
            }
            counter++;
        } while (mse > targetError && counter < epoch);
        System.out.println("epoch:" + counter);
        System.out.println("////////////////// BOBOT FINAL:");
        showBobot();
    }

    public float[] recognizeJST(float[] input, float target) {
        float[] zIn = new float[jmlHidden];
        float[] z = new float[jmlHidden];
        float[] yIn = new float[jmlOutput];
        float[] y = new float[jmlOutput];
        //hitung zIn, z,yIn, y result
        for (int i = 0; i < jmlHidden; i++) {
            zIn[i] = 0;
            for (int j = 0; j < jmlInput; j++) {
                zIn[i] += input[j] * v[j][i];
            }
            zIn[i] = v0[i] + zIn[i];
            z[i] = sigmoid(zIn[i]);
        }

        for (int i = 0; i < jmlOutput; i++) {
            yIn[i] = 0;
            for (int j = 0; j < jmlHidden; j++) {
                yIn[i] += z[j] * w[j][i];
            }
            yIn[i] += w0[i];
            y[i] = sigmoid(yIn[i]);
        }

        return y;
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
            for (int j = 0; j < jmlOutput; j++) {
                System.out.println("w[" + i + "][" + j + "]:" + w[i][j]);
            }
        }
        for (int i = 0; i < jmlOutput; i++) {
            System.out.println("w0[" + i + "]:" + w0[i]);
        }
    }
}
