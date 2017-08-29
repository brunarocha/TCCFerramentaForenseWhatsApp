/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author BRUNA
 */
public class ScriptVerificaDispositivo {
    
    public static boolean scriptExtraiBuildProp(){
        String diretorio_comando = new File("platform-tools/comandoverificadispositivo.bat").getAbsolutePath();
        String diretorio_adb = new File("platform-tools").getAbsolutePath();
        String diretorio_salvar = new File("platform-tools/backup").getAbsolutePath();
         
        Process p;
        
        try {
            p = Runtime.getRuntime().exec("\""+diretorio_comando+"\"  \""+diretorio_adb+"\" \""+diretorio_salvar+"\"");
            
            if(p.waitFor() == 0){
                File file = new File(diretorio_salvar+"/build.prop");
                
                if(file.exists()){
                    return true;
                }
                else{
                    return false;
                }
            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao verificar dispositivo! "+ex.getMessage());
        }
        
        return false;
    }
}
