/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author BRUNA
 */
public class ConsultasAntigas {
    
    /*public void preparaConsultaTodos(Consulta consulta){
        this.contatos.removeAll(contatos);
        
        // consulta por palavra chave
        if(!consulta.getPalavraChave().isEmpty() && consulta.getDataInicial() == null){
            pesquisaMensagensPorPalavraChave(consulta.getPalavraChave(), consulta.getTipo());
        }
        // consulta por data
        else if(consulta.getPalavraChave().isEmpty() && consulta.getDataInicial() != null && 
                consulta.getDataFinal() != null && consulta.getHoraInicial() == null){
            pesquisaMensagensPorData(consulta.getDataInicial(), consulta.getDataFinal(), consulta.getTipo());
        }
        // consulta por palavra chave e data
        else if(!consulta.getPalavraChave().isEmpty() && consulta.getDataInicial() != null &&
                 consulta.getDataFinal() != null && consulta.getHoraInicial() == null){
            pesquisaMensagensPorPalavraChaveData(consulta.getPalavraChave(), consulta.getDataInicial(), 
                    consulta.getDataFinal(), consulta.getTipo());
        }
        // consulta por data e hora
        else if(consulta.getPalavraChave().isEmpty() && consulta.getDataInicial() != null && 
                consulta.getHoraInicial() != null && consulta.getHoraFinal() != null){
            pesquisaMensagensPorDataHora(consulta.getDataInicial(), consulta.getHoraInicial(), 
                                         consulta.getHoraFinal(), consulta.getTipo());
        }
        // consulta por palavra, data e hora
        else if(!consulta.getPalavraChave().isEmpty() && consulta.getDataInicial() != null && 
                 consulta.getHoraInicial() != null && consulta.getHoraFinal() != null){
            pesquisaMensagensPorPalavraDataHora(consulta.getPalavraChave(), consulta.getDataInicial(), 
                    consulta.getHoraInicial(), consulta.getHoraFinal(), consulta.getTipo());
        }
    }
    */
    
    
    /*public boolean criaTabelaMensagem(){
        try {
            preparedStatement = this.connection.prepareStatement("CALL SP_CRIA_TABELA_MENSAGEM (?, ?)");
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.execute();
            
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela "+tabelaMensagem+"! "+ex);
            return false;
        }
    }*/
    
    
    
    /* consulta.setPalavraChave(campoPesquisaPalavra.getText());
        // verifica se data inicial está preenchida
        if(campoPesquisaDataInicial.getValue() != null){
            consulta.setDataInicial(ConverteData.getDateDeString((String)campoPesquisaDataInicial.getValue()));
            
            // verifica se data final está preenchida
            if(campoPesquisaDataFinal.getValue() == null){ // && campoPesquisaHoraInicial.getValue() == null){ // preenche data atual
                if(campoPesquisaHoraInicial.getValue() != null){
                    consulta.setHoraInicial(ConverteData.getTimeDeString(campoPesquisaHoraInicial.getText()));
                    consulta.setHoraFinal(ConverteData.getTimeDeString(campoPesquisaHoraFinal.getText()));
                }
                else{
                    consulta.setDataFinal(ConverteData.getDateDataAtual());
                }
            }
            else{
                if(campoPesquisaHoraInicial.getValue() == null){
                    consulta.setDataFinal(ConverteData.getDateDeString((String)campoPesquisaDataFinal.getValue()));
                }
            }
        }
        
        if(checkContatos.isSelected() && checkGrupos.isSelected()){
            consulta.setTipo("T");
            consulta.setContato(null);
        }
        else if(checkContatos.isSelected()){
            consulta.setTipo("C");
            consulta.setContato(null);
        }
        else if(checkGrupos.isSelected()){
            consulta.setTipo("G");
            consulta.setContato(null);
        }     
        else{
            consulta.setContato(contatoSelecionado);
        }*/
    
    
    
    
    /*public List<Mensagem> pesquisaMensagensContatoPorPalavra(Contato contato, String palavraChave){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE(?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
            preparedStatement.setString(3, palavraChave);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            
            System.out.println("MENSAGENs: "+mensagens);
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por palavra chave do contato! "+ex);
            return null;
        }
    }
    
    
    public List<Mensagem> pesquisaMensagensContatoPorData(Contato contato, Date dataInicial, Date dataFinal){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_CONTATO_DATA(?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
            preparedStatement.setDate(3, dataInicial);
            preparedStatement.setDate(4, dataFinal);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por data do contato! "+ex);
            return null;
        }
    }
    
    
    public List<Mensagem> pesquisaMensagensContatoPorPalavraChaveData(Contato contato, Date dataInicial, Date dataFinal, String palavraChave){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA(?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
            preparedStatement.setString(3, palavraChave);
            preparedStatement.setDate(4, dataInicial);
            preparedStatement.setDate(5, dataFinal);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por palavra chave e data do contato! "+ex);
            return null;
        }
    }
    
    
    public List<Mensagem> pesquisaMensagensContatoPorDataHora(Contato contato, Date data, Time horaInicial, Time horaFinal){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_CONTATO_DATA_HORA(?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
            preparedStatement.setDate(3, data);
            preparedStatement.setTime(4, horaInicial);
            preparedStatement.setTime(5, horaFinal);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            return mensagens;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por data do contato! "+ex);
            return null;
        }
    }

    public List<Mensagem> pesquisaMensagensContatoPorPalavraChaveDataHora(Contato contato, String palavraChave, Date data, Time horaInicial, Time horaFinal){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA_HORA(?, ?, ?, ?, ?, ?) ";
            
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setLong(2, contato.getId());
            preparedStatement.setString(3, palavraChave);
            preparedStatement.setDate(4, data);
            preparedStatement.setTime(5, horaInicial);
            preparedStatement.setTime(6, horaFinal);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                mensagens.add(carregaObjetoMensagem(resultSet));
            }
            return mensagens;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por palavra chave, data e hora do contato! "+ex);
            return null;
        }
    }*/
    
    
    
    /*
    public List<Contato> pesquisaMensagensPorPalavraChave(String palavraChave, String tipo){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_PALAVRACHAVE(?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setString(3, tipo);
            preparedStatement.setString(4, palavraChave);
            
            ResultSet resultSet = preparedStatement.executeQuery();
             
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por palavra chave! "+ex);
            return null;
        }
    }
    
    
    public List<Contato> pesquisaMensagensPorData(Date dataInicial, Date dataFinal, String tipo){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_DATA(?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setString(3, tipo);
            preparedStatement.setDate(4, dataInicial);
            preparedStatement.setDate(5, dataFinal);
        
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por data! "+ex);
            return null;
        }
    }
    
    
    public List<Contato> pesquisaMensagensPorDataHora(Date data, Time horaInicial, Time horaFinal, String tipo){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_DATA_HORA(?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setString(3, tipo);
            preparedStatement.setDate(4, data);
            preparedStatement.setTime(5, horaInicial);
            preparedStatement.setTime(6, horaFinal);
        
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por data! "+ex);
            return null;
        }
    }
    
    public List<Contato> pesquisaMensagensPorPalavraChaveData(String palavraChave, Date dataInicial, Date dataFinal, String tipo){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_PALAVRACHAVE_DATA(?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setString(3, tipo);
            preparedStatement.setString(4, palavraChave);
            preparedStatement.setDate(5, dataInicial);
            preparedStatement.setDate(6, dataFinal);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por palavra chave e data! "+ex);
            return null;
        }
    }
    
    
    
    
    /*
     //////////////////////
     PESQUISA TODAS AS MENSAGENS
    
    public List<Contato> pesquisaMensagensPorPalavraDataHora(String palavra, Date data, Time horaInicial, Time horaFinal, String tipo){
        try {
            String sql = "CALL SP_PESQUISAMENSAGENS_DATA_HORA_PALAVRACHAVE(?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tabelaMensagem);
            preparedStatement.setString(2, tabelaContato);
            preparedStatement.setString(3, tipo);
            preparedStatement.setDate(4, data);
            preparedStatement.setTime(5, horaInicial);
            preparedStatement.setTime(6, horaFinal);
            preparedStatement.setString(7, palavra);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                contatos.add(carregaObjetoContato(resultSet));
            }
            return contatos;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar mensagens por data! "+ex);
            return null;
        }
    }
    */
    
    
}
