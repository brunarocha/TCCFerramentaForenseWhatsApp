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
import model.Consulta;
import model.Contato;
import model.Mensagem;
import model.relatorio.RankingMensagem;
import util.ConexaoJDBC;

/**
 *
 * @author BRUNA
 */
public class MensagemDAO {
    
    private final String tabelaMensagem, tabelaContato;
    private PreparedStatement preparedStatement;

    public MensagemDAO(String tabelaMensagem, String tabelaContato) {
        this.tabelaMensagem = tabelaMensagem;
        this.tabelaContato = tabelaContato;
    }

    public boolean insereMensagem(Mensagem mensagem, Long dispositivoID){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            String sql = " INSERT INTO "+tabelaMensagem+" (MEN_ID, MEN_DPS_ID, MEN_KEY_REMOTE_JID, " +
                     " MEN_REMOTE_RESOURCE, MEN_KEY_FROM_ME, MEN_DATA, MEN_DATE, MEN_TIME, MEN_MEDIA_URL, " +
                     " MEN_MEDIA_MIME_TYPE, MEN_MEDIA_SIZE, MEN_MEDIA_NAME, MEN_MEDIA_CAPTION ) " +
                     " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, mensagem.getId());
            preparedStatement.setLong(2, dispositivoID);
            preparedStatement.setLong(3, pesquisaIDContato(mensagem.getKey_remote_jid().getJid()));
            preparedStatement.setString(4, mensagem.getRemote_resource());
            preparedStatement.setBoolean(5, mensagem.isKey_from_me());
            preparedStatement.setString(6, mensagem.getData());
            preparedStatement.setDate(7, mensagem.getDate());
            preparedStatement.setTime(8, mensagem.getTime());
            preparedStatement.setString(9, mensagem.getMedia_url());
            preparedStatement.setString(10, mensagem.getMedia_mime_type());
            preparedStatement.setInt(11, mensagem.getMedia_size());
            preparedStatement.setString(12, mensagem.getMedia_name());
            preparedStatement.setString(13, mensagem.getMedia_caption());
            preparedStatement.execute();
            
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao importar mensagem! "+ex);
            return false;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public Long pesquisaIDContato(String jid){
        ContatoDAO contatoDAO = new ContatoDAO(tabelaContato);
        Contato c = contatoDAO.pesquisaContatoPorJID(jid);
        
        /*if(c == null){
            return null;
        }
        else{*/
        return c.getId();
        //}
    }
    
    
    public List<Mensagem> filtraConsultaContato(Consulta consulta){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_FILTRA_CONSULTAS_CONTATO(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, consulta.getContato().getId());
            preparedStatement.setDate(3, consulta.getDataInicial());
            preparedStatement.setDate(4, consulta.getDataFinal());
            preparedStatement.setTime(5, consulta.getHoraInicial());
            preparedStatement.setTime(6, consulta.getHoraFinal());
            preparedStatement.setString(7, consulta.getPalavraChave());
        
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Mensagem> mensagens = new ArrayList<>();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar consulta por filtros de contato!" +ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public List<Contato> filtraConsultaTodos(Consulta consulta){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_FILTRA_CONSULTAS_TODOS(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setDate(3, consulta.getDataInicial());
            preparedStatement.setDate(4, consulta.getDataFinal());
            preparedStatement.setTime(5, consulta.getHoraInicial());
            preparedStatement.setTime(6, consulta.getHoraFinal());
            preparedStatement.setString(7, consulta.getPalavraChave());
            preparedStatement.setString(8, consulta.getTipo());
        
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar consulta por filtros!" +ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public List<Mensagem> pesquisaMensagensPorContato(Contato contato){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISAMENSAGENS_CONTATO(?, ?)");
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
        
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Mensagem> mensagens = new ArrayList<>();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar mensagens do contato : "+contato+"! "+ex);
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    public List<RankingMensagem> pesquisaMensagensTimeline(Consulta consulta){
        Connection connection = ConexaoJDBC.getConexaoJDBC();
        
        try {
            preparedStatement = connection.prepareStatement("CALL SP_PESQUISARANKINGMENSAGEM(?, ?, ?, ?, ?) ");
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setDate(3, consulta.getDataInicial());
            preparedStatement.setDate(4, consulta.getDataFinal());
            preparedStatement.setString(5, consulta.getTipo());
        
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RankingMensagem> ranking = new ArrayList<>();
        
            RankingMensagem rkm;
            while(resultSet.next()){
                rkm = new RankingMensagem();
                
                rkm.setTotalMensagem(resultSet.getInt("COUNT(*)"));
                
                if(resultSet.getString("ctt_display_name") != null){
                    rkm.setNome(resultSet.getString("ctt_display_name"));
                }
                else if(resultSet.getString("ctt_wa_name") != null){
                    rkm.setNome(resultSet.getString("ctt_wa_name"));
                }
                else{
                    rkm.setNome(resultSet.getString("ctt_jid"));
                }
                
                ranking.add(rkm);
            }
            
            return ranking;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar ranking de mensagens!" +ex.getMessage());
            return null;
        }
        finally{
            fechaConexao(preparedStatement, connection);
        }
    }
    
    
    private void fechaConexao(PreparedStatement preparedStatement, Connection connection){
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão!");
        }
    }
    
    
    
    
    private Mensagem carregaObjetoMensagem(ResultSet resultSet) throws SQLException{
        Mensagem mensagem = new Mensagem();
        mensagem.setId(resultSet.getLong("men_id"));
        mensagem.setKey_remote_jid(new Contato("men_key_remote_jid"));
        mensagem.setKey_from_me(resultSet.getBoolean("men_key_from_me"));
        mensagem.setRemote_resource(resultSet.getString("men_remote_resource"));
        mensagem.setDate(resultSet.getDate("men_date"));
        mensagem.setTime(resultSet.getTime("men_time"));
        mensagem.setMedia_url(resultSet.getString("men_media_url"));
        mensagem.setMedia_mime_type(resultSet.getString("men_media_mime_type"));
        mensagem.setMedia_size(resultSet.getInt("men_media_size"));
        mensagem.setMedia_name(resultSet.getString("men_media_name"));
        mensagem.setMedia_caption(resultSet.getString("men_media_caption"));

        if(resultSet.getString("men_data") == null){
            mensagem.setData("MIDIA"); 
        }
        else{
            mensagem.setData(resultSet.getString("men_data"));
        }
       
        return mensagem;
    }
    
    
    private Contato carregaObjetoContato(ResultSet resultSet) throws SQLException{
        Contato contato = new Contato();
        contato.setId(resultSet.getLong("ctt_id"));
        contato.setJid(resultSet.getString("ctt_jid"));
        contato.setDisplay_name(resultSet.getString("ctt_display_name"));
        contato.setWa_name(resultSet.getString("ctt_wa_name"));
        
        return contato;
    }

}