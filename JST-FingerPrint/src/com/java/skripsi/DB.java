/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db_jst_fz", "root", "admin");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertData(FingerPrint fp) {
        try {
            PreparedStatement psmnt = connection.prepareStatement("insert into tbl_fp values(NULL,?,?,?)");
            psmnt.setString(1, fp.getNama());
            File imgFile = new File("img_"+fp.getNama());
            /*FileOutputStream fos = new FileOutputStream(imgFile);
            fos.write(fp.getImage());
            fos.flush();
            fos.close();*/
            FileInputStream fis = new FileInputStream(imgFile);
            psmnt.setBinaryStream(2, (InputStream) fis, (int) (fp.getImage().length));
            psmnt.setString(3, fp.getPola());
            psmnt.executeUpdate();
        } catch (IOException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteData(int id) {
        try {
            PreparedStatement psmnt = connection.prepareStatement("delete from tbl_fp where id_fp=?");
            psmnt.setInt(1, id);
            psmnt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<FingerPrint> getData() {
        ArrayList<FingerPrint> list = new ArrayList<FingerPrint>();
        FingerPrint fp;
        byte[] buf = new byte[1024];
        InputStream in;
        int n;
        try {
            PreparedStatement psmnt = connection.prepareStatement("select * from tbl_fp");
            ResultSet rs = psmnt.executeQuery();
            while (rs.next()) {
                fp = new FingerPrint();
                fp.setId(rs.getInt("id_fp"));
                fp.setNama(rs.getString("nama"));
                Blob blobImg = rs.getBlob("image");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                in = blobImg.getBinaryStream();
                n = 0;
                while ((n = in.read(buf)) >= 0) {
                    baos.write(buf, 0, n);
                }
                in.close();
                fp.setImage(baos.toByteArray());
                fp.setPola(rs.getString("target"));
                list.add(fp);
            }
        } catch (IOException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public FingerPrint getData(int id) {
        FingerPrint fp = null;
        byte[] buf = new byte[1024];
        InputStream in;
        int n;
        try {
            PreparedStatement psmnt = connection.prepareStatement("select * from tbl_fp where id_fp=?");
            psmnt.setInt(1, id);
            ResultSet rs = psmnt.executeQuery();
            while (rs.next()) {
                fp = new FingerPrint();
                fp.setId(rs.getInt("id_fp"));
                fp.setNama(rs.getString("nama"));
                Blob blobImg = rs.getBlob("image");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                in = blobImg.getBinaryStream();
                n = 0;
                while ((n = in.read(buf)) >= 0) {
                    baos.write(buf, 0, n);
                }
                in.close();
                fp.setImage(baos.toByteArray());
                fp.setPola(rs.getString("target"));
            }
        } catch (IOException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fp;
    }
}
