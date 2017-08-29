/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author BRUNA
 */
public class ConverteData {
    private static final Calendar calendarStatic = Calendar.getInstance();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static String getStringDataAtual() {
        return simpleDateFormat.format(calendarStatic.getTime());        
    }
    
    
    public static String getStringDeCalendar(Calendar calendarParametro){
        Date data = calendarParametro.getTime();
        return simpleDateFormat.format(data);
    }
    
    
    public static String getStringDeDate(Date date){
        return simpleDateFormat.format(date);
    }
    
    
    public static String getStringDeTime(Time time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        
        return c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
    }
    
    
    public static Calendar getCalendarDeDate(Date date){
        Calendar calendarSet = Calendar.getInstance();
        calendarSet.setTime(date);
        
        return calendarSet;
    }
    
    
    public static java.sql.Date getDateDeString(String data){
        try {
            simpleDateFormat.parse(data);
            Calendar calendar = simpleDateFormat.getCalendar();
            return new java.sql.Date(calendar.getTimeInMillis());
        } catch (ParseException ex) {
            return null;
        }      
    }
    
    
    public static java.sql.Date getDateDataAtual(){
        return new java.sql.Date(calendarStatic.getTimeInMillis());      
    }
    
    
    public static java.sql.Date getDateDeTimestamp(Timestamp timestamp){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());
        
        return new java.sql.Date(c.getTimeInMillis());
    }
    
    
    public static java.sql.Time getTimeDeString(String horaRecebida){
        int hora = Integer.parseInt(horaRecebida.substring(0, horaRecebida.indexOf(":")));
        int minuto = Integer.parseInt(horaRecebida.substring(horaRecebida.indexOf(":")+1));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minuto);
        Time time = new Time(c.getTimeInMillis());

        return time;
    }
    
    
    public static java.sql.Time getTimeDeTimestamp(Timestamp timestamp){
        return new Time(timestamp.getTime());
    }
    
}
