package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {
    @FXML private TextField txtNombre, txtCedula, txtEdad, txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private ComboBox<String> cmbRol;
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colNombre, colRol, colCedula,colCorreo,colClave;
    @FXML private TableColumn<Usuario,Integer> colEdad;
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        cmbRol.getItems().addAll("Administrador", "Cajero", "Reportes");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        tblUsuarios.setItems(listaUsuarios);
    }

    @FXML
    public void btnguardar() {
        String nombre = txtNombre.getText();
        String rol = cmbRol.getValue();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();
        //
        btnLimpiar();
    }

    @FXML
    public void btnactualizar() {
        Usuario seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            //
        }
    }

    @FXML
    public void btnElmiinar() {
        Usuario seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaUsuarios.remove(seleccionado);
            //
        }
    }

    @FXML
    public void btnLimpiar() {
        txtNombre.clear();
        txtCedula.clear();
        txtEdad.clear();
        txtCorreo.clear();
        txtClave.clear();
        cmbRol.getSelectionModel().clearSelection();
    }
}