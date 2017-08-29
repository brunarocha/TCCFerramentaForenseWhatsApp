/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import model.AcessoBanco;
import model.Dispositivo;

/**
 *
 * @author BRUNA
 */
public class ManipulaArquivos {
    
    
    public static Dispositivo extraiDadosPropriedades(Dispositivo dispositivo){
        String diretorio = new File("platform-tools/backup").getAbsolutePath();
        File filePropriedades = new File(diretorio+"/build.prop");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePropriedades));
            
            while(br.ready()){
                String linha = br.readLine();
                
                if(linha.contains("ro.product.display")){
                    dispositivo.setNumero_modelo(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.build.version.full")){
                    dispositivo.setVersao_sistema(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.build.id")){
                    dispositivo.setNumero_versao(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.product.model")){
                    dispositivo.setModelo(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.product.manufacturer")){
                    dispositivo.setFabricante(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.product.locale")){
                    dispositivo.setIdioma(linha.substring(linha.indexOf("=")+1));
                }
                
                if(linha.contains("ro.build.version.release")){
                    dispositivo.setVersao_android(linha.substring(linha.indexOf("=")+1));
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo BUILD.PROP não encontrado! "+ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Exceção IO "+ex.getMessage());
        }
        return dispositivo;
    }
    
    
    public static String extraiDiretorioSDCard(){
        String diretorio = new File("platform-tools").getAbsolutePath();
        File fileLog = new File(diretorio+"/log.txt");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLog));
            
            while(br.ready()){
                String linha = br.readLine();
                
                if(linha.contains("extSdCard")){
                    return "storage/extSdCard";
                }
                
                for(int i=0; i< 10; i++){
                    if(linha.contains(""+i)){
                        return "storage/"+linha;
                    }
                }
            }
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo LOG.txt não encontrado! "+ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Exceção IO "+ex.getMessage());
        }
        return null;
    }
    
    
    public static AcessoBanco lerArquivoConfiguracaoBanco(){
        File file = new File("configuracao_banco.txt");
        AcessoBanco acessoBanco = new AcessoBanco();
        
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while(bufferedReader.ready()){
                String linha = bufferedReader.readLine();
                String valor = linha.substring(0, linha.indexOf(":"));
               
                switch(valor){
                    case "usuario" : acessoBanco.setUsuario(linha.substring(linha.indexOf(":")+1));
                    case "senha" : acessoBanco.setSenha(linha.substring(linha.indexOf(":")+1));
                    case "host" : acessoBanco.setHost(linha.substring(linha.indexOf(":")+1));
                    case "porta" : acessoBanco.setPorta(linha.substring(linha.indexOf(":")+1));
                    case "banco" : acessoBanco.setBanco(linha.substring(linha.indexOf(":")+1));
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo de configuração do banco de dados não encontrado! "+ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao verificar arquivo de configuração do banco dados! "+ex);
        }
        
        return acessoBanco;
    }
    
    
    public static boolean salvaConfiguracao(AcessoBanco acesso){
        File file = new File("configuracao_banco.txt");
        
        try {
            FileWriter fileWriter = new FileWriter(file);
            
            fileWriter.write("usuario:" + acesso.getUsuario());
            fileWriter.write("\r\n");
            fileWriter.write("senha:"+String.valueOf(acesso.getSenha()));
            fileWriter.write("\r\n");
            fileWriter.write("host:"+acesso.getHost());
            fileWriter.write("\r\n");
            fileWriter.write("porta:"+acesso.getPorta());
            fileWriter.write("\r\n");
            fileWriter.write("banco:"+acesso.getBanco());
            fileWriter.flush();
            
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao verificar arquivo de configuração do banco dados! "+ex);
            return false;
        }
    }
}
