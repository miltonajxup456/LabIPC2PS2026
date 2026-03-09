/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_ipc2_ps;

/**
 *
 * @author millin-115
 */
public class Roles {
    
    public final int superAdmin;
    public final int admin;
    public final int usuario;
    
    public Roles() {
        this.superAdmin = 1;
        this.admin = 2;
        this.usuario = 3;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public int getAdmin() {
        return admin;
    }

    public int getUsuario() {
        return usuario;
    }
    
}
