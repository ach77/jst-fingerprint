/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.util.ArrayList;

public class ImageProcessor {

    public ArrayList<int[][]> list;
    private int[][] group0 = {{1, 1, 0, 0}, {0, 0, 1, 1}};
    private int[][] group45 = {{1, 1, 1, 0}, {0, 1, 1, 1}, {0, 0, 0, 1}, {1, 0, 0, 0}, {0, 1, 1, 0}};
    private int[][] group90 = {{0, 1, 0, 1}, {1, 0, 1, 0}};
    private int[][] group45m = {{1, 1, 0, 1}, {1, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 0, 0}, {1, 0, 0, 1}};
    private Object[] arrGroup = {group0, group45, group90, group45m};

    private double calculate(int[][] data) {
        double jml0 = 0, jml45 = 0, jml90 = 0, jml45m = 0;
        int[][] tmpGroup = null;
        int[] tmp = new int[4];
        int k;
        boolean isGroup;
        for (int m = 0; m < data.length; m += 2) {
            if (m + 2 > data.length) {
                break;
            }
            for (int n = 0; n < data[m].length; n += 2) {
                if (n + 2 > data[m].length) {
                    break;
                }
                k = 0;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        tmp[k] = data[m + i][n + j];
                        k++;
                    }
                }

                //bandingkan array
                tmpGroup = null;
                for (int i = 0; i < arrGroup.length; i++) {
                    tmpGroup = (int[][]) arrGroup[i];
                    for (int j = 0; j < tmpGroup.length; j++) {
                        isGroup = true;
                        for (int l = 0; l < tmpGroup[j].length; l++) {
                            if (tmp[l] != tmpGroup[j][l]) {
                                isGroup = false;
                            }
                        }
                        if (isGroup) {
                            switch (i) {
                                case 0:
                                    jml0++;
                                    break;
                                case 1:
                                    jml45++;
                                    break;
                                case 2:
                                    jml90++;
                                    break;
                                case 3:
                                    jml45m++;
                                    break;
                            }
                            break;
                        }
                    }
                }
            }
        }
        double result = 0;
        try {
            if ((jml0 + jml45 + jml90 + jml45m) == 0) {
                result = 0;
            } else {
//                result = ((jml0 * 0) + (jml45 * 45) + (jml90 * 90) + (jml45m * -45)) / (jml0 + jml45 + jml90 + jml45m);
                result = ((jml0 * 0) + (jml45 * 1.57) + (jml90 * 3.14) + (jml45m * -1.57)) / (jml0 + jml45 + jml90 + jml45m);
            }
        } catch (ArithmeticException e) {
            result = 0;
        }
        return result;
    }

    public double[] divideImageArray(int[][] imgArray) {

        double[] result = new double[19];
        //pola 1 dan 2
        list = new ArrayList<int[][]>();
        list = slice(imgArray, 2, 1, list);
        list = slice(imgArray, 1, 2, list);
        list = slice(imgArray, 3, 1, list);
        list = slice(imgArray, 1, 3, list);
        list = slice(imgArray, 3, 3, list);

        int tmp[][];
        for (int i = 0; i < list.size(); i++) {
            tmp = (int[][]) list.get(i);
            result[i] = calculate(tmp);
        }
        return result;
    }

    public ArrayList<int[][]> getListArrayImage() {
        return list;
    }

    public static ArrayList<int[][]> slice(int[][] array, int row, int column, ArrayList<int[][]> listData) {
        int width, height;
        width = array[0].length;
        height = array.length;
        int tmp[][] = new int[height / row][width / column];

        int startCol = 0, startRow = 0, j = 0, k = 0;
        for (int n = 0; n < row; n++) {
            startRow = n * (height / row);
            for (int m = 0; m < column; m++) {
                startCol = m * (width / column);
                for (j = 0; j < (height / row); j++) {
                    for (k = 0; k < (width / column); k++) {
                        tmp[j][k] = array[startRow + j][startCol + k];
                    }
                }
                listData.add(tmp);
                tmp = new int[height / row][width / column];
            }
        }

        return listData;
    }
}
