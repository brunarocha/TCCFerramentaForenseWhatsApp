/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listmodel;

import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author BRUNA
 * @param <T>
 */
public class JListModel<T> implements ListModel{
    
    private final List<T> dados;

    public JListModel(List<T> dados) {
        this.dados = dados;
    }
    
    @Override
    public int getSize() {
        return this.dados.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.dados.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    
    }
    
}