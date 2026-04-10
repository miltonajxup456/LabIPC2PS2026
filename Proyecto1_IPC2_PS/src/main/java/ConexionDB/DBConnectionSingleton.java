/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author millin-115
 */
public class DBConnectionSingleton {
    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "Proyecto1_PS2026";
    private static final String USER_NAME = "usuarioP1";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;

    private static DBConnectionSingleton instance;

    private Connection connection;
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ocurrio un error en la base de datos "+e);
        }
        return null;
    }
    

//    private DBConnectionSingleton() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public static DBConnectionSingleton getInstance() {
//        if (instance == null) {
//            instance = new DBConnectionSingleton();
//        }
//        return instance;
//    }
}
