package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
public class DashboardController {
    @FXML private BorderPane dashArea;
    @FXML private Label lblUser;
    @FXML private Label lblRol;
    @FXML private Button btnUsuarios;
    @FXML private Button btnClientes;
    @FXML private Button btnVentas;
    @FXML private Button btnProductos;


    public void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent vista = loader.load();
            dashArea.getChildren().clear();
            dashArea.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mostrarUser(Usuario u){
        lblUser.setText(u.getNombre());
        lblRol.setText(u.getRol());
        cargarVentanaRol(u.getRol());
        if (u.getRol().equals("Cajero")) {
            btnVentas();
        } else if (u.getRol().equals("Reportes")) {
            btnReportes();
        } else {
            btnUsuarios();
        }
    }
    public void cargarVentanaRol(String rol){
        switch (rol){
            case "Administrador":
                cargarVista("Administrador.fxml");
                break;
            case "Cajero":
                ocultarbtn(btnUsuarios);
                ocultarbtn(btnProductos);
                break;
            case "Reportes":
                ocultarbtn(btnProductos);
                ocultarbtn(btnClientes);
                ocultarbtn(btnUsuarios);
                ocultarbtn(btnVentas);
                break;
        }
    }
    public void ocultarbtn(Button btn){
        btn.setVisible(false);
        btn.setManaged(false);
    }
    @FXML public void btnRegresar(){
        Stage stage = (Stage) lblUser.getScene().getWindow();
        stage.close();
    }

    @FXML public void btnUsuarios() {
        cargarVista("Usuarios.fxml");
    }

    @FXML public void btnClientes(){
        cargarVista("Clientes.fxml");
    }
    @FXML public void btnVentas(){
        cargarVista("Ventas.fxml");
    }
    @FXML public void btnProductos(){
        cargarVista("Productos.fxml");
    }
    @FXML public void btnReportes(){
        cargarVista("Reportes.fxml");
    }
}
