/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.text.NumberFormat;

public class Converter {

    public static NumberFormat numberFormat;

    /**
     * mengeset angka dibelakang koma
     * @param fraction jumlah angka dibelakang koma
     */
    public static void setFraction(int fraction){
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(fraction);
    }
    
    /**
     * mengubah karakter koma (,) menjadi titik (.) 
     * digunakan untuk memparsing string menjadi double
     * @param num 
     * @return
     */
    public static String formatString(double num) {
        return numberFormat.format(num).replace(",", ".");
    }

    /**
     * mengubah angka desimal menjadi angka biner
     * @param decimal   anka desimal
     * @param digit     jumlah digit biner yg dihasilkan
     * @return
     */
    public static String decimalToBinary(int decimal, int digit) {
        String result = "";
        int sisa;
        while (decimal != 0) {
            sisa = decimal % 2;
            decimal = decimal / 2;
            result = sisa + result;
        }
        if (result.length() < digit) {
            int tmp = digit - result.length();
            for (int i = 0; i < tmp; i++) {
                result = "0" + result;
            }
        }
        return result;
    }

    /**
     * mengubah angka biner menjadi desimal
     * @param binary angka biner
     * @return
     */
    public static int binaryToDecimal(String binary) {
        int result = 0, tmp;
        for (int i = 0; i < binary.length(); i++) {
            tmp = Integer.valueOf(binary.charAt(binary.length() - (1 + i))) % 48;
            if (tmp < 0 || tmp > 1) {
                return -1;
            }
            result += tmp * Math.pow(2, i);
        }
        return result;
    }

    /**
     * mengubah string menjadi array double
     * @param string
     * @return
     */
    public static double[] stringToArrayDouble(String string) {
        double[] array = new double[string.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = Double.parseDouble(String.valueOf(string.charAt(i)));
        }
        return array;
    }

    /**
     * validasi nilai biner
     * @param str
     * @return
     */
    public static boolean cekBiner(String str) {
        boolean valid = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' || str.charAt(i) != '1') {
                return false;
            }
        }
        return valid;
    }
}
