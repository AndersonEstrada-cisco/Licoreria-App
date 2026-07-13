package com.DonLiquox.licoreria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String url = "jdbc:postgresql://localhost:5432/donliquox_db";
    private static final String user ="postgres";
    private static final String clave = "admin";
    public static Connection getConneccion() throws SQLException {
        try{
            return DriverManager.getConnection(url,user,clave);
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar a la base de datos" + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Connection con = getConneccion();
            if (con != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }


}
