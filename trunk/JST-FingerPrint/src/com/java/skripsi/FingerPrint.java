/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

/**
 *
 * @author Andi Taru NNW
 */
public class FingerPrint {

    private int id;
    private String nama;
    private byte[] image;
    private String pola;

    public FingerPrint() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * @return the pola
     */
    public String getPola() {
        return pola;
    }

    /**
     * @param pola the pola to set
     */
    public void setPola(String pola) {
        this.pola = pola;
    }
}
