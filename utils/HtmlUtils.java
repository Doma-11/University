/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boric
 */
public class HtmlUtils {
    
     public static String createDocumentation() throws IOException {
        
        StringBuilder htmlBuilder = new StringBuilder();
        
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("<title>Project documentation</title>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        
        Files.walk(Paths.get(".\\src"))
                .filter(f -> (!f.getFileName().toString().endsWith("fxml") && !f.getFileName().toString().endsWith("css")))
                .forEach((it) -> {
                    File fileItem = it.toFile();
                    if(!fileItem.isDirectory() && !it.toString().contains("resources") && !it.toString().contains("controller")){
                        try {
                            System.out.println(it.toString());
                            String fileName = it.toString().replace("\\", ".");
                            System.out.println(fileName);
                            fileName = fileName.substring(6, fileName.lastIndexOf("."));
                            System.out.println(fileName);
                            Class clazz = Class.forName(fileName);
                            htmlBuilder.append("<h1>");
                            htmlBuilder.append(it.getFileName());
                            htmlBuilder.append("</h1>");
                            ReflectionUtils.readClassAndMembersInfo( clazz, htmlBuilder);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(HtmlUtils.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
        
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");
        
        return htmlBuilder.toString();
    }
}
