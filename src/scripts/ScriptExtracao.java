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
public class ScriptExtracao {
    
    public static boolean scriptExtraiBanco(String diretorio, String diretorioSDCard){
        String diretorioComando = new File("platform-tools/comandoextraiarquivos.bat").getAbsolutePath();
        String diretorioPrincipal = new File("platform-tools").getAbsolutePath();
        
        Process p;
        try {
            p = Runtime.getRuntime().exec(" \""+diretorioComando+"\" \""+diretorioPrincipal+"\" \""+diretorio+"\" "+diretorioSDCard+"");
            if(p.waitFor() == 0){
                return true;
            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao extrair dos dados "+ex.getMessage());
        }
        return false;
    }
    
}
