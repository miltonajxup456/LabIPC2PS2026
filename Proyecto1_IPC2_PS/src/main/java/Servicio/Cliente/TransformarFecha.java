/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio.Cliente;

import Model.Cliente.ClienteDB;
import Model.Cliente.ClienteRequest;

/**
 *
 * @author millin-115
 */
public class TransformarFecha {
    
    public String transformarALocal(ClienteRequest request) {
        String fecha[] = request.getFechaNac().split("-");
        String fechaLocal = fecha[0]+"/"+fecha[1]+"/"+fecha[2];
        return fechaLocal;
    }
    
    public String transformarAJson(ClienteDB cliente) {
        String fecha[] = cliente.getFechaNac().split("/");
        String fechajson = fecha[2]+"-"+fecha[1]+"-"+fecha[0];
        return fechajson;
    }
    
}
