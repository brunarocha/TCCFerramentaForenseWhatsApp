/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BRUNA
 */
public class TabelaModeloRelatorio extends AbstractTableModel{
    
    private final List<String> relatorios;
    private final String colunas[] = {"Descrição"};

    public TabelaModeloRelatorio(List<String> relatorios) {
        this.relatorios = relatorios;
    }
    
    @Override
    public int getRowCount() {
        return this.relatorios.size();
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
        String relatorio = this.relatorios.get(rowIndex);
        
        switch(columnIndex){
            case 0: return relatorio;
        }
        
        return null;
    }
    
}
