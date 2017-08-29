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
 * @author Bruna
 */
public class GerenciaTelaCaso {
    
    private int tamanho;
    private int altura;
    
    private Dimension dimensionSet;
    
    private Dimension framePrincipalCaso;
    private Dimension painelBarraFerramentas;
    private Dimension painelCasos;
    private Dimension painelDispositivos;
    private Dimension painelInferior;
    private Dimension tabelaCasos;
    private Dimension arvoreDispositivos;

    public GerenciaTelaCaso() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        framePrincipalCaso = d;
    }

    public Dimension getFramePrincipalCaso() {
        return framePrincipalCaso;
    }
    
    
    public Dimension getPainelBarraFerramentas() {
        tamanho = (int) (framePrincipalCaso.width*0.10);
        altura = framePrincipalCaso.width;
        dimensionSet = new Dimension( tamanho, altura );
        painelBarraFerramentas = dimensionSet;
        
        return painelBarraFerramentas;
    }

    public Dimension getPainelCasos() {
        tamanho = (int) (framePrincipalCaso.width*0.50);
        altura = (int) (framePrincipalCaso.getHeight()*0.70);
        dimensionSet = new Dimension( tamanho, altura );
        painelCasos = dimensionSet;
        
        return painelCasos;
    }

    public Dimension getPainelDispositivos() {
        tamanho = (int) (framePrincipalCaso.width*0.20);
        altura = (int) (framePrincipalCaso.getHeight()*0.70);
        dimensionSet = new Dimension( tamanho, altura );
        painelDispositivos = dimensionSet;
        
        return painelDispositivos;
    }

    public Dimension getPainelInferior() {
        tamanho = framePrincipalCaso.width;
        altura = (int) (framePrincipalCaso.getHeight()*0.05);
        dimensionSet = new Dimension( tamanho, altura );
        painelInferior = dimensionSet;
        
        return painelInferior;
    }

    public Dimension getTabelaCasos() {
        tamanho = (int)painelCasos.getWidth()-40;
        altura = (int)painelCasos.getHeight()-40;
        dimensionSet = new Dimension( tamanho, altura );
        tabelaCasos = dimensionSet;
        
        return tabelaCasos;
    }

    public Dimension getArvoreDispositivos() {
        tamanho = (int)painelDispositivos.getWidth()-40;
        altura = (int) (painelDispositivos.getHeight()-120);
        dimensionSet = new Dimension( tamanho, altura );
        arvoreDispositivos = dimensionSet;
        
        return arvoreDispositivos;
    }
    
}
