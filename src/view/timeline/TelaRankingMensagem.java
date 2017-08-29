/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.timeline;

import dao.MensagemDAO;
import java.awt.Cursor;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Consulta;
import model.Dispositivo;
import model.relatorio.RankingMensagem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BRUNA
 */
public class TelaRankingMensagem extends javax.swing.JDialog {

    private List<RankingMensagem> timelines;
    private final Dispositivo dispositivo;
    private Consulta consulta;
    private String valorSelecionado;
    private final String tempo[] = {"", "ANO", "MÊS"};
    private final String anos[] = {"2015", "2016", "2017"};
    private final String meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
                                    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        
    /**
     * Creates new form TelaTimeline
     * @param parent
     * @param modal
     * @param dispositivo
     */
    public TelaRankingMensagem(javax.swing.JDialog parent, boolean modal, Dispositivo dispositivo) {
        super(parent, modal);
        initComponents();
        this.dispositivo = dispositivo;
        
        preencheJSliderTempo();
        preencheComboAno();
        preencheComboMes();
        desativaCombos();
    }

    
    private void preencheJSliderTempo(){
        jSliderTempo.setMinorTickSpacing(1);
        jSliderTempo.setMajorTickSpacing(1);
        jSliderTempo.setMinimum(1);
        jSliderTempo.setMaximum(tempo.length-1);
        
        Hashtable labelTable = new Hashtable();
        for(int i = 0; i < tempo.length; i++){
            labelTable.put(i, new JLabel(tempo[i]));
        }
        
        jSliderTempo.setLabelTable(labelTable);
        jSliderTempo.setPaintLabels(true);
    }
    
    
    private void preencheComboAno(){
        comboAno.removeAllItems();
        comboAno.setModel(new javax.swing.DefaultComboBoxModel<>(anos));
    }
    
    
    private void preencheComboMes(){
        comboMes.removeAllItems();
        comboMes.setModel(new javax.swing.DefaultComboBoxModel<>(meses));
    }
    
    
    private void desativaCombos(){
        comboAno.setEnabled(false);
        comboMes.setEnabled(false);
    }
    
    
    private void preencheObjetoConsulta(){
        consulta = new Consulta();
        
        Calendar dataInicial = Calendar.getInstance();
        Calendar dataFinal = Calendar.getInstance();
        
        if(valorSelecionado.equals("ANO")){
            dataInicial.set(Integer.parseInt(comboAno.getSelectedItem().toString()), 01, 01);
            dataFinal.set(Integer.parseInt(comboAno.getSelectedItem().toString()), 12, 31);
        }
        
        if(valorSelecionado.equals("MÊS")){
            dataInicial.set(Integer.parseInt(comboAno.getSelectedItem().toString()), comboMes.getSelectedIndex(), 01);
            dataFinal.set(Integer.parseInt(comboAno.getSelectedItem().toString()), comboMes.getSelectedIndex(), dataInicial.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        
        consulta.setDataInicial(new Date(dataInicial.getTimeInMillis()));
        consulta.setDataFinal(new Date(dataFinal.getTimeInMillis()));
        
        if(checkContatos.isSelected() && checkGrupos.isSelected() ||
           !checkContatos.isSelected() && !checkGrupos.isSelected()){
            consulta.setTipo("T");
        }
        else if(checkContatos.isSelected()){
            consulta.setTipo("C");
        }
        else if(checkGrupos.isSelected()){
            consulta.setTipo("G");
        }
    }
    
    private void geraTimelinePorAno() throws JRException{
        MensagemDAO mensagemDAO = new MensagemDAO(dispositivo.getTabela_mensagem(), dispositivo.getTabela_contato());
        timelines = mensagemDAO.pesquisaMensagensTimeline(consulta);
        
        if(timelines.isEmpty()){
            JOptionPane.showMessageDialog(this, "Não foram encontrados resultados!");
        }
        else{
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(timelines);

            File file = new File("src/timeline/TelaTimeline.jasper");

            JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), null, ds);
            JasperViewer visualizador = new JasperViewer(jasperPrint, false); // se estiver true é equivalente ao EXIT_ON_CLOSE. se estiver false é equivalente ao dispose(), para não fechar a aplicação,
            frameInternoTimeline.setContentPane(visualizador.getContentPane());
            
            getContentPane().setCursor(Cursor.getDefaultCursor());
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoRadios = new javax.swing.ButtonGroup();
        jSliderTempo = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        comboMes = new javax.swing.JComboBox<>();
        comboAno = new javax.swing.JComboBox<>();
        botaoPesquisar = new javax.swing.JButton();
        checkContatos = new javax.swing.JCheckBox();
        checkGrupos = new javax.swing.JCheckBox();
        frameInternoTimeline = new javax.swing.JInternalFrame();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSair = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RANKING DE MENSAGENS");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        jSliderTempo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jSliderTempo.setPaintLabels(true);
        jSliderTempo.setPaintTicks(true);
        jSliderTempo.setSnapToTicks(true);
        jSliderTempo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSliderTempoMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(24, 62, 81));
        jLabel1.setText("TEMPO");

        comboMes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mês", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        comboAno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboAno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboAno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ano", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        botaoPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pesquisar.png"))); // NOI18N
        botaoPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPesquisarActionPerformed(evt);
            }
        });

        checkContatos.setText("Contatos");

        checkGrupos.setText("Grupos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboAno, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(botaoPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(checkContatos)
                        .addGap(18, 18, 18)
                        .addComponent(checkGrupos)))
                .addGap(0, 374, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botaoPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkContatos)
                    .addComponent(checkGrupos))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        frameInternoTimeline.setVisible(true);

        javax.swing.GroupLayout frameInternoTimelineLayout = new javax.swing.GroupLayout(frameInternoTimeline.getContentPane());
        frameInternoTimeline.getContentPane().setLayout(frameInternoTimelineLayout);
        frameInternoTimelineLayout.setHorizontalGroup(
            frameInternoTimelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        frameInternoTimelineLayout.setVerticalGroup(
            frameInternoTimelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        menuSair.setText("Sair");
        menuSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSairMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuSair);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSliderTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(frameInternoTimeline))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frameInternoTimeline)
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jSliderTempoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderTempoMouseClicked
        valorSelecionado = tempo[jSliderTempo.getValue()];
        
        if(valorSelecionado.equals("ANO")){
            comboAno.setEnabled(true);
            comboMes.setEnabled(false);
        }
        
        if(valorSelecionado.equals("MÊS")){
            comboAno.setEnabled(true);
            comboMes.setEnabled(true);
        }
    }//GEN-LAST:event_jSliderTempoMouseClicked

    private void botaoPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPesquisarActionPerformed
        preencheObjetoConsulta();
        
        try {
            geraTimelinePorAno();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro gerar timeline! "+ex);
        }
    }//GEN-LAST:event_botaoPesquisarActionPerformed

    private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMouseClicked
        this.dispose();
    }//GEN-LAST:event_menuSairMouseClicked

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
            java.util.logging.Logger.getLogger(TelaRankingMensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRankingMensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRankingMensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRankingMensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaTimeline dialog = new TelaTimeline(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaRankingMensagem dialog = new TelaRankingMensagem(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoPesquisar;
    private javax.swing.JCheckBox checkContatos;
    private javax.swing.JCheckBox checkGrupos;
    private javax.swing.JComboBox<String> comboAno;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JInternalFrame frameInternoTimeline;
    private javax.swing.ButtonGroup grupoRadios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSliderTempo;
    private javax.swing.JMenu menuSair;
    // End of variables declaration//GEN-END:variables
}
