/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.relatorio;

/**
 *
 * @author BRUNA
 */
public class Relatorio {
    
    private Long id;
    private String nome;
    private int mensagens_enviadas;
    private int mensagens_recebidas;

    public Relatorio() {
    }

    public Relatorio(Long id, String nome, int mensagens_enviadas, int mensagens_recebidas) {
        this.id = id;
        this.nome = nome;
        this.mensagens_enviadas = mensagens_enviadas;
        this.mensagens_recebidas = mensagens_recebidas;
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

    public int getMensagens_enviadas() {
        return mensagens_enviadas;
    }

    public void setMensagens_enviadas(int mensagens_enviadas) {
        this.mensagens_enviadas = mensagens_enviadas;
    }

    public int getMensagens_recebidas() {
        return mensagens_recebidas;
    }

    public void setMensagens_recebidas(int mensagens_recebidas) {
        this.mensagens_recebidas = mensagens_recebidas;
    }
    
}
