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
}
