/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

/**
 *
 * @author Omega
 */
public class JSTEngine {

    int[][] group0 = {{1, 1, 0, 0}, {0, 0, 1, 1}};
    int[][] group45 = {{1, 1, 1, 0}, {0, 1, 1, 1}, {0, 0, 0, 1}, {1, 0, 0, 0}, {0, 1, 1, 0}};
    int[][] group90 = {{0, 1, 0, 1}, {1, 0, 1, 0}};
    int[][] group45m = {{1, 1, 0, 1}, {1, 0, 1, 1}, {0, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 0, 0}, {1, 0, 0, 1}};
    Object[] arrGroup = {group0, group45, group90, group45m};
    int jml0, jml45, jml90, jml45m;

    public void grouping(int[][] data) {
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
        System.out.println("result:" + jml0 + "," + jml45 + "," + jml90 + "," + jml45m);
    }

    public int getInput() {
        int result = 0;
        result = ((jml0 * 0) + (jml45 * 45) + (jml90 * 90) + (jml45m * -45)) / (jml0 + jml45 + jml90 + jml45m);
        return result;
    }
}
