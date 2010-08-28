/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

public class FingerPrint {

    private int id;
    private String nama;
    private byte[] image;
    private String bobot1;
    private String bobot2;
    private String bobot3;

    public FingerPrint() {
        this.bobot1 = "";
        this.bobot2 = "";
        this.bobot3 = "";
    }

    public FingerPrint(String nama, byte[] image, String bobot1, String bobot2, String bobot3) {
        this.nama = nama;
        this.image = image;
        this.bobot1 = bobot1;
        this.bobot2 = bobot2;
        this.bobot3 = bobot3;
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

    public String getBobot1() {
        return bobot1;
    }

    public void setBobot1(String bobot1) {
        this.bobot1 = bobot1;
    }

    public String getBobot2() {
        return bobot2;
    }

    public void setBobot2(String bobot2) {
        this.bobot2 = bobot2;
    }

    public String getBobot3() {
        return bobot3;
    }

    public void setBobot3(String bobot3) {
        this.bobot3 = bobot3;
    }

    public void setBobots(String[] bobots) {
        for (int i = 0; i < bobots.length; i++) {
            switch (i) {
                case 0:
                    bobot1 = bobots[0];
                    break;
                case 1:
                    bobot2 = bobots[1];
                    break;
                case 2:
                    bobot3 = bobots[2];
                    break;
            }
        }
    }

    public String[] getBobots() {
        String[] bobots = new String[getNumBobots()];
        int i = 0;
        if (bobot1 != null) {
            if (!bobot1.trim().equals("")) {
                bobots[i] = bobot1;
                i++;
            }
        }
        if (bobot2 != null) {
            if (!bobot2.trim().equals("")) {
                bobots[i] = bobot2;
                i++;
            }
        }
        if (bobot3 != null) {
            if (!bobot3.trim().equals("")) {
                bobots[i] = bobot3;
            }
        }
        return bobots;
    }

    public int getNumBobots() {
        int numBobots = 0;
        if (bobot1 != null) {
            if (!bobot1.trim().equals("")) {
                numBobots++;
            }
        }
        if (bobot2 != null) {
            if (!bobot2.trim().equals("")) {
                numBobots++;
            }
        }
        if (bobot3 != null) {
            if (!bobot3.trim().equals("")) {
                numBobots++;
            }
        }
        return numBobots;
    }
}
