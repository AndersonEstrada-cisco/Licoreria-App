package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.UsuarioDAO;
import com.DonLiquox.licoreria.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class UsuarioController {
    @FXML private TextField txtNombre, txtCedula, txtEdad, txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private ComboBox<String> cmbRol;
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colNombre, colRol, colCedula, colCorreo, colClave;
    @FXML private TableColumn<Usuario, Integer> colEdad;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colRol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRol()));
        colCedula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCedula()));
        colEdad.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEdad()).asObject());
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));
        colClave.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("******"));
        cargarTabla();

        tblUsuarios.setOnMouseClicked(e -> {
            Usuario u = tblUsuarios.getSelectionModel().getSelectedItem();
            if (u != null) {
                txtNombre.setText(u.getNombre());
                txtCedula.setText(u.getCedula());
                txtEdad.setText(String.valueOf(u.getEdad()));
                txtCorreo.setText(u.getCorreo());
                txtClave.setText(u.getClave());
                cmbRol.setValue(u.getRol());
            }
        });
        cmbRol.getItems().addAll("Administrador","Reportes","Cajero");
    }

    private void cargarTabla() {
        try {
            usuarioDAO.mostrar();
            tblUsuarios.setItems(usuarioDAO.getUsuarios());
        } catch (Exception e) { mostrarAlerta("Error al cargar: " + e.getMessage()); }
    }

    @FXML
    public void btnguardar() {
        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String correo = txtCorreo.getText();
            String clave = txtClave.getText();
            String rol = cmbRol.getValue();
            Usuario u = new Usuario(0, nombre,cedula,edad,correo,clave,rol);
            usuarioDAO.ingresar(u);
            btnLimpiar();
        } catch (Exception e) { mostrarAlerta(e.getMessage()); }
    }

    @FXML
    public void btnactualizar() {
        Usuario userS = tblUsuarios.getSelectionModel().getSelectedItem();
        if (userS == null){
            mostrarAlerta("Seleccione un elemento");
            return;
        }
        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String correo = txtCorreo.getText();
            String clave = txtClave.getText();
            String rol = cmbRol.getValue();
            Usuario u = new Usuario(userS.getId(), nombre,cedula,edad,correo,clave,rol);
            usuarioDAO.actualizar(u);
            btnLimpiar();
        } catch (Exception e) { mostrarAlerta("Error al actualizar" + e.getMessage()); }
    }

    @FXML
    public void btnElmiinar() {
        Usuario u = tblUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) {
            mostrarAlerta("Seleccione un producto de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar al usuario: " + u.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                usuarioDAO.eliminar(u.getId());
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el al usuario: " + e.getMessage());
            }
        }
    }

    public void btnLimpiar() {
        txtNombre.clear();
        txtCedula.clear();
        txtEdad.clear();
        txtCorreo.clear();
        txtClave.clear();
        cmbRol.getSelectionModel().clearSelection();
    }
    @FXML
    public void btnBuscar(){

    }
    public void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg); alert.show();
    }
}