/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

/**
 *
 * @author BRUNA
 */
public class TabelaModeloUsuario extends AbstractTableModel{
    
    private final List<Usuario> usuarios;
    private final String colunas[] = {"ID", "Nome"};

    public TabelaModeloUsuario(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Override
    public int getRowCount() {
        return this.usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return this.colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = this.usuarios.get(rowIndex);
        
        switch(columnIndex){
            case 0: return usuario.getId();
            case 1: return usuario.getNome();
        }
        
        return null;
    }
    
}
