package com.DonLiquox.licoreria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String url = "jdbc:postgresql://ep-rapid-brook-awue7rnb-pooler.c-12.us-east-1.aws.neon.tech/neondb?sslmode=require";
    private static final String user ="neondb_owner";
    private static final String clave = "npg_3Aho4EJrdBvs";
    public static Connection getConexion() throws SQLException {
        try{
            return DriverManager.getConnection(url,user,clave);
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Connection con = getConexion();
            if (con != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }


}
