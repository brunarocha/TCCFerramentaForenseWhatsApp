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
import model.AcessoBanco;

/**
 *
 * @author BRUNA
 */
public class ConexaoJDBC {
    
    public static Connection getConexaoJDBC(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Classe com.mysql.Driver.jdbc não encontrada! "+ex);
        }
        
        try {
            AcessoBanco acesso = ManipulaArquivos.lerArquivoConfiguracaoBanco();
            
            return DriverManager.getConnection("jdbc:mysql://"+acesso.getHost()+":"+acesso.getPorta()+
                                               "/"+acesso.getBanco(), acesso.getUsuario(), acesso.getSenha());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Conexão com o banco de dados recusada! "+ex);
        }
        
        return null;
    }
    
}
