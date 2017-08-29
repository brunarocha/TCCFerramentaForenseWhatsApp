/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.caso;

import dao.CasoDAO;
import dao.UsuarioDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import listmodel.JListModel;
import model.Acesso;
import model.Caso;
import model.Usuario;
import util.ConverteData;

/**
 *
 * @author BRUNA
 */
public class TelaAlterarCaso extends javax.swing.JDialog {

    private List<Usuario> usuarios;
    private List<Usuario> usuariosSelecionados = new ArrayList<>();
    private List<Usuario> usuariosSelecionadosAux = new ArrayList<>();
    private Caso caso;
     private Usuario usuario;
    /**
     * Creates new form TelaNovoCaso
     * @param parent
     * @param modal
     * @param caso
     * @param usuario
     */
    public TelaAlterarCaso(java.awt.Frame parent, boolean modal, Caso caso, Usuario usuario) {
        super(parent, modal);
        initComponents();
        
        this.caso = caso;
        this.usuario = usuario;
        
        carregaJListUsuariosAcesso();
        carregaJListUsuarios();
        preencheDadosTela();
    }

    
    private void preencheDadosTela(){
        labelDescricao.setText(caso.getDescricao());
        labelNomeInvestigado.setText(caso.getNome_investigado());
        labelDataCriacao.setText(ConverteData.getStringDeCalendar(caso.getData_criacao()));
    }
    
    
    private void carregaJListUsuarios(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarios = usuarioDAO.pesquisaUsuarios(usuario.getId());
        
        for(Usuario us : usuariosSelecionados){
            usuarios.remove(us);
        }
        jListUsuarios.setModel(new JListModel(usuarios));
    }
    
    private void carregaJListUsuariosAcesso(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuariosSelecionados = usuarioDAO.pesquisaUsuariosAcesso(usuario.getId(), caso.getId());
        usuariosSelecionadosAux = usuarioDAO.pesquisaUsuariosAcesso(usuario.getId(), caso.getId());
        jListUsuariosSelecionados.setModel(new JListModel(usuariosSelecionados));
    }
    
    private  List<Usuario> verificaUsuariosSelecionados(){
        List<Usuario> usuariosNovos = new ArrayList<>();
        
        for(Usuario us: usuariosSelecionados){
            usuariosNovos.add(us);
        }
        
        for(Usuario us : usuariosSelecionadosAux){
            usuariosNovos.remove(us);
        }
        return usuariosNovos;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelDataCriacao = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsuarios = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListUsuariosSelecionados = new javax.swing.JList<>();
        botaoInsereUsuario = new javax.swing.JButton();
        botaoRemoveUsuario = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        botaoSalvar = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();
        labelNomeInvestigado = new javax.swing.JLabel();
        labelDescricao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CRIAR NOVO CASO");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ALTERAR CASO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(24, 62, 81))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Descrição:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nome do investigado:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Data de criação:");

        labelDataCriacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelDataCriacao.setText("Data de criação:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Usuários que podem acessar:");

        jListUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListUsuarios);

        jListUsuariosSelecionados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListUsuariosSelecionados);

        botaoInsereUsuario.setText(">");
        botaoInsereUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInsereUsuarioActionPerformed(evt);
            }
        });

        botaoRemoveUsuario.setText("<");
        botaoRemoveUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemoveUsuarioActionPerformed(evt);
            }
        });

        botaoSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botaoSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/confirmar.png"))); // NOI18N
        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
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

        labelNomeInvestigado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelNomeInvestigado.setText("Descrição:");

        labelDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelDescricao.setText("Descrição:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDescricao))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNomeInvestigado))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDataCriacao))
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoInsereUsuario)
                            .addComponent(botaoRemoveUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoCancelar)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelDescricao))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelNomeInvestigado))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelDataCriacao))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22)
                        .addComponent(botaoInsereUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(botaoRemoveUsuario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoInsereUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoInsereUsuarioActionPerformed
        int in[] = jListUsuarios.getSelectedIndices();
        List<Usuario> listaAuxiliar = new ArrayList<>();
        
        if(in.length == 0){
            JOptionPane.showMessageDialog(this, "Nenhum usuário selecionado!");
        }
        else{
            for (int i = 0; i < in.length; i++) {
                usuariosSelecionados.add(usuarios.get(in[i]));
                listaAuxiliar.add(usuarios.get(in[i]));
            }
        
            for(Usuario u : listaAuxiliar){
                usuarios.remove(u);
            }
        
            jListUsuarios.setModel(new JListModel(usuarios));
            jListUsuariosSelecionados.setModel(new JListModel(usuariosSelecionados));
        }
    }//GEN-LAST:event_botaoInsereUsuarioActionPerformed

    private void botaoRemoveUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemoveUsuarioActionPerformed
        int in[] = jListUsuariosSelecionados.getSelectedIndices();
        List<Usuario> listaAuxiliar = new ArrayList<>();
        
        if(in.length == 0){
            JOptionPane.showMessageDialog(this, "Nenhum usuário selecionado!");
        }
        else{
            for (int i = 0; i < in.length; i++) {
                usuarios.add(usuariosSelecionados.get(in[i]));
                listaAuxiliar.add(usuariosSelecionados.get(in[i]));
            }
        
            for(Usuario u : listaAuxiliar){
                usuariosSelecionados.remove(u);
            }
            
            jListUsuarios.setModel(new JListModel(usuarios));
            jListUsuariosSelecionados.setModel(new JListModel(usuariosSelecionados));
        }
    }//GEN-LAST:event_botaoRemoveUsuarioActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        CasoDAO casoDAO = new CasoDAO();

        if(!verificaUsuariosSelecionados().isEmpty()){
            for(Usuario usuarioSelecionado : verificaUsuariosSelecionados()){
                casoDAO.relacionaCasoUsuario(Acesso.Visualizar, usuarioSelecionado.getId(), caso.getId());
            }
        }
        JOptionPane.showMessageDialog(this, "Caso alterado!");
        this.dispose();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaAlterarCaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarCaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarCaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarCaso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaNovoCaso dialog = new TelaNovoCaso(new javax.swing.JFrame(), true);
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
                TelaNovoCaso dialog = new TelaNovoCaso(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoInsereUsuario;
    private javax.swing.JButton botaoRemoveUsuario;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jListUsuarios;
    private javax.swing.JList<String> jListUsuariosSelecionados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelDataCriacao;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelNomeInvestigado;
    // End of variables declaration//GEN-END:variables
}
