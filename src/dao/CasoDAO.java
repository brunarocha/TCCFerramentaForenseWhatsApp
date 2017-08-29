/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Acesso;
import model.Caso;
import model.Usuario;
import util.ConexaoJDBC;
import util.ConverteData;

/**
 *
 * @author BRUNA
 */
public class CasoDAO {
    
    private final Connection connection;
    private PreparedStatement preparedStatement;
    
    public CasoDAO() {
        this.connection = ConexaoJDBC.getConexaoJDBC();
    }
    
    
    public boolean inserir(Caso caso){
        String sql = "CALL SP_INSERE_CASO (?, ?, ?) ";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1, caso.getDescricao());
            this.preparedStatement.setString(2, caso.getNome_investigado());
            this.preparedStatement.setString(3, caso.getDiretorio());
            this.preparedStatement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir novo caso! "+ex.getMessage());
            return false;
        }
    }
    
    
    public boolean excluir(Caso caso){
        String sql = "CALL  SP_EXCLUI_CASO (?) ";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setLong(1, caso.getId());
            this.preparedStatement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir caso! "+ex.getMessage());
            return false;
        }
    }
    
    
    
    public boolean relacionaCasoUsuario(Acesso tipoAcesso, Long idUsuario, Long idCaso){
        String sql = "CALL SP_INSERE_CASO_USUARIO (?, ?, ?) ";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1, tipoAcesso.toString());
            this.preparedStatement.setLong(2, idUsuario);
            this.preparedStatement.setLong(3, idCaso);
            this.preparedStatement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao relacionar caso com usuário! "+ex.getMessage());
            return false;
        }
    }
    
    
    public List<Caso> pesquisaListaCasos(Usuario usuario, Acesso acesso){
        String sql = "CALL SP_LISTA_CASOS (?, ?) ";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setLong(1, usuario.getId());
            this.preparedStatement.setString(2, acesso.toString());
            
            ResultSet resultSet = this.preparedStatement.executeQuery();
            List<Caso> casos = new ArrayList<>();
            
            while(resultSet.next()){
                casos.add(carregaObjetoCaso(resultSet, usuario));
            }
           
            return casos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar casos do usuário: "+usuario.getNome()+"! "+ex.getMessage());
            return null;
        }
    }
    
    
    public boolean verificaNomeCaso(String descricao){
        String sql = "CALL SP_VERIFICA_NOME_CASO(?)";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1, descricao);
            
            ResultSet resultSet = this.preparedStatement.executeQuery();
            resultSet.next();
            
            return resultSet.getLong(1) > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar nome do caso: "+descricao+"! "+ex.getMessage());
            return false;
        }
    }
    
    
    private Caso carregaObjetoCaso(ResultSet resultSet, Usuario usuario) throws SQLException{
        Caso caso = new Caso();
        caso.setId(resultSet.getLong("cas_id"));
        caso.setUsuario(usuario);
        caso.setDescricao(resultSet.getString("cas_descricao"));
        caso.setData_criacao(ConverteData.getCalendarDeDate(resultSet.getDate("cas_data_criacao")));
        caso.setNome_investigado(resultSet.getString("cas_nome_investigado"));
        caso.setDiretorio(resultSet.getString("cas_diretorio"));
        
        return caso;
    }
    
}