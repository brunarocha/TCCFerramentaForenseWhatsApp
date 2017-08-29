/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author BRUNA
 */
public class Caso {
    
    private Long id;
    private Usuario usuario;
    private String descricao;
    private String nome_investigado;
    private Calendar data_criacao;
    private String diretorio;
    private List<Dispositivo> dispositivos;
    private List<Usuario> usuarios_acesso;
    
    public Caso() {
    }

    public Caso(Long id, Usuario usuario, String descricao, String nome_investigado, Calendar data_criacao, String diretorio, List<Dispositivo> dispositivos, List<Usuario> usuarios_acesso) {
        this.id = id;
        this.usuario = usuario;
        this.descricao = descricao;
        this.nome_investigado = nome_investigado;
        this.data_criacao = data_criacao;
        this.diretorio = diretorio;
        this.dispositivos = dispositivos;
        this.usuarios_acesso = usuarios_acesso;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome_investigado() {
        return nome_investigado;
    }

    public void setNome_investigado(String nome_investigado) {
        this.nome_investigado = nome_investigado;
    }

    public Calendar getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Calendar data_criacao) {
        this.data_criacao = data_criacao;
    }
    
    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public List<Usuario> getUsuarios_acesso() {
        return usuarios_acesso;
    }

    public void setUsuarios_acesso(List<Usuario> usuarios_acesso) {
        this.usuarios_acesso = usuarios_acesso;
    }

    @Override
    public String toString() {
        return descricao;
    }

}