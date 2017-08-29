/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author BRUNA
 */
public class ConexaoSQLite {
    
    public static Connection getConexaoSQLite(String url){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Classe org.sqlite.JDBC não encontrada! ");
        }
        
        try {
            return DriverManager.getConnection("jdbc:sqlite:"+url);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro criar conexão JDBC do SQLite! ");
            return null;
        }
    }
    
}