/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class DataTableModel extends AbstractTableModel {

    int row;
    String[] column = {"ID", "Nama", "Gambar", "Bobot1","Bobot2","Bobot3"};
    ArrayList<FingerPrint> list;
    Object[][] data;
    DB db;

    public DataTableModel(DB db) {
        //dapatkan data dari database
        this.db = db;
        list = db.getData();
        data = new Object[list.size()][column.length];
        FingerPrint fp;
        for (int i = 0; i < list.size(); i++) {
            fp = list.get(i);
            data[i][0] = fp.getId();
            data[i][1] = fp.getNama();
            data[i][2] = fp.getImage();
            data[i][3] = fp.getBobot1();
            data[i][4] = fp.getBobot2();
            data[i][5] = fp.getBobot3();
        }
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int idx) {
        return column[idx];
    }
}
