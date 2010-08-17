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
public class JSTEngine implements LogInterface {

    private String log;
    public double lr, targetError = 0, mse = 0;              // learning rate ,targetError, kuadrat target error/MSE(Mean Square Error
    public int epoch, jmlInput, jmlHidden, jmlOutput;       // jumlah pengulangan maksimum,jumlah neuron input,bias,output yg digunakan
    public double[] v0, w0;                               // bobot bobot bias input-hidden, bobot bias hidden-output,output
    public double[][] v, w, x, target;                       // bobot input-hidden, bobot hidden-output, input, target
    public double batasMax, batasMin;                        // batas maksimum/minimum nilai random
    private Random r = new Random();
    private NumberFormat numberFormat;
    double []arrMse;

    public JSTEngine() {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);
    }

    public JSTEngine(double[][] x, double[][] target, double lr, double targetError, int epoch, int jmlInput, int jmlHidden, int jmlOutput, double batasMin, double batasMax) {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);
        setParameter(x, target, lr, targetError, epoch, jmlInput, jmlHidden, jmlOutput, batasMin, batasMax);
        inisialisasiBobot();
    }

    public void setParameter(double[][] x, double[][] target, double lr, double targetError, int epoch, int jmlInput, int jmlHidden, int jmlOutput, double batasMin, double batasMax) {
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
        this.arrMse = new double[epoch];
        inisialisasiBobot();
        log = "";
        showInput();
        showTarget();
        log +=
                "-Learning Rate:" + this.lr + "\n"
                + "-Target Error:" + this.targetError + "\n"
                + "-Maksimum Epoch:" + this.epoch + "\n"
                + "-Jumlah Input:" + this.jmlInput + "\n"
                + "-Jumlah Hidden:" + this.jmlHidden + "\n"
                + "-Jumlah Output:" + this.jmlOutput + "\n"
                + "-Batas Random Maksimum:" + this.batasMax + "\n"
                + "-Batas Random Minimum:" + this.batasMin + "\n"
                + "-Bobot Awal:\n";
        showBobot();
    }

    public double random(double min, double max) {
        double randomNum = r.nextInt() % max;
        randomNum = randomNum < min ? randomNum + max : randomNum;
        return randomNum;
    }

    public double sigmoid(double x) {
        return (double) (1 / (1 + Math.exp(-x)));
    }

    public double format(double num) {
        return Double.parseDouble(numberFormat.format(num));
    }

    public String formatString(double num) {
        return (String.valueOf(num)).substring(0, 7);
    }

    void inisialisasiBobot() {
        //bobot input-hidden
        v = new double[jmlInput][jmlHidden];
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                v[i][j] = random(batasMin, batasMax);
            }
        }

        //bobot bias input-hidden
        v0 = new double[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
            v0[i] = random(batasMin, batasMax);
        }

        //bobot hidden-output
        w = new double[jmlHidden][jmlOutput];
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                w[i][j] = random(batasMin, batasMax);
            }
        }

        //bobot bias hidden-output
        w0 = new double[jmlOutput];
        for (int i = 0; i < jmlOutput; i++) {
            w0[i] = random(batasMin, batasMax);
        }
    }

    public void trainingJST() {
        double error = 0;
        double[] zIn = new double[jmlHidden];
        double[] z = new double[jmlHidden];
        double[] yIn = new double[jmlOutput];
        double[] y = new double[jmlOutput];
        double[] gammaOut = new double[jmlOutput];
        double[][] deltaW = new double[jmlHidden][jmlOutput];
        double[] deltaW0 = new double[jmlOutput];
        double[] gammaIn = new double[jmlHidden];
        double[] gamma = new double[jmlHidden];
        double[] deltaV0 = new double[jmlHidden];
        double[][] deltaV = new double[jmlInput][jmlHidden];
        int counter = 0;
        double[] xTemp = null;
        double[] tTemp = null;
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
                    }
                    zIn[i] = v0[i] + zIn[i];
                    z[i] = sigmoid(zIn[i]);
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
                error = 0;
                for (int i = 0; i < jmlOutput; i++) {
                    error += Math.pow(tTemp[i] - y[i], 2) * 0.5;    //0.5 = degree of freedom for error
                }
                mse = error;

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
                gammaIn = new double[jmlHidden];
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
                    break;
                }
            }
            counter++;
            arrMse[counter]=mse;
            log += "MSE ke-" + counter + ":" + mse + "\n";
        } while (mse > targetError && counter < epoch);
        log += "-Selesai pada epoch ke-" + counter + " dengan MSE:" + mse + "\n"
                + "-Bobot Akhir:\n";
        showBobot();
    }

    public double[] recognizeJST(double[] input) {
        double[] zIn = new double[jmlHidden];
        double[] z = new double[jmlHidden];
        double[] yIn = new double[jmlOutput];
        double[] y = new double[jmlOutput];
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

        showRecognition(input, z, y);
        return y;
    }

    public void showRecognition(double[] input, double[] z, double[] y) {
        log = "";
        log += "-Input:\n";
        for (int i = 0; i < input.length; i++) {
            log += i != (input.length - 1) ? input[i] + "," : input[i];
        }
        log += "-Hidden:\n";
        for (int i = 0; i < jmlHidden; i++) {
            log += "z[" + i + "]:" + z[i];
        }
        log += "-Output:\n";
        for (int i = 0; i < jmlOutput; i++) {
            log += "y[" + i + "]:" + y[i];
        }
    }

    public void showInput() {
        log += "-Input:\n";
        for (int i = 0; i < x.length; i++) {
            log += "Input ke-" + i + ":[";
            for (int j = 0; j < x[i].length; j++) {
                log += j != (x[i].length - 1) ? x[i][j] + "," : x[i][j];
            }
            log += "]\n";
        }
    }

    public void showTarget() {
        log += "-Target:\n";
        for (int i = 0; i < target.length; i++) {
            log += "Target ke-" + i + ":[";
            for (int j = 0; j < target[i].length; j++) {
                log += j != (target[i].length - 1) ? target[i][j] + "," : target[i][j];
            }
            log += "]\n";
        }
    }

    public void showBobot() {
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                log += "v[" + i + "][" + j + "]:" + v[i][j] + "\n";
            }
        }
        log += "\n";
        for (int i = 0; i < jmlHidden; i++) {
            log += "v0[" + i + "]:" + v0[i] + "\n";
        }
        log += "\n";
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                log += "w[" + i + "][" + j + "]:" + w[i][j] + "\n";
            }
        }
        log += "\n";
        for (int i = 0; i < jmlOutput; i++) {
            log += "w0[" + i + "]:" + w0[i] + "\n";
        }
    }

    public String getBobot(String delimiter) {
        String result = "";
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                result += formatString(v[i][j]) + delimiter;
            }
        }
        for (int i = 0; i < jmlHidden; i++) {
            result += formatString(v0[i]) + delimiter;
        }
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                result += formatString(w[i][j]) + delimiter;
            }
        }
        for (int i = 0; i < jmlOutput; i++) {
            result += i != jmlOutput - 1 ? formatString(w0[i]) + delimiter : formatString(w0[i]);
        }
        return result;
    }

    public String getLog() {
        return log;
    }

    public void setBobotRecognize(String strBobot, String delimiter, int jmlInput, int jmlOutput) {
        String[] arrBobot = strBobot.split(delimiter);
        this.jmlInput = jmlInput;
        this.jmlOutput = jmlOutput;
        this.jmlHidden = (arrBobot.length - (jmlOutput)) / (jmlInput + jmlOutput + 1);

        int counter = 0;
        v = new double[jmlInput][jmlHidden];
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                v[i][j] = Double.parseDouble(arrBobot[counter]);
                counter++;
            }
        }

        v0 = new double[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
            v0[i] = Double.parseDouble(arrBobot[counter]);
            counter++;
        }

        w = new double[jmlHidden][jmlOutput];
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                w[i][j] = Double.parseDouble(arrBobot[counter]);
                counter++;
            }
        }

        w0 = new double[jmlOutput];
        for (int i = 0; i < jmlOutput; i++) {
            w0[i] = Double.parseDouble(arrBobot[counter]);
            counter++;
        }
    }

    public double[] round(double[] result) {
        double[] hasil = new double[result.length];
        double threshold = 0.5;
        for (int i = 0; i < result.length; i++) {
            hasil[i] = result[i] < threshold ? 0 : 1;
        }
        return hasil;
    }

    public boolean match(double[] target, double[] result) {
        boolean valid = true;
        for (int i = 0; i < result.length; i++) {
            if (target[i] != result[i]) {
                return false;
            }
        }
        return valid;
    }

    public double[] getChartData(){
        return arrMse;
    }
}
