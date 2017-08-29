/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author BRUNA
 */
public class AcessoBanco {
    
    private String usuario;
    private String senha;
    private String host;
    private String porta;
    private String banco;

    public AcessoBanco() {
    }

    public AcessoBanco(String usuario, String senha, String host, String porta, String banco) {
        this.usuario = usuario;
        this.senha = senha;
        this.host = host;
        this.porta = porta;
        this.banco = banco;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
    
}