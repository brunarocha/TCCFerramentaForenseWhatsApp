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
import model.Contato;
import util.ConexaoJDBC;

/**
 *
 * @author BRUNA
 */
public class ContatoDAO {
    
    private final String tabelaContato;
    private PreparedStatement preparedStatement;

    
    public ContatoDAO(String tabelaContato) {
        this.tabelaContato = tabelaContato;
    }
    
    
    public boolean insereContato(Contato contato, Long dispositivoID){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            String sql = "INSERT INTO "+tabelaContato+" (CTT_ID, CTT_JID, CTT_STATUS, CTT_STATUS_DATE, CTT_STATUS_TIME, CTT_NUMBER, CTT_DISPLAY_NAME, CTT_WA_NAME, CTT_DPS_ID) " +
                         " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, contato.getId());
            preparedStatement.setString(2, contato.getJid());
            preparedStatement.setString(3, contato.getStatus());
            preparedStatement.setDate(4, contato.getStatus_date());
            preparedStatement.setTime(5, contato.getStatus_time());
            preparedStatement.setString(6, contato.getNumber());
            preparedStatement.setString(7, contato.getDisplay_name());
            preparedStatement.setString(8, contato.getWa_name());
            preparedStatement.setLong(9, dispositivoID);
            preparedStatement.execute();
            
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao importar contato: "+contato+"! "+ex);
            return false;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public boolean criaTabelaContato(){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_CRIA_TABELA_CONTATO (?)");
            preparedStatement.setString(1, tabelaContato);
            preparedStatement.execute();
            
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela "+tabelaContato+"! "+ex);
            return false;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
        
    // consulta todos os contatos
    public List<Contato> pesquisaTodosContatos(){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISACONTATOS_TODOS (?)");
            preparedStatement.setString(1, tabelaContato);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet, false));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar todos contatos! "+ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    // consulta contatos da agenda
    public List<Contato> pesquisaContatosAgenda(){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISACONTATOS_AGENDA (?)");
            preparedStatement.setString(1, tabelaContato);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet, false));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar contatos da agenda! "+ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    // consulta contatos não salvos
    public List<Contato> pesquisaContatosNaoSalvos(){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISACONTATOS_NAOSALVOS (?)");
            preparedStatement.setString(1, tabelaContato);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet, false));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar contatos não salvos! "+ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    // consulta grupos
    public List<Contato> pesquisaGrupos(){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISAGRUPOS (?)");
            preparedStatement.setString(1, tabelaContato);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContatoGrupo(resultSet));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar grupos! "+ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public Contato pesquisaContatoPorJID(String jid){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISA_CONTATO_JID (?, ?)");
            preparedStatement.setString(1, tabelaContato);
            preparedStatement.setString(2, jid);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            
            return carregaObjetoContato(resultSet, true);
        } catch (SQLException ex) {
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    private void fechaConexao(PreparedStatement ps, Connection cnnt){
        try {
            ps.close();
            cnnt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão!");
        }
    }
    
    
    
    private Contato carregaObjetoContato(ResultSet resultado, boolean filtro ) throws SQLException{
        Contato contato = new Contato();
        
        contato.setId(resultado.getLong("CTT_ID"));
        contato.setJid(resultado.getString("CTT_JID"));
        contato.setStatus(resultado.getString("CTT_STATUS"));
        contato.setNumber(resultado.getString("CTT_NUMBER"));
        contato.setDisplay_name(resultado.getString("CTT_DISPLAY_NAME"));
        
        if(filtro){
            contato.setWa_name(resultado.getString("CTT_WA_NAME"));
        }
        
        if(resultado.getDate("CTT_STATUS_DATE") == null){
            contato.setStatus_date(null);
            contato.setStatus_time(null);
        }
        else{
            contato.setStatus_date(resultado.getDate("CTT_STATUS_DATE"));
            contato.setStatus_time(resultado.getTime("CTT_STATUS_TIME"));
        }
        
        return contato;
    }
    
    
    
    private Contato carregaObjetoContatoGrupo(ResultSet resultado) throws SQLException{
        Contato contato = new Contato();
        contato.setId(resultado.getLong("CTT_ID"));
        contato.setJid(resultado.getString("CTT_JID"));
        contato.setNumber(resultado.getString("CTT_NUMBER"));
        contato.setDisplay_name(resultado.getString("CTT_DISPLAY_NAME"));
        
        return contato;
    }
}
