/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Bruna
 */
public class Configuracao {
    
    private Long id;
    private Usuario usuario;
    private String diretorio_caso;

    public Configuracao() {
    }

    public Configuracao(Long id, Usuario usuario, String diretorio_caso) {
        this.id = id;
        this.usuario = usuario;
        this.diretorio_caso = diretorio_caso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDiretorio_caso() {
        return diretorio_caso;
    }

    public void setDiretorio_caso(String diretorio_caso) {
        this.diretorio_caso = diretorio_caso;
    }
}