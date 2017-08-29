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
public class Dispositivo  {
    
    private Long id;
    private String nome;
    private String fabricante;
    private String idioma;
    private String modelo;
    private String numero_versao;
    private String numero_modelo;
    private String versao_android;
    private String versao_sistema;
    private String diretorio;
    private String tabela_contato;
    private String tabela_mensagem;
    private Caso caso;
    
    public Dispositivo() {
    }

    public Dispositivo(Long id) {
        this.id = id;
    }
    
    
    public Dispositivo(Long id, String nome, String fabricante, String idioma, String modelo, String numero_versao, String numero_modelo, String versao_android, String versao_sistema, String diretorio, String tabela_contato, String tabela_mensagem, Caso caso) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.idioma = idioma;
        this.modelo = modelo;
        this.numero_versao = numero_versao;
        this.numero_modelo = numero_modelo;
        this.versao_android = versao_android;
        this.versao_sistema = versao_sistema;
        this.diretorio = diretorio;
        this.tabela_contato = tabela_contato;
        this.tabela_mensagem = tabela_mensagem;
        this.caso = caso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumero_versao() {
        return numero_versao;
    }

    public void setNumero_versao(String numero_versao) {
        this.numero_versao = numero_versao;
    }

    public String getNumero_modelo() {
        return numero_modelo;
    }

    public void setNumero_modelo(String numero_modelo) {
        this.numero_modelo = numero_modelo;
    }

    public String getVersao_android() {
        return versao_android;
    }

    public void setVersao_android(String versao_android) {
        this.versao_android = versao_android;
    }

    public String getVersao_sistema() {
        return versao_sistema;
    }

    public void setVersao_sistema(String versao_sistema) {
        this.versao_sistema = versao_sistema;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getTabela_contato() {
        return tabela_contato;
    }

    public void setTabela_contato(String tabela_contato) {
        this.tabela_contato = tabela_contato;
    }

    public String getTabela_mensagem() {
        return tabela_mensagem;
    }

    public void setTabela_mensagem(String tabela_mensagem) {
        this.tabela_mensagem = tabela_mensagem;
    }
    
    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    @Override
    public String toString() {
        return nome;
    }

}