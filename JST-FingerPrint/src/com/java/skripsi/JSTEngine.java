/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.text.NumberFormat;
import java.util.Random;

public class JSTEngine implements LogInterface {

    private String log;                                     //log proses JST (training maupun recognize)
    public double lr, targetError = 0, mse = 0;             // learning rate ,targetError, kuadrat target error/MSE(Mean Square Error
    public int epoch, jmlInput, jmlHidden, jmlOutput;       // jumlah pengulangan maksimum,jumlah neuron input, jumlah neuron hidden, jumlah neuron output
    public double[] v0, w0;                                 // bobot bobot bias input-hidden, bobot bias hidden-output
    public double[][] v, w, x, target;                      // bobot input-hidden, bobot hidden-output, input, target
    public double batasMax, batasMin;                       // batas maksimum/minimum nilai random
    private Random r = new Random();                        //objek random
    private NumberFormat numberFormat;
    double[] arrMse;                                        //nilai array MSE

    public JSTEngine() {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);           //set pecahan menjadi 5 digit dibelakang koma
    }

    public JSTEngine(double[][] x, double[][] target, double lr, double targetError, int epoch, int jmlInput, int jmlHidden, int jmlOutput, double batasMin, double batasMax) {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(5);
        setParameter(x, target, lr, targetError, epoch, jmlInput, jmlHidden, jmlOutput, batasMin, batasMax);
        inisialisasiBobot();
    }

    /**
     * set parameter2 yg dibutuhkan dalam proses training JST
     * @param x             input
     * @param target        target
     * @param lr            learning rate
     * @param targetError   target error
     * @param epoch         jumlah maksimum perulangan
     * @param jmlInput      jumlah neuron input
     * @param jmlHidden     jumlah neuron hidden
     * @param jmlOutput     jumlah neuron output
     * @param batasMin      nilai minimum random
     * @param batasMax      nilai maksimum random
     */
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
        inisialisasiBobot();                //inisialisasi bobot awal secara random
        log = "";                           //bersihkan log
        createLogInput();                   //tulis log informasi input
        createLogTarget();                  //tulis log informasi target
        log +=                              //tulis log informasi parameter2 training JST
                "-Learning Rate:" + this.lr + "\n"
                + "-Target Error:" + this.targetError + "\n"
                + "-Maksimum Epoch:" + this.epoch + "\n"
                + "-Jumlah Input:" + this.jmlInput + "\n"
                + "-Jumlah Hidden:" + this.jmlHidden + "\n"
                + "-Jumlah Output:" + this.jmlOutput + "\n"
                + "-Batas Random Maksimum:" + this.batasMax + "\n"
                + "-Batas Random Minimum:" + this.batasMin + "\n"
                + "-Bobot Awal:\n";
        createLogBobot();                   //tulis log informasi bobot awal
    }

    /**
     * fungsi penghasil nilai random
     * @param min
     * @param max
     * @return
     */
    public double random(double min, double max) {
        double randomNum = r.nextInt() % max;
        randomNum = randomNum < min ? randomNum + max : randomNum;
        return randomNum;
    }

    /**
     * fungsi sigmoid digunakan sigmoid bipolar
     * @param x
     * @return
     */
    public double sigmoid(double x) {
        return (double) (1 / (1 + Math.exp(-x)));
    }

    /**
     * mengubah nilai double menjadi string
     * @param num
     * @return
     */
    public String formatString(double num) {
        return (String.valueOf(num)).substring(0, 7);
    }

    /**
     * mengeset bobot-bobot awal secara random
     */
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

    /**
     * fungsi untuk melakukan training JST
     */
    public void trainingJST() {
        double error = 0;
        double[] zIn = new double[jmlHidden];               //keluaran untuk lapisan hidden
        double[] z = new double[jmlHidden];                 //unit lapisan hidden
        double[] yIn = new double[jmlOutput];               //keluaran untuk lapisan output
        double[] y = new double[jmlOutput];                 //unit lapisan output
        double[] deltaK = new double[jmlOutput];            //faktor pengaturan nilai sambungan pada lapisan output
        double[][] deltaW = new double[jmlHidden][jmlOutput];//selisih bobot lapisan hidden-output
        double[] deltaW0 = new double[jmlOutput];           //selisih bobot bias lapisan hidden-output
        double[] deltaInJ = new double[jmlHidden];          //faktor pengaturan nilai sambungan pada keluaran lapisan hidden
        double[] deltaJ = new double[jmlHidden];            //faktor pengaturan nilai sambungan pada lapisan hidden
        double[] deltaV0 = new double[jmlHidden];           //selisih bobot bias lapisan input-hidden
        double[][] deltaV = new double[jmlInput][jmlHidden];//selisih bobot lapisan input-hidden
        int counter = 0;                                    //counter untuk jumlah epoch
        double[] xTemp = null;                              //variabel temporer untuk input
        double[] tTemp = null;                              //variabel temporer untuk target
        int jmlData = x.length;                             // jumlah data neuron yg ditraining

        // uji kondisi
        do {
            for (int n = 0; n < jmlData; n++) { //untuk input data ke n
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
                    error += Math.pow(tTemp[i] - y[i], 2) * 0.5; 
                }
                mse = error;

                // hitung gammaOut
                for (int i = 0; i < jmlOutput; i++) {
                    deltaK[i] = (tTemp[i] - y[i]) * sigmoid(yIn[i]) * (1 - sigmoid(yIn[i]));
                }

                ///////////////////// PERBAIKAN BOBOT BARU ////////////////////////
                //hitung deltaW
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        deltaW[i][j] = lr * deltaK[j] * z[i];
                    }
                }
                //hitung w0
                for (int i = 0; i < jmlOutput; i++) {
                    deltaW0[i] = lr * deltaK[i];
                }
                //hitung gammaIn, gamma dan deltaV0
                deltaInJ = new double[jmlHidden];
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        deltaInJ[i] += deltaK[j] * w[i][j];
                    }
                    deltaJ[i] = deltaInJ[i] * sigmoid(zIn[i]) * (1 - sigmoid(zIn[i]));
                }

                //hitung deltaV
                for (int i = 0; i < jmlInput; i++) {
                    for (int j = 0; j < jmlHidden; j++) {
                        deltaV[i][j] = lr * deltaJ[j] * xTemp[i];
                    }
                }

                // hitung deltaV0
                for (int i = 0; i < jmlHidden; i++) {
                    deltaV0[i] = lr * deltaJ[i];
                }
                //hitung w baru
                for (int i = 0; i < jmlHidden; i++) {
                    for (int j = 0; j < jmlOutput; j++) {
                        w[i][j] += deltaW[i][j];
                    }
                }

                //hitung w0 baru
                for (int i = 0; i < jmlOutput; i++) {
                    w0[i] += deltaW0[i];
                }

                //hitung v baru
                for (int i = 0; i < jmlInput; i++) {
                    for (int j = 0; j < jmlHidden; j++) {
                        v[i][j] += deltaV[i][j];
                    }
                }

                //hitung v0 baru
                for (int i = 0; i < jmlHidden; i++) {
                    v0[i] += deltaV0[i];
                }
                if (mse < targetError) {
                    break;
                }
            }
            arrMse[counter] = mse;
            counter++;     //iterasi untuk jumlah epoch
            //tambahkan informasi ke log
            log += "MSE ke-" + counter + ":" + mse + "\n";
        } while (mse > targetError && counter < epoch);     //kondisi perulangan berhenti
        //tambahkan informasi ke log
        log += "-Selesai pada epoch ke-" + counter + " dengan MSE:" + mse + "\n"
                + "-Bobot Akhir:\n";
        createLogBobot();
    }

    /**
     * Mengenali inputan dengan bobot akhir yg dihasilkan dari proses JST
     * @param input
     * @return nilai output
     */
    public double[] recognizeJST(double[] input) {
        double[] zIn = new double[jmlHidden];   //keluaran untuk lapisan hidden
        double[] z = new double[jmlHidden];     //unit lapisan hidden
        double[] yIn = new double[jmlOutput];   //keluaran untuk lapisan output
        double[] y = new double[jmlOutput];     //unit lapisan output
        //hitung zIn, z,yIn, y result
        for (int i = 0; i < jmlHidden; i++) {
            zIn[i] = 0;
            for (int j = 0; j < jmlInput; j++) {
                zIn[i] += input[j] * v[j][i];
            }
            zIn[i] = v0[i] + zIn[i];
            z[i] = sigmoid(zIn[i]);
        }

        //hitung y dan yin
        for (int i = 0; i < jmlOutput; i++) {
            yIn[i] = 0;
            for (int j = 0; j < jmlHidden; j++) {
                yIn[i] += z[j] * w[j][i];
            }
            yIn[i] += w0[i];
            y[i] = sigmoid(yIn[i]);
        }

        //tambahkan informasi ke log
        createLogRecognition(input, z, y);
        return y;
    }

    /**
     * mengisi log dengan informasi proses recognition
     * @param input
     * @param z
     * @param y
     */
    public void createLogRecognition(double[] input, double[] z, double[] y) {
        log = "";
        log += "-Input:\n";
        for (int i = 0; i < input.length; i++) {
            log += i != (input.length - 1) ? input[i] + "," : input[i];
        }
        log += "\n-Hidden:\n";
        for (int i = 0; i < jmlHidden; i++) {
            log += "z[" + i + "]:" + z[i] + "\n";
        }
        log += "\n-Output:\n";
        for (int i = 0; i < jmlOutput; i++) {
            log += "y[" + i + "]:" + y[i] + "\n";
        }
    }

    /**
     * mengisi log dengan informasi neuron input
     */
    public void createLogInput() {
        log += "-Input:\n";
        for (int i = 0; i < x.length; i++) {
            log += "Input ke-" + i + ":[";
            for (int j = 0; j < x[i].length; j++) {
                log += j != (x[i].length - 1) ? x[i][j] + "," : x[i][j];
            }
            log += "]\n";
        }
    }

    /**
     * mengisi log dengan informasi target
     */
    public void createLogTarget() {
        log += "-Target:\n";
        for (int i = 0; i < target.length; i++) {
            log += "Target ke-" + i + ":[";
            for (int j = 0; j < target[i].length; j++) {
                log += j != (target[i].length - 1) ? target[i][j] + "," : target[i][j];
            }
            log += "]\n";
        }
    }

    /**
     * mengisi log dengan informasi bobot awal
     */
    public void createLogBobot() {
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

    /**
     * mendapatkan informasi bobot2 yg digunakan
     * @param delimiter delimiter untuk memisahkan bobot
     * @return string bobot dengan delimiter yg telah ditentukan
     */
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

    /**
     * mendapatkan log yg berupa informasi proses
     * @return
     */
    public String getLog() {
        return log;
    }

    /**
     * set parameter untuk proses recognize image
     * @param strBobot  bobot akhir
     * @param delimiter delimiter pemisah bobot
     * @param jmlInput
     * @param jmlOutput
     */
    public void setBobotRecognize(String strBobot, String delimiter, int jmlInput, int jmlOutput) {
        //dapatkan bobot akhir
        String[] arrBobot = strBobot.split(delimiter);
        this.jmlInput = jmlInput;
        this.jmlOutput = jmlOutput;
        //hitung jumlah hidden
        this.jmlHidden = (arrBobot.length - (jmlOutput)) / (jmlInput + jmlOutput + 1);

        int counter = 0;    //idx array bobot
        //mendapatkan bobot input-hidden
        v = new double[jmlInput][jmlHidden];
        for (int i = 0; i < jmlInput; i++) {
            for (int j = 0; j < jmlHidden; j++) {
                v[i][j] = Double.parseDouble(arrBobot[counter]);
                counter++;
            }
        }

        //mendapatkan bobot bias input-hidden
        v0 = new double[jmlHidden];
        for (int i = 0; i < jmlHidden; i++) {
            v0[i] = Double.parseDouble(arrBobot[counter]);
            counter++;
        }

        //mendapatkan bobot hidden-output
        w = new double[jmlHidden][jmlOutput];
        for (int i = 0; i < jmlHidden; i++) {
            for (int j = 0; j < jmlOutput; j++) {
                w[i][j] = Double.parseDouble(arrBobot[counter]);
                counter++;
            }
        }

        //mendapatkan bobot bias hidden-output
        w0 = new double[jmlOutput];
        for (int i = 0; i < jmlOutput; i++) {
            w0[i] = Double.parseDouble(arrBobot[counter]);
            counter++;
        }
    }

    /**
     * membulatkan nilai jika >0.5 menjadi 1 dan jika <0.5 menjadi 0
     * @param result
     * @return
     */
    public double[] round(double[] result) {
        double[] hasil = new double[result.length];
        double threshold = 0.5;
        for (int i = 0; i < result.length; i++) {
            hasil[i] = result[i] < threshold ? 0 : 1;
        }
        return hasil;
    }

    /**
     * mencocokan tiap elemen target dengan hasil recognition
     * @param target
     * @param result
     * @return  jika true, maka cocok, jika false maka tidak cocok
     */
    public boolean match(double[] target, double[] result) {
        boolean valid = true;
        for (int i = 0; i < result.length; i++) {
            if (target[i] != result[i]) {
                return false;
            }
        }
        return valid;
    }

    /**
     * mendapatkan array MSE
     * @return
     */
    public double[] getChartData() {
        return arrMse;
    }
}
