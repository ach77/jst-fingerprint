/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

/**
 *
 * @author omega
 */
public class Converter {

    public static String decimalToBinary(int decimal,int digit) {
        String result = "";
        int  sisa;
        while (decimal != 0) {
            sisa = decimal % 2;
            decimal = decimal / 2;
            result=sisa+result;
        }
        if(result.length()<digit){
            int tmp=digit-result.length();
            for (int i = 0; i < tmp; i++) {
                result="0"+result;
            }
        }
        return result;
    }

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

}
