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
public class Contato {
    
    private Long id;
    private Dispositivo dispositivo;
    private String jid;
    private String status;
    private Date status_date;
    private Time status_time;
    private String number;
    private String display_name;
    private String wa_name;
    
    
    public Contato() {
    }

    public Contato(String jid) {
        this.jid = jid;
    }
    
    public Contato(Long id, Dispositivo dispositivo, String jid, String status, Date status_date, Time status_time, String number, String display_name, String wa_name) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.jid = jid;
        this.status = status;
        this.status_date = status_date;
        this.status_time = status_time;
        this.number = number;
        this.display_name = display_name;
        this.wa_name = wa_name;
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

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatus_date() {
        return status_date;
    }

    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    public Time getStatus_time() {
        return status_time;
    }

    public void setStatus_time(Time status_time) {
        this.status_time = status_time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getWa_name() {
        return wa_name;
    }

    public void setWa_name(String wa_name) {
        this.wa_name = wa_name;
    }

    @Override
    public String toString() {
        if(display_name != null){
            return display_name;
        }
        else if(wa_name != null){
            return wa_name;
        }
        else{
            return jid;
        }
    }
}