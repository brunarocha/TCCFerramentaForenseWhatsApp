/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.relatorio;

import dao.ContatoDAO;
import dao.MensagemDAO;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import model.Consulta;
import model.Contato;
import model.Dispositivo;
import model.Mensagem;
import model.relatorio.Relatorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import util.ConverteData;

/**
 *
 * @author BRUNA
 */
public class TelaDispositivoGeraRelatorio extends javax.swing.JDialog {

    private final Dispositivo dispositivo;
    private Consulta consulta;
    private List<Mensagem> mensagens;
    private List<Contato> contatos;
    private List<Relatorio> relatorio = new ArrayList<>();
    
    /**
     * Creates new form TelaDispositivoGeraRelatorio
     * @param parent
     * @param modal
     * @param dispositivo
     */
    public TelaDispositivoGeraRelatorio(javax.swing.JDialog parent, boolean modal, Dispositivo dispositivo) {
        super(parent, modal);
        initComponents();
        this.dispositivo = dispositivo;
        desabilitaComboContato();
        desabilitaCamposFiltro();
    }

    
    private void habilitaComboContato(){
        comboContato.removeAllItems();
        ContatoDAO contatoDAO = new ContatoDAO(dispositivo.getTabela_contato());
        contatos = contatoDAO.pesquisaTodosContatos();
        
        for(Contato contato : contatos){
            comboContato.addItem(contato);
        }
        comboContato.setEnabled(true);
    }
    
    
    private void desabilitaComboContato(){
        comboContato.removeAllItems();
        comboContato.setEnabled(false);
    }
    
    
    private void desabilitaCamposFiltro(){
        campoPalavraChave.setEnabled(false);
        campoDataInicial.setEnabled(false);
        campoDataFinal.setEnabled(false);
        campoHoraInicial.setEnabled(false);
        campoHoraFinal.setEnabled(false);
    }
    
    private void preencheListaRelatorio(List<Contato> resultados){
        MensagemDAO mensagemDAO = new MensagemDAO(dispositivo.getTabela_mensagem(), dispositivo.getTabela_contato());
        
        for (int i = 0; i < resultados.size(); i++) {
            Contato c = new Contato();
            c.setId(resultados.get(i).getId());
            consulta.setContato(c);
            
            mensagens = mensagemDAO.filtraConsultaContato(consulta);
            
            int enviadas = 0;
            int recebidas = 0;
            
            for(Mensagem m : mensagens){
                if(m.isKey_from_me()){
                    enviadas++;
                }
                else{
                    recebidas++;
                }
            }
            
            relatorio.add(new Relatorio(resultados.get(i).getId(), resultados.get(i).toString(), enviadas, recebidas));
        }        
    }
    
    
    private void geraRelatorioContato() throws JRException{
        MensagemDAO mensagemDAO = new MensagemDAO(dispositivo.getTabela_mensagem(), dispositivo.getTabela_contato());
        mensagens = mensagemDAO.filtraConsultaContato(consulta);
        
        HashMap parametros = new HashMap();
        parametros.put("contato", consulta.getContato()+" - "+consulta.getContato().getJid());
        parametros.put("filtros", geraTextoRelatorio());
                
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(mensagens);
        // DataSource criado a partir da lista de Beans
        
        File file = new File("src/relatorio/RelatorioContato.jasper");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), parametros, ds);

        File diretorioRelatorio = new File(dispositivo.getDiretorio()+"\\Relatorios");
        File arquivoRelatorio = new File(diretorioRelatorio+"\\"+campoTitulo.getText()+".pdf");
        
        try {
            arquivoRelatorio.createNewFile();
            JasperExportManager.exportReportToPdfFile(jasperPrint, arquivoRelatorio.getAbsolutePath());
            java.awt.Desktop.getDesktop().open(arquivoRelatorio);
            JOptionPane.showMessageDialog(this, "Relatório gerado!");
            this.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar arquivo do relatório! "+ex);
        }
        
        
    }
    
    
    private void geraRelatorioTodos() throws JRException{
        MensagemDAO mensagemDAO = new MensagemDAO(dispositivo.getTabela_mensagem(), dispositivo.getTabela_contato());
        preencheListaRelatorio(mensagemDAO.filtraConsultaTodos(consulta));
        
        HashMap parametros = new HashMap();
        parametros.put("filtros", geraTextoRelatorio());
        parametros.put("titulo", campoTitulo.getText());
                
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorio);
        // DataSource criado a partir da lista de Beans
        
        File file = new File("src/relatorio/RelatorioTudo.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), parametros, ds);
        
        File diretorioRelatorio = new File(dispositivo.getDiretorio()+"\\Relatorios");
        File arquivoRelatorio = new File(diretorioRelatorio+"\\"+campoTitulo.getText()+".pdf");
        
        try {
            arquivoRelatorio.createNewFile();
            JasperExportManager.exportReportToPdfFile(jasperPrint, arquivoRelatorio.getAbsolutePath());
            java.awt.Desktop.getDesktop().open(arquivoRelatorio);
            JOptionPane.showMessageDialog(this, "Relatório gerado!");
            this.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar arquivo do relatório! "+ex);
        }
    }
    
    
    private void preencheObjetoConsulta(){
        consulta = new Consulta();
        
        if(campoPalavraChave.getText().isEmpty()){ consulta.setPalavraChave(null); }
        else{ consulta.setPalavraChave(campoPalavraChave.getText()); }
        
        if(campoDataInicial.getValue() == null){ consulta.setDataInicial(null); }
        else{ consulta.setDataInicial(ConverteData.getDateDeString((String)campoDataInicial.getValue())); }
        
        if(campoDataFinal.getValue() == null){ consulta.setDataFinal(null); }
        else{ consulta.setDataFinal(ConverteData.getDateDeString((String)campoDataFinal.getValue())); }
        
        if(campoHoraInicial.getValue() == null){ consulta.setHoraInicial(null); }
        else{ consulta.setHoraInicial(ConverteData.getTimeDeString((String)campoHoraInicial.getValue())); }
        
        if(campoHoraFinal.getValue() == null){ consulta.setHoraFinal(null); }
        else{ consulta.setHoraFinal(ConverteData.getTimeDeString((String)campoHoraFinal.getValue())); }
       
        if(radioButtonTodos.isSelected()){
            consulta.setTipo("T");
            consulta.setContato(null);
        }
        else if(radioButtonAgenda.isSelected()){
            consulta.setTipo("C");
            consulta.setContato(null);
        }
        else if(radioButtonGrupos.isSelected()){
            consulta.setTipo("G");
            consulta.setContato(null);
        }     
        else if(radioButtonEspecifico.isSelected()){
            Contato contatoSelecionado = contatos.get(comboContato.getSelectedIndex());
            //System.out.println("selecionado : "+contatoSelecionado.getJid());
            consulta.setContato(contatoSelecionado);
        }
    }
    
    
    private String geraTextoRelatorio(){
        String palavraChave = "", dataInicial = "", dataFinal = "", horaInicial = "", horaFinal = "";
        if(consulta.getPalavraChave() !=null){
            palavraChave = "Palavra-chave: "+consulta.getPalavraChave()+ "\r\n";
        }
        if(consulta.getDataInicial() != null && consulta.getDataFinal() != null ){
            dataInicial = "Data Inicial: "+ConverteData.getStringDeDate(consulta.getDataInicial())+" \r\n";
            dataFinal = "Data Final: "+ConverteData.getStringDeDate(consulta.getDataFinal())+" \r\n";
        }
        if(consulta.getDataInicial() != null && consulta.getHoraInicial() != null){
            dataInicial = "Data: "+ConverteData.getStringDeDate(consulta.getDataInicial())+" \r\n";
            horaInicial = "Hora Inicial: "+ConverteData.getStringDeTime(consulta.getHoraInicial())+" \r\n";
            horaFinal = "Hora Final: "+ConverteData.getStringDeTime(consulta.getHoraFinal());
        }
        
        return palavraChave+dataInicial+dataFinal+horaInicial+ horaFinal;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioButtonGrupo = new javax.swing.ButtonGroup();
        painelGeraRelatorio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoTitulo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        radioButtonTodos = new javax.swing.JRadioButton();
        radioButtonGrupos = new javax.swing.JRadioButton();
        radioButtonEspecifico = new javax.swing.JRadioButton();
        comboContato = new javax.swing.JComboBox<>();
        radioButtonAgenda = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        checkPalavraChave = new javax.swing.JCheckBox();
        checkData = new javax.swing.JCheckBox();
        checkHora = new javax.swing.JCheckBox();
        campoPalavraChave = new javax.swing.JTextField();
        campoDataInicial = new javax.swing.JFormattedTextField();
        labelAData = new javax.swing.JLabel();
        campoDataFinal = new javax.swing.JFormattedTextField();
        campoHoraInicial = new javax.swing.JFormattedTextField();
        campoHoraFinal = new javax.swing.JFormattedTextField();
        labelAHora = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        botaoGerar = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GERAR RELATÓRIO");

        painelGeraRelatorio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GERAÇÃO DE RELATÓRIOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(24, 62, 81))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Título:");

        campoTitulo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Contatos:");

        radioButtonGrupo.add(radioButtonTodos);
        radioButtonTodos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioButtonTodos.setText("Todos");

        radioButtonGrupo.add(radioButtonGrupos);
        radioButtonGrupos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioButtonGrupos.setText("Grupos");

        radioButtonGrupo.add(radioButtonEspecifico);
        radioButtonEspecifico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioButtonEspecifico.setText("Específico");
        radioButtonEspecifico.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioButtonEspecificoStateChanged(evt);
            }
        });

        comboContato.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        radioButtonGrupo.add(radioButtonAgenda);
        radioButtonAgenda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioButtonAgenda.setText("Agenda");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Filtrar:");

        checkPalavraChave.setText("Palavra chave");
        checkPalavraChave.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkPalavraChaveStateChanged(evt);
            }
        });

        checkData.setText("Data");
        checkData.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkDataStateChanged(evt);
            }
        });

        checkHora.setText("Hora");
        checkHora.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkHoraStateChanged(evt);
            }
        });

        campoPalavraChave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        try {
            campoDataInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelAData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelAData.setText("a");

        try {
            campoDataFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            campoHoraInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            campoHoraFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelAHora.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelAHora.setText("a");

        botaoGerar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoGerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gerar.png"))); // NOI18N
        botaoGerar.setText("Gerar");
        botaoGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoGerarActionPerformed(evt);
            }
        });

        botaoSair.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/sair.png"))); // NOI18N
        botaoSair.setText("Sair");
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelGeraRelatorioLayout = new javax.swing.GroupLayout(painelGeraRelatorio);
        painelGeraRelatorio.setLayout(painelGeraRelatorioLayout);
        painelGeraRelatorioLayout.setHorizontalGroup(
            painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comboContato, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkPalavraChave, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(checkData))
                                .addComponent(checkHora))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(campoPalavraChave)
                                .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                                    .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                                            .addComponent(campoDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(labelAData)
                                            .addGap(18, 18, 18)
                                            .addComponent(campoDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                                            .addComponent(campoHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(labelAHora, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(campoHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelGeraRelatorioLayout.createSequentialGroup()
                            .addComponent(botaoGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botaoSair))
                        .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                            .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(4, 4, 4)
                                    .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(radioButtonTodos)
                                        .addComponent(radioButtonGrupos))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(radioButtonEspecifico)
                                        .addComponent(radioButtonAgenda))))
                            .addGap(1, 1, 1))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        painelGeraRelatorioLayout.setVerticalGroup(
            painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelGeraRelatorioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(radioButtonTodos)
                    .addComponent(radioButtonAgenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioButtonGrupos)
                    .addComponent(radioButtonEspecifico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboContato, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoPalavraChave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPalavraChave)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAData)
                    .addComponent(campoDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAHora)
                    .addComponent(campoHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkHora))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelGeraRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSair, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(painelGeraRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(painelGeraRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoSairActionPerformed

    private void checkHoraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkHoraStateChanged
        if(checkHora.isSelected()){
            campoHoraInicial.setEnabled(true);
            campoHoraFinal.setEnabled(true);
            campoDataFinal.setValue(null);
            campoDataFinal.setEnabled(false);
        }
        else{
            campoHoraInicial.setEnabled(false);
            campoHoraFinal.setEnabled(false);
        }
    }//GEN-LAST:event_checkHoraStateChanged

    private void checkDataStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkDataStateChanged
        if(checkData.isSelected()){
            campoDataInicial.setEnabled(true);
            campoDataFinal.setEnabled(true);
        }
        else{
            campoDataInicial.setEnabled(false);
            campoDataFinal.setEnabled(false);
        }
    }//GEN-LAST:event_checkDataStateChanged

    private void checkPalavraChaveStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkPalavraChaveStateChanged
        if(checkPalavraChave.isSelected()){
            campoPalavraChave.setEnabled(true);
        }
        else{
            campoPalavraChave.setEnabled(false);
        }
    }//GEN-LAST:event_checkPalavraChaveStateChanged

    private void radioButtonEspecificoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioButtonEspecificoStateChanged
        if(radioButtonEspecifico.isSelected()){
            habilitaComboContato();
        }
        else{
            desabilitaComboContato();
        }
    }//GEN-LAST:event_radioButtonEspecificoStateChanged

    private void botaoGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoGerarActionPerformed
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        preencheObjetoConsulta();
        try {
            if(consulta.getContato() != null){
                geraRelatorioContato();
            }
            else{
                geraRelatorioTodos();
            }
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório! "+ex);
        }
        getContentPane().setCursor(Cursor.getDefaultCursor());
        
    }//GEN-LAST:event_botaoGerarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaDispositivoGeraRelatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDispositivoGeraRelatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDispositivoGeraRelatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDispositivoGeraRelatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
       /* java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaDispositivoGeraRelatorio dialog = new TelaDispositivoGeraRelatorio(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoGerar;
    private javax.swing.JButton botaoSair;
    private javax.swing.JFormattedTextField campoDataFinal;
    private javax.swing.JFormattedTextField campoDataInicial;
    private javax.swing.JFormattedTextField campoHoraFinal;
    private javax.swing.JFormattedTextField campoHoraInicial;
    private javax.swing.JTextField campoPalavraChave;
    private javax.swing.JTextField campoTitulo;
    private javax.swing.JCheckBox checkData;
    private javax.swing.JCheckBox checkHora;
    private javax.swing.JCheckBox checkPalavraChave;
    private javax.swing.JComboBox<Object> comboContato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelAData;
    private javax.swing.JLabel labelAHora;
    private javax.swing.JPanel painelGeraRelatorio;
    private javax.swing.JRadioButton radioButtonAgenda;
    private javax.swing.JRadioButton radioButtonEspecifico;
    private javax.swing.ButtonGroup radioButtonGrupo;
    private javax.swing.JRadioButton radioButtonGrupos;
    private javax.swing.JRadioButton radioButtonTodos;
    // End of variables declaration//GEN-END:variables
}
