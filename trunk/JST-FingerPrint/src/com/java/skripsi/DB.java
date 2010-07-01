/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.skripsi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andi Taru NNW
 */
public class DB {
    Connection connection;

    public DB() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db_jst_fz", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertData(String nama, File image) {
        try {
            PreparedStatement psmnt = connection.prepareStatement("insert into tbl_fp values(NULL,?,?,'')");
            psmnt.setString(1, "nama");
            FileInputStream fis = new FileInputStream(image);
            psmnt.setBinaryStream(2, (InputStream)fis, (int)(image.length()));
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
