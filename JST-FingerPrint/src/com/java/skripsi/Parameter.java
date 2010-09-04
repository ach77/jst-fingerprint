/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;
/**
 * Class untuk menangani parameter JST
 */
public class Parameter {

    int maxEpoch;           //jumlah maksimum epoch
    double target;          //target
    double learningRate;    //learning rate
    int numHiddenLayer;     //jumlah hidden layer
    double minRandom;       //minimum random
    double maxRandom;       //maksimum random

    public Parameter() {
        //set nilai parameter menjadi default
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

    /**
     * mengeset nilai parameter menjadi default
     */
    public void setToDefault() {
        this.maxEpoch = 1000;
        this.target = 0.0001;
        this.learningRate = 0.5;
        this.numHiddenLayer = 20;
        this.minRandom = 0;
        this.maxRandom = 0.7;
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
