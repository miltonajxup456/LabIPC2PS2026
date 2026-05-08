/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author millin-115
 */
public class EntregaService {
    
    public void descargaDeEntrega(OutputStream out, String pathArchivo) {
        File file = new File(pathArchivo);
        
        try (ZipOutputStream zos = new ZipOutputStream(out);
                FileInputStream fis = new FileInputStream(file)) {
            ZipEntry entry = new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            
            byte[] buffer = new byte[4096];
            int len;
            
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            
            zos.closeEntry();
        } catch (IOException e) {
            System.out.println("Error al descargar entrega "+e);
        }
    }
    
}
