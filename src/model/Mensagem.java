/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author BRUNA
 */
public class Mensagem {
    
    private Long id;
    private Dispositivo dispositivo;
    private Contato key_remote_jid; 
    private String remote_resource; 
    private boolean key_from_me;
    private String data;
    private Date date;
    private Time time;
    private String media_url;
    private String media_mime_type;
    private int media_size;
    private String media_name;
    private String media_caption;
    
    public Mensagem() {
    }

    public Mensagem(Long id, Dispositivo dispositivo, Contato key_remote_jid, String remote_resource, boolean key_from_me, String data, Date date, Time time, String media_url, String media_mime_type, int media_size, String media_name, String media_caption) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.key_remote_jid = key_remote_jid;
        this.remote_resource = remote_resource;
        this.key_from_me = key_from_me;
        this.data = data;
        this.date = date;
        this.time = time;
        this.media_url = media_url;
        this.media_mime_type = media_mime_type;
        this.media_size = media_size;
        this.media_name = media_name;
        this.media_caption = media_caption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Contato getKey_remote_jid() {
        return key_remote_jid;
    }

    public void setKey_remote_jid(Contato key_remote_jid) {
        this.key_remote_jid = key_remote_jid;
    }

    public String getRemote_resource() {
        return remote_resource;
    }

    public void setRemote_resource(String remote_resource) {
        this.remote_resource = remote_resource;
    }

    public boolean isKey_from_me() {
        return key_from_me;
    }

    public void setKey_from_me(boolean key_from_me) {
        this.key_from_me = key_from_me;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_mime_type() {
        return media_mime_type;
    }

    public void setMedia_mime_type(String media_mime_type) {
        this.media_mime_type = media_mime_type;
    }

    public int getMedia_size() {
        return media_size;
    }

    public void setMedia_size(int media_size) {
        this.media_size = media_size;
    }

    public String getMedia_name() {
        return media_name;
    }

    public void setMedia_name(String media_name) {
        this.media_name = media_name;
    }

    public String getMedia_caption() {
        return media_caption;
    }

    public void setMedia_caption(String media_caption) {
        this.media_caption = media_caption;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "data=" + data + '}';
    }
    
    

}