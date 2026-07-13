package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtClave;
    @FXML
    private ComboBox<String> cmbRol;

    public void btnIngresar() {
        String user = txtUser.getText().trim();
        String clave = txtClave.getText().trim();
        String rolIngresado = cmbRol.getValue();

        if (user.isEmpty() || clave.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Los campos no pueden estar").show();
            return;
        }

        if(cmbRol.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Debe seleccionar un rol").show();
            return;
        }

        String sql = "SELECT id_usuario, nombre, cedula, edad, correo, clave, rol FROM usuarios WHERE correo = ?";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, user);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                String claveBD = rs.getString("clave");
                String rol = rs.getString("rol");
                if (clave.equals(claveBD) && rolIngresado.equals(rol)) {
                    int id = rs.getInt("id_usuario");
                    String nombre = rs.getString("nombre");
                    String cedula = rs.getString("cedula");
                    int edad = rs.getInt("edad");
                    String correo = rs.getString("correo");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/DonLiquox/licoreria/view/dashboard.fxml"));
                    Parent root = loader.load();
                    DashboardController dashc = loader.getController();

                    Usuario u = new Usuario(id, nombre, cedula, edad, correo, claveBD, rol);
                    dashc.mostrarUser(u);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage loginStage = (Stage) txtUser.getScene().getWindow();
                    loginStage.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "La contraseña o el rol no son correctos").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "El usuario y contraseña no es el correcto").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "El usuario y contraseña no es el correcto").show();
        }
    }
    public void initialize(){
        cmbRol.getItems().addAll("Administrador","Cajero","Reportes");
    }
}
