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
import model.Caso;
import model.Dispositivo;
import util.ConexaoJDBC;

/**
 *
 * @author BRUNA
 */
public class DispositivoDAO {
    
    private final Connection connection;
    private PreparedStatement preparedStatement;
    
    public DispositivoDAO() {
        this.connection = ConexaoJDBC.getConexaoJDBC();
    }
    
  
  
    public boolean inserir(Dispositivo dispositivo){
        String sql = "CALL SP_INSERE_DISPOSITIVO (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setLong(1, dispositivo.getCaso().getId());
            this.preparedStatement.setString(2, dispositivo.getNome());
            this.preparedStatement.setString(3, dispositivo.getFabricante());
            this.preparedStatement.setString(4, dispositivo.getIdioma());
            this.preparedStatement.setString(5, dispositivo.getModelo());
            this.preparedStatement.setString(6, dispositivo.getNumero_versao());
            this.preparedStatement.setString(7, dispositivo.getNumero_modelo());
            this.preparedStatement.setString(8, dispositivo.getVersao_android());
            this.preparedStatement.setString(9, dispositivo.getVersao_sistema());
            this.preparedStatement.setString(10, dispositivo.getDiretorio());
            this.preparedStatement.setString(11, dispositivo.getTabela_contato());
            this.preparedStatement.setString(12, dispositivo.getTabela_mensagem());
            this.preparedStatement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir novo dispositivo! "+ex.getMessage());
            return false;
        }
    }
    
    
    public boolean excluir(Dispositivo dispositivo){
        String sql = "CALL SP_EXCLUI_DISPOSITIVO (?)";
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setLong(1, dispositivo.getId());
            this.preparedStatement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir dispositivo! "+ex.getMessage());
            return false;
        }
    }
    
    
    public Dispositivo pesquisaDispositivoPorNome(String nome, Caso caso){
        try {
            this.preparedStatement = this.connection.prepareStatement("CALL SP_PESQUISA_DISPOSITIVO_NOME (?)");
            this.preparedStatement.setString(1, nome);
            
            ResultSet resultSet = this.preparedStatement.executeQuery();
            resultSet.next();
            
            return carregaObjetoDispositivo(resultSet, caso);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar dispositivo "+nome+"! "+ex.getMessage());
            return null;
        }
    }
    
    
    public List<Dispositivo> dispositivosPorCaso(Caso caso){
        try {
            this.preparedStatement = this.connection.prepareStatement("CALL SP_LISTA_DISPOSITIVOS_CASO (?)");
            this.preparedStatement.setLong(1, caso.getId());
            
            ResultSet resultSet = this.preparedStatement.executeQuery();
            List<Dispositivo> dispositivos = new ArrayList<>();
            
            while(resultSet.next()){
                dispositivos.add(carregaObjetoDispositivo(resultSet, caso));
            }
            
            return dispositivos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar dispositivos do caso: "+caso.getDescricao()+ "! "+ex.getMessage());
            return null;
        }
    }
    
    
    public int verificaImportacaoDispositivo(String tabelaDispositivo){
        try {
            this.preparedStatement = this.connection.prepareStatement("CALL SP_VERIFICA_IMPORTACAO_DISPOSITIVO (?)");
            this.preparedStatement.setString(1, tabelaDispositivo);
            
            ResultSet resultSet = this.preparedStatement.executeQuery();
            resultSet.next();
            
            return resultSet.getInt("COUNT(*)");
            
        } catch (SQLException ex) {
           //JOptionPane.showMessageDialog(null, "Erro ao inserir novo dispositivo! "+ex.getMessage());
            return 0;
        }
    }
    
    
    
    private Dispositivo carregaObjetoDispositivo(ResultSet resultado, Caso caso) throws SQLException{
        Dispositivo dispositivo = new Dispositivo();
                
        dispositivo.setId(resultado.getLong("dps_id"));
        dispositivo.setCaso(caso);
        dispositivo.setNome(resultado.getString("dps_nome"));
        dispositivo.setFabricante(resultado.getString("dps_fabricante"));
        dispositivo.setIdioma(resultado.getString("dps_idioma"));
        dispositivo.setModelo(resultado.getString("dps_modelo"));
        dispositivo.setNumero_versao(resultado.getString("dps_numero_versao"));
        dispositivo.setNumero_modelo(resultado.getString("dps_numero_modelo"));
        dispositivo.setVersao_android(resultado.getString("dps_versao_android"));
        dispositivo.setVersao_sistema(resultado.getString("dps_versao_sistema"));
        dispositivo.setDiretorio(resultado.getString("dps_diretorio"));
        dispositivo.setTabela_contato(resultado.getString("dps_tabela_contato"));
        dispositivo.setTabela_mensagem(resultado.getString("dps_tabela_mensagem"));
        
        return dispositivo;
    }
    
    
    
}