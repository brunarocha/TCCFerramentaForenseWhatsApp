/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Contato;
import model.Mensagem;
import util.ConexaoSQLite;
import util.ConverteData;

/**
 *
 * @author BRUNA
 */
public class SQLiteBancoDAO {
    private final Connection connection;
    private Statement statement;
    

    public SQLiteBancoDAO(String urlBanco) {
        this.connection = ConexaoSQLite.getConexaoSQLite(urlBanco);
        try {
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar statement! "+ex);
        }
    }
    
    
    
    // carrega lista de contatos do banco WA.DB
    public List<Contato> carregaTabelaContatoSQLite(){
        String sql = " SELECT _id, jid, status, status_timestamp, number, display_name, wa_name "
                    +" FROM wa_contacts "
                    +" WHERE is_whatsapp_user = 1 and jid != 'status@broadcast'; ";
        
        try {
            ResultSet resultado = statement.executeQuery(sql);
            List<Contato> contatos = new ArrayList<>();
            
            while(resultado.next()){
                contatos.add(carregaObjetoContato(resultado));
            }
            
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tabela contato do banco WA.DB! "+ex);
            return null;
        }
    }
    
    
    // carrega lista de mensagens do banco MSGSTORE.DB
    public List<Mensagem> carregaTabelaMensagensSQLite(){
        String sql = " SELECT _id, key_remote_jid, key_from_me, data, timestamp, media_url, media_mime_type, "
                    +" media_size, media_name, media_caption, remote_resource "
                    +" FROM messages "
                    +" WHERE key_remote_jid != -1 AND key_remote_jid != 'status@broadcast' "
                    +" AND ( (data is not null and media_url is not null) "
                    +"       or (data is null and media_url is not null) " 
                    +"       or (data is not null and media_url is null) " 
                    +"     ) " 
                    +" ORDER BY timestamp ASC ";
                
        try {
            ResultSet resultado = statement.executeQuery(sql);
            
            List<Mensagem> mensagens = new ArrayList<>();
            
            while(resultado.next()){
                mensagens.add(carregaObjetoMensagem(resultado));
            }
            
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tabela mensagem do banco MSGSTORE.DB! "+ex);
            return null;
        }
    }
    
    
    
    private Contato carregaObjetoContato(ResultSet resultado) throws SQLException{
        Contato contato = new Contato();
        
        contato.setId(resultado.getLong("_id"));
        contato.setJid(resultado.getString("jid"));
        contato.setStatus(resultado.getString("status"));
        contato.setNumber(resultado.getString("number"));
        contato.setDisplay_name(resultado.getString("display_name"));
        contato.setWa_name(resultado.getString("wa_name"));
        
        if(resultado.getTimestamp("status_timestamp").getTime() == 0){
            contato.setStatus_date(null);
            contato.setStatus_time(null);
        }
        else{
            contato.setStatus_date(ConverteData.getDateDeTimestamp(resultado.getTimestamp("status_timestamp")));
            contato.setStatus_time(ConverteData.getTimeDeTimestamp(resultado.getTimestamp("status_timestamp")));
        }
        
        return contato;
    }
    
    
    private Mensagem carregaObjetoMensagem(ResultSet resultado) throws SQLException{
        Mensagem mensagem = new Mensagem();
        
        mensagem.setId(resultado.getLong("_id"));
        mensagem.setKey_remote_jid(new Contato(resultado.getString("key_remote_jid")));
        mensagem.setKey_from_me(resultado.getBoolean("key_from_me"));
        mensagem.setData(resultado.getString("data"));
        mensagem.setMedia_url(resultado.getString("media_url"));
        mensagem.setMedia_mime_type(resultado.getString("media_mime_type"));
        mensagem.setMedia_size(resultado.getInt("media_size"));
        mensagem.setMedia_name(resultado.getString("media_name"));
        mensagem.setMedia_caption(resultado.getString("media_caption"));
        mensagem.setRemote_resource(resultado.getString("remote_resource"));        
        
        if(resultado.getTimestamp("timestamp").getTime() == 0){
            mensagem.setDate(null);
            mensagem.setTime(null);
        }
        else{
            mensagem.setDate(ConverteData.getDateDeTimestamp(resultado.getTimestamp("timestamp")));
            mensagem.setTime(ConverteData.getTimeDeTimestamp(resultado.getTimestamp("timestamp")));
        }
        
        return mensagem;
    }
    
    
}
