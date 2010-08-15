/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

/**
 *
 * @author omega
 */
public class Parameter {

    int maxEpoch;
    double target;
    double learningRate;
    int numHiddenLayer;
    double minRandom;
    double maxRandom;

    public Parameter() {
        setToDefault();
    }

    public Parameter(int maxEpoch, double target, double learningRate, int numHiddenLayer, double minRandom, double maxRandom) {
        this.maxEpoch = maxEpoch;
        this.target = target;
        this.learningRate = learningRate;
        this.numHiddenLayer = numHiddenLayer;
        this.minRandom = minRandom;
        this.maxRandom = maxRandom;
    }

    public void setToDefault() {
        this.maxEpoch = 1000;
        this.target = 0.01;
        this.learningRate = 1;
        this.numHiddenLayer = 6;
        this.minRandom = 0;
        this.maxRandom = 0.35;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getMaxEpoch() {
        return maxEpoch;
    }

    public void setMaxEpoch(int maxEpoch) {
        this.maxEpoch = maxEpoch;
    }

    public double getMaxRandom() {
        return maxRandom;
    }

    public void setMaxRandom(double maxRandom) {
        this.maxRandom = maxRandom;
    }

    public double getMinRandom() {
        return minRandom;
    }

    public void setMinRandom(double minRandom) {
        this.minRandom = minRandom;
    }

    public int getNumHiddenLayer() {
        return numHiddenLayer;
    }

    public void setNumHiddenLayer(int numHiddenLayer) {
        this.numHiddenLayer = numHiddenLayer;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }
}
