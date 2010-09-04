/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.util.ArrayList;

/**
 * Class yang digunakan untuk memecah image menjadi 19 bagian dan mengambil nilai derajat rata
 * -rata sebagai nilai input ke JST
 */
public class ImageProcessor {

    public ArrayList<int[][]> list;                                     //list untuk menampung image yg dipecah menjadi 19
    //pola yg dicari , misal 0 derajat adalah :
    // 1 1 dan 0 0      45 derajat : 1 1 dan 0 1 dan 0 0 dan 1 0 dan 0 1
    // 0 0     1 1                   1 0     1 1     0 1     0 0     1 0
    private int[][] group0 = {{1, 1, 0, 0}, {0, 0, 1, 1}};
    private int[][] group45 = {{1, 1, 1, 0}, {0, 1, 1, 1}, {0, 0, 0, 1}, {1, 0, 0, 0}, {0, 1, 1, 0}};
    private int[][] group90 = {{0, 1, 0, 1}, {1, 0, 1, 0}};
    private int[][] group45m = {{1, 1, 0, 1}, {1, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 0, 0}, {1, 0, 0, 1}};
    private Object[] arrGroup = {group0, group45, group90, group45m};   //array untuk menyimpan grup derajat

    /**
     * mengecek apakah terdapat nilai 1 di posisi row,col secara horizontal
     * @param data
     * @param row
     * @param col
     * @return jika ada angka 1, maka true, jika 0 semua, maka false
     */
    private boolean isValidHorizontal(int[][] data, int row, int col) {
        boolean result = false;
        for (int i = 0; i < 3; i++) {
            if (data[row][col + i] == 1) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * mengecek apakah terdapat nilai 1 di posisi row,col secara vertical
     * @param data
     * @param row
     * @param col
     * @return jika ada angka 1, maka true, jika 0 semua, maka false
     */
    private boolean isValidVertical(int[][] data, int row, int col) {
        boolean result = false;
        for (int i = 0; i < 3; i++) {
            if (data[row + i][col] == 1) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Untuk mengecek apakah disekitar titik (row,col) terdapat angka 1 atau tidak
     * @param data
     * @param row
     * @param col
     * @return jika terdapat angka 1 disekitar titik tersebut maka true, jika 0 semua maka false
     */
    public  boolean isHaveNeighbour(int[][] data, int row, int col) {
        int totRow = data.length;
        int totCol = data[0].length;
        boolean h1, h2, v1, v2;
        //deteksi apakah titik ada di kiri
        if (col - 1 > 0) {      //kanan atau tengah
            //deteksi apakah titik ada di atas
            if (row - 1 > 0) {  //tengah atau bawah
                //deteksi apakah titik ada di bawah
                if (totRow - (row + 1) < 2) {       //bawah
                    //deteksi apakah titik ada di kanan
                    if (totCol - (col + 1) < 2) {   //kanan-bawah
                        h1 = isValidHorizontal(data, row - 1, col - 1);
                        v1 = isValidVertical(data, row - 1, col - 1);
                        return h1 || v1;
                    } else {    //tengah-bawah
                        h1 = isValidHorizontal(data, row - 1, col - 1);
                        v1 = isValidVertical(data, row - 1, col - 1);
                        v2 = isValidVertical(data, row - 1, col + 2);
                        return h1 || v1 || v2;
                    }
                } else {  //tengah
                    if (totCol - (col + 1) < 2) {   //tengah-kanan
                        h1 = isValidHorizontal(data, row - 1, col - 1);
                        h2 = isValidHorizontal(data, row + 2, col - 1);
                        v1 = isValidVertical(data, row - 1, col - 1);
                        return h1 || h2 || v1;
                    } else {    //tengah-tengah
                        h1 = isValidHorizontal(data, row - 1, col);
                        h2 = isValidHorizontal(data, row + 2, col - 1);
                        v1 = isValidVertical(data, row - 1, col - 1);
                        v2 = isValidVertical(data, row , col + 2);
                        return h1 || h2 || v1 || v2;
                    }
                }
            } else {    //atas
                if (totCol - (col + 1) < 2) {   //atas-kanan
                    h1 = isValidHorizontal(data, row + 2, col - 1);
                    v1 = isValidVertical(data, row, col - 1);
                    return h1 || v1;
                } else {  //atas-tengah
                    h1 = isValidHorizontal(data, row + 2, col);
                    v1 = isValidVertical(data, row, col - 1);
                    v2 = isValidVertical(data, row, col + 2);
                    return h1 || v1 || v2;
                }
            }
        } else {    //kiri
            if (row - 1 > 0) {  //tengah || bawah
                if (totRow - (row + 1) < 2) {   //kiri-bawah
                    h1 = isValidHorizontal(data, row - 1, col);
                    v1 = isValidVertical(data, row - 1, col + 2);
                    return h1 || v1;
                } else {  //kiri-tengah
                    h1 = isValidHorizontal(data, row - 1, col);
                    h2 = isValidHorizontal(data, row + 2, col);
                    v1 = isValidVertical(data, row, col + 2);
                    return h1 || h2 || v1;
                }
            } else {    //kiri-atas
                h1 = isValidHorizontal(data, row + 2, col);
                v1 = isValidVertical(data, row, col + 2);
                return h1 || v1;
            }
        }
    }

    /**
     * menghitung nilai derajat rata2 untuk tiap pecahan image (image yg terbagi menjadi 19 bagian)
     * @param data  array int image
     * @return  derajat rata2
     */
    private double calculate(int[][] data) {
        double jml0 = 0, jml45 = 0, jml90 = 0, jml45m = 0;      //jumlah kelompok derajat
        int[][] tmpGroup = null;
        int[] tmp = new int[4];                                 //variabel temporary untuk mengecek pengelompokan pixel ke dalam kelompok derajat
        int k;                                                  //index variabel tmp
        boolean isGroup;                                        //flag apakah pixel 2x2 termasuk ke dalam kelompok derajat
        for (int m = 0; m < data.length; m += 2) {
            if (m + 2 > data.length) {
                break;
            }
            for (int n = 0; n < data[m].length; n += 2) {
                if (n + 2 > data[m].length) {
                    break;
                }
                if (!isHaveNeighbour(data, m, n)) {             //mengecek apakah disekitar pixel tersebut terdapat pixel 1 lain
                    continue;
                }
                k = 0;
                for (int i = 0; i < 2; i++) {                   //masukkan data ke dalam variabel temp
                    for (int j = 0; j < 2; j++) {
                        tmp[k] = data[m + i][n + j];
                        k++;
                    }
                }

                //kelompokkan array variabel temporer ke dalam kelompok derajat 
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
                        //jika pixel tersebut termasuk dalam salah satu kelompok derajat
                        if (isGroup) {
                            switch (i) {
                                case 0:     //kelompok derajat 0
                                    jml0++;
                                    break;
                                case 1:     //kelompok derajat 45
                                    jml45++;
                                    break;
                                case 2:     //kelompok derajat 90
                                    jml90++;
                                    break;
                                case 3:     //kelompok derajat -45
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
            //menghitung jumlah derajat rata2
            if ((jml0 + jml45 + jml90 + jml45m) == 0) {
                result = 0;
            } else {
                result = ((jml0 * 0) + (jml45 * 1.57) + (jml90 * 3.14) + (jml45m * -1.57)) / (jml0 + jml45 + jml90 + jml45m);
            }
        } catch (ArithmeticException e) {
            result = 0;
        }
        return result;
    }

    /**
     * memecah image menjadi 19 bagian dan mendapatkan nilai derajat rata2 dari tiap bagian image
     * @param imgArray  image yg akan dipecah
     * @return  nilai array derajat rata2
     */
    public double[] divideImageArray(int[][] imgArray) {
        double[] result = new double[19];           //variabel derajat rata2
        //pola 1 dan 2
        list = new ArrayList<int[][]>();            //variabel penampung image
        list = slice(imgArray, 2, 1, list);         //pecah image 2 bagian atas-bawah
        list = slice(imgArray, 1, 2, list);         //pecah image 2 bagian kiri-kanan
        list = slice(imgArray, 3, 1, list);         //pecah image 3 bagian atas-tengah-bawah
        list = slice(imgArray, 1, 3, list);         //pecah image 3 bagian kiri-tengah-kanan
        list = slice(imgArray, 3, 3, list);         //pecah image 9 bagian

        //menghitung derajat rata2
        int tmp[][];
        for (int i = 0; i < list.size(); i++) {
            tmp = (int[][]) list.get(i);
            result[i] = calculate(tmp);
        }
        return result;
    }

    /**
     * mendapatkan list image dengan representasi array integer
     * @return
     */
    public ArrayList<int[][]> getListArrayImage() {
        return list;
    }

    /**
     * memotong-potong image sejumlah row*column
     * @param array     image yg dipecah
     * @param row       jumlah row
     * @param column    jumlah column
     * @param listData  list image yg lain
     * @return
     */
    public static ArrayList<int[][]> slice(int[][] array, int row, int column, ArrayList<int[][]> listData) {
        int width, height;
        width = array[0].length;                                //lebar image
        height = array.length;                                  //tinggi image
        int tmp[][] = new int[height / row][width / column];    //variabel temporary untuk menyimpan nilai image

        //pecah berdasarkan row dan column yg diinginkan
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
                listData.add(tmp);                              //tambahkan data tmp ke list
                tmp = new int[height / row][width / column];
            }
        }

        return listData;
    }
}
