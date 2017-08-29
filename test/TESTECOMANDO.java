
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BRUNA
 */
public class TESTECOMANDO {
    
    public static void main(String[] args) {
        String path = new File("platform-tools/comando.bat").getAbsolutePath();
        String pathprincipal = new File("platform-tools").getAbsolutePath();
        String diretorio = new File("platform-tools").getAbsolutePath();
        //System.out.println(diretorio);
        Process p;
        
        try {
            p = Runtime.getRuntime().exec("cmd.exe /c "+path+" "+pathprincipal+" \""+diretorio+"\"");

            if(p.waitFor() == 0){
                System.out.println("TERMINOU");
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao extrair dos dados "+ex.getMessage());
        }
    }
}
