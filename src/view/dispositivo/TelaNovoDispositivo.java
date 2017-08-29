/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dispositivo;

import dao.DispositivoDAO;
import java.awt.Cursor;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Caso;
import model.Dispositivo;
import scripts.ScriptExtracao;
import scripts.ScriptVerificaDispositivo;
import util.ManipulaArquivos;

/**
 *
 * @author BRUNA
 */
public class TelaNovoDispositivo extends javax.swing.JDialog {

    private Dispositivo dispositivo = new Dispositivo();
    private final Caso caso;
    
    /**
     * Creates new form TelaNovoDispositivo
     * @param parent
     * @param modal
     * @param caso
     */
    public TelaNovoDispositivo(java.awt.Frame parent, boolean modal, Caso caso) {
        super(parent, modal);
        initComponents();
        this.caso = caso;
        
        carregaTextPaneAviso();
        desabilitaCamposBotoes();
        campoSalvarEM.setText(caso.getDiretorio());
        campoSalvarEM.setEditable(false);
        
        textPanelInformacao.setText("Nenhum dispositivo verificado");
        textPanelInformacao.setEditable(false);
        painelCamposCaso.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CRIAR NOVO DISPOSITIVO: "+caso.getDescricao(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(24, 62, 81))); // NOI18N
        textPaneAviso.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
    }

    private void carregaTextPaneAviso(){
        textPaneAviso.setText("* O dispositivo deve estar conectado via cabo USB; \n"
                            +  "* A DEPURAÇÃO USB deve estar ativada; \n"
                            +  "* O modo de armazenamento deve estar ativo.");
        textPaneAviso.setEditable(false);
    }
    
    
    private void carregaTextPaneInformacaoDispositivo(){
        textPanelInformacao.setText("Modelo: "+dispositivo.getNumero_modelo()+" "+dispositivo.getModelo()+" \n"
                            + "Android: "+dispositivo.getVersao_android()+" \n"
                            + "Fabricante: "+dispositivo.getFabricante());
        textPanelInformacao.setEditable(false);
    }
    
    
    private void desabilitaCamposBotoes(){
        campoNomeDispositivo.setEnabled(false);
        campoSalvarEM.setEnabled(false);
        botaoCriar.setEnabled(false);
    }
    
    
    private void habilitaCamposBotoes(){
        campoNomeDispositivo.setEnabled(true);
        campoSalvarEM.setEnabled(true);
        botaoCriar.setEnabled(true);
    }
    
    
    private boolean verificaCamposPreenchidos(){
        if(campoNomeDispositivo.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Informe o nome do dispositivo!");
            return false;
        }
        
        return true;
    }
    
    
    private void carregaObjetoDispositivo(){
        this.dispositivo.setCaso(this.caso);
        this.dispositivo.setNome(campoNomeDispositivo.getText());
        this.dispositivo.setDiretorio(caso.getDiretorio()+"\\"+this.dispositivo.getNome());
        this.dispositivo.setTabela_contato(this.dispositivo.getNome().replaceAll(" ", "_")+"_contato");
        this.dispositivo.setTabela_mensagem(this.dispositivo.getNome().replaceAll(" ", "_")+"_mensagem");
    }
            
    
    private void criaPastaDispositivo(){
        File dirDispositivo = new File(dispositivo.getDiretorio());
        dirDispositivo.mkdir();
    }
    
    private void criaPastaDispositivoRelatorio(){
        File dirRelatorio = new File(dispositivo.getDiretorio()+"\\Relatorios");
        dirRelatorio.mkdir();
    }
    
    
    private boolean extraiDadosDispositivo(String diretorioSDCard){
        String diretorioSalvar = this.dispositivo.getDiretorio(); 
        
        boolean carregaScript = ScriptExtracao.scriptExtraiBanco(diretorioSalvar, diretorioSDCard);
        
        if(carregaScript){
            getContentPane().setCursor(Cursor.getDefaultCursor());
            
            // método para bloquear acesso escrita aos bancos extraídos
            bloqueiaAcessoEscritaBanco();
            return true;
        }
        return false;
    }
    
    
    private void bloqueiaAcessoEscritaBanco(){
        String diretorio = this.dispositivo.getCaso().getUsuario().getDiretorio_casos()
                           +this.dispositivo.getCaso().getDiretorio()+this.dispositivo.getDiretorio();
        File fileMensagens = new File(diretorio+"\\msgstore.db");
        File fileContatos = new File(diretorio+"\\wa.db");
        File filePropriedades = new File(diretorio+"\\build.prop");
        
        fileMensagens.setReadOnly();
        fileContatos.setReadOnly();
        filePropriedades.setReadOnly();
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCamposCaso = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPaneAviso = new javax.swing.JTextPane();
        botaoVerificarDispositivo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textPanelInformacao = new javax.swing.JTextPane();
        labelImagem = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        campoNomeDispositivo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoSalvarEM = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        botaoCriar = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CRIAR NOVO DISPOSITIVO");

        painelCamposCaso.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CRIAR NOVO DISPOSITIVO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(24, 62, 81))); // NOI18N

        jScrollPane1.setBorder(null);

        textPaneAviso.setBackground(new java.awt.Color(240, 240, 240));
        textPaneAviso.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        textPaneAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(textPaneAviso);

        botaoVerificarDispositivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoVerificarDispositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/verificardispositivo.png"))); // NOI18N
        botaoVerificarDispositivo.setText("Verificar Dispositivo");
        botaoVerificarDispositivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVerificarDispositivoActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        textPanelInformacao.setBackground(new java.awt.Color(240, 240, 240));
        textPanelInformacao.setBorder(null);
        textPanelInformacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(textPanelInformacao);

        labelImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancela.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nome do dispositivo");

        campoNomeDispositivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Salvar em");

        campoSalvarEM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        botaoCriar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoCriar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        botaoCriar.setText("Criar");
        botaoCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCriarActionPerformed(evt);
            }
        });

        botaoCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/sair.png"))); // NOI18N
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelCamposCasoLayout = new javax.swing.GroupLayout(painelCamposCaso);
        painelCamposCaso.setLayout(painelCamposCasoLayout);
        painelCamposCasoLayout.setHorizontalGroup(
            painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCamposCasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCamposCasoLayout.createSequentialGroup()
                        .addComponent(botaoCriar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoCancelar))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelCamposCasoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoSalvarEM))
                    .addGroup(painelCamposCasoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(painelCamposCasoLayout.createSequentialGroup()
                                .addComponent(botaoVerificarDispositivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelImagem))
                            .addComponent(jScrollPane2)))
                    .addGroup(painelCamposCasoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoNomeDispositivo)))
                .addGap(42, 42, 42))
        );
        painelCamposCasoLayout.setVerticalGroup(
            painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCamposCasoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painelCamposCasoLayout.createSequentialGroup()
                        .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelImagem)
                            .addComponent(botaoVerificarDispositivo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoNomeDispositivo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoSalvarEM, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCamposCasoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoCriar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCamposCaso, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCamposCaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoVerificarDispositivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVerificarDispositivoActionPerformed
        // TODO add your handling code here:
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        boolean script = ScriptVerificaDispositivo.scriptExtraiBuildProp();
        
        if(script){
            // objeto recebe dados do arquivo BUILD.PROP para exibir no painel Informações
            this.dispositivo = ManipulaArquivos.extraiDadosPropriedades(this.dispositivo);
            
            // preenche labels do painel Informações
            carregaTextPaneInformacaoDispositivo();
            
            File fileImagem = new File("src/resources/confirma.png");
            labelImagem.setIcon(new ImageIcon(fileImagem.getAbsolutePath()));
            
            habilitaCamposBotoes();
        }
        else{
            JOptionPane.showMessageDialog(this, "Ocorreu um erro verificar o dispositivo!");
        }
        getContentPane().setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botaoVerificarDispositivoActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void botaoCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCriarActionPerformed
        if(verificaCamposPreenchidos()){ // 1º VERIFICA SE OS CAMPOS ESTÃO PREENCHIDOS
            carregaObjetoDispositivo(); // PREENCHE OBJETO COM OS DADOS DA TELA
            criaPastaDispositivo();
            criaPastaDispositivoRelatorio();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            String diretorio = ManipulaArquivos.extraiDiretorioSDCard();
            
            if(extraiDadosDispositivo(diretorio)){
                DispositivoDAO dispositivoDAO = new DispositivoDAO();

                if(dispositivoDAO.inserir(dispositivo)){
                    JOptionPane.showMessageDialog(this, "Dispositivo criado!");
                    dispositivo = dispositivoDAO.pesquisaDispositivoPorNome(this.dispositivo.getNome(), caso);
                    this.dispose();
                    
                    new TelaDispositivoImportaBanco(this.dispositivo).setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao inserir novo dispositivo!");
                }
            }

            getContentPane().setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_botaoCriarActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaNovoDispositivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaNovoDispositivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaNovoDispositivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaNovoDispositivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoCriar;
    private javax.swing.JButton botaoVerificarDispositivo;
    private javax.swing.JTextField campoNomeDispositivo;
    private javax.swing.JTextField campoSalvarEM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelImagem;
    private javax.swing.JPanel painelCamposCaso;
    private javax.swing.JTextPane textPaneAviso;
    private javax.swing.JTextPane textPanelInformacao;
    // End of variables declaration//GEN-END:variables
}
