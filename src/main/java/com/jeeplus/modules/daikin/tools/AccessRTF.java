package com.jeeplus.modules.daikin.tools;

import java.io.*;  

import javax.swing.text.BadLocationException;  
import javax.swing.text.DefaultStyledDocument;  
import javax.swing.text.rtf.*;  
  
public class AccessRTF {  
    String text;  
    DefaultStyledDocument dsd;  
    RTFEditorKit rtf;  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
          
        AccessRTF readRTF=new AccessRTF();  
        readRTF.readRtf(new File("e:\\1.rtf"));  
        readRTF.writeRtf(new File("e:\\2.rtf"));  
    }  
    public void readRtf(File in) {  
        rtf=new RTFEditorKit();  
        dsd=new DefaultStyledDocument();  
        try {  
            rtf.read(new FileInputStream(in), dsd, 0);  
            text = new String(dsd.getText(0, dsd.getLength()));  
            System.out.println(text);  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (BadLocationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
    public void writeRtf(File out) {  
        try {  
            rtf.write(new FileOutputStream(out), dsd, 0, dsd.getLength());  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (BadLocationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}  