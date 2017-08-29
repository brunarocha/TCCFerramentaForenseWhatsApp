/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gerenciatela;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author BRUNA
 */
public class GerenciaTelaDispositivo {
    
    private int tamanho;
    private int altura;
    
    private Dimension dimensionSet;
    
    private Dimension framePrincipalDispositivo;
    private Dimension painelInformacao;
    private Dimension painelContatos;
    private Dimension jListContatos;
    private Dimension painelConversa;
    private Dimension tabelaConversa;
    private Dimension painelFiltros;

    public GerenciaTelaDispositivo() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        
        framePrincipalDispositivo = d;
    }

    public Dimension getFramePrincipalDispositivo() {
        tamanho = (int)framePrincipalDispositivo.getWidth()-60;
        altura = (int) (framePrincipalDispositivo.getHeight()-60);
        dimensionSet = new Dimension( tamanho, altura );
        framePrincipalDispositivo = dimensionSet;
        
        return framePrincipalDispositivo;
    }

    public Dimension getPainelInformacao() {
        tamanho = (int) (framePrincipalDispositivo.getWidth()*0.20);
        altura = (int) (framePrincipalDispositivo.getHeight()*0.10);
        dimensionSet = new Dimension( tamanho, altura );
        painelInformacao = dimensionSet;
        
        return painelInformacao;
    }

    public Dimension getPainelContatos() {
        tamanho = (int) (framePrincipalDispositivo.getWidth()*0.18);
        altura = (int) (framePrincipalDispositivo.getHeight()*0.60);
        dimensionSet = new Dimension( tamanho, altura );
        painelContatos = dimensionSet;
        
        return painelContatos;
    }

    public Dimension getJListContatos() {
        tamanho = (int) (painelContatos.getWidth()*0.90);
        altura = (int) (painelContatos.getHeight()*0.40);
        dimensionSet = new Dimension( tamanho, altura );
        jListContatos = dimensionSet;
        
        return jListContatos;
    }

    public Dimension getPainelConversa() {
        tamanho = (int) (framePrincipalDispositivo.getWidth()*0.55);
        altura = (int) (framePrincipalDispositivo.getHeight()*0.90);
        dimensionSet = new Dimension( tamanho, altura );
        painelConversa = dimensionSet;
        
        return painelConversa;
    }

    public Dimension getTabelaConversa() {
        tamanho = (int)painelConversa.getWidth()-40;
        altura = (int)painelConversa.getHeight()-150;
        dimensionSet = new Dimension( tamanho, altura );
        tabelaConversa = dimensionSet;
        
        return tabelaConversa;
    }

    public Dimension getPainelFiltros() {
        tamanho = (int) (framePrincipalDispositivo.getWidth()*0.22);
        altura = (int) (framePrincipalDispositivo.getHeight()*0.78);
        dimensionSet = new Dimension( tamanho, altura );
        painelFiltros = dimensionSet;
        
        return painelFiltros;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
