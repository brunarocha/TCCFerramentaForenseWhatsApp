/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import dao.ContatoDAO;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Dispositivo;
import model.Mensagem;
import util.ConverteData;

/**
 *
 * @author Bruna
 */
public class TabelaModeloMensagem  extends AbstractTableModel{
    private final List<Mensagem> mensagens;
    private final Dispositivo dispositivo;
    private final String colunas[] = {"Recebidas", "Enviadas"};

    public TabelaModeloMensagem(List<Mensagem> mensagens, Dispositivo dispositivo) {
        this.mensagens = mensagens;
        this.dispositivo = dispositivo;
    }
    
    
    @Override
    public int getRowCount() {
        return this.mensagens.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mensagem mensagem = this.mensagens.get(rowIndex);
        ContatoDAO contatoDAO = new ContatoDAO(this.dispositivo.getTabela_contato());
        
        if(mensagem.isKey_from_me()){
            if(mensagem.getRemote_resource() == null){
                switch(columnIndex){
                    case 0 : return ConverteData.getStringDeDate(mensagem.getDate())+" "+ConverteData.getStringDeTime(mensagem.getTime())+"\n";
                    case 1 : return mensagem.getData();
                }
            }
            else{
                switch(columnIndex){
                    case 0 : return ConverteData.getStringDeDate(mensagem.getDate())+" "+ConverteData.getStringDeTime(mensagem.getTime())+"\n"+
                                    mensagem.getRemote_resource(); //+"\n"+
                                    //contatoDAO.pesquisaContato(mensagem.getRemote_resource()).getWa_name();
                    case 1 : return mensagem.getData();
                }
            }
        }
        
        else{
            if(mensagem.getRemote_resource() == null){
                switch(columnIndex){
                    case 0 : return mensagem.getData();
                    case 1 : return ConverteData.getStringDeDate(mensagem.getDate())+" "+ConverteData.getStringDeTime(mensagem.getTime());
                }
            }
            else{
                switch(columnIndex){
                    case 0 : return mensagem.getData();
                    case 1 : return ConverteData.getStringDeDate(mensagem.getDate())+" "+ConverteData.getStringDeTime(mensagem.getTime())+"\n"+
                                    mensagem.getRemote_resource();//+"\n"+
                                    //contatoDAO.pesquisaContato(mensagem.getRemote_resource()).getWa_name();
                }
            }
        }
        
        return null;
    }
    
    
}
    

