/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Caso;
import util.ConverteData;

/**
 *
 * @author BRUNA
 */
public class TabelaModeloCaso extends AbstractTableModel{
    
    private final List<Caso> casos;
    private final String colunas[] = {"Descrição", "Investigado", "Data de criação"};

    public TabelaModeloCaso(List<Caso> casos) {
        this.casos = casos;
    }
    
    @Override
    public int getRowCount() {
        return this.casos.size();
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
        Caso caso = this.casos.get(rowIndex);
        
        switch(columnIndex){
            case 0: return caso.getDescricao();
            case 1: return caso.getNome_investigado();
            case 2: return ConverteData.getStringDeCalendar(caso.getData_criacao());
            
        }
        
        return null;
    }
    
}
