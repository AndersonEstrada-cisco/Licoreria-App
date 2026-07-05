package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.db.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtClave;
    @FXML
    private ComboBox<String> cmbRol;

    public void btnIngresar() throws SQLException {
        String user = txtUser.getText().trim();
        String clave = txtClave.getText().trim();
        String rolIngresado = cmbRol.getValue();

        if (user.isEmpty() || clave.isEmpty()) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos");
        }
        String sql = "SELECT clave, rol FROM usuarios WHERE correo = ?";

        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, user);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                String claveBD = rs.getString("clave");
                String rol = rs.getString("rol");
                if (clave.equals(claveBD) || rolIngresado.equals(rol)) {
                    DashboardController c = new DashboardController();
                    //c.cargarVista();
                    //return;
                } else {
                    throw new Exception("Datos no registrados");
                }
            } else {
                throw new Exception("Los datos estan mal ingresados");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al ingresar: " + e.getMessage());
        }
    }
    public void initialize(){
        cmbRol.getItems().addAll("Administrador","Cajero","Cliente");
    }
}
