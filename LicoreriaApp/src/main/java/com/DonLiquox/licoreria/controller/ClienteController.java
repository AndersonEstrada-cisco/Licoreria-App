package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    @FXML private TextField txtNombre, txtCedula, txtTelefono, txtEmail, txtDireccion;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre, colCedula, colTelefono, colEmail,colDireccion;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tblClientes.setItems(listaClientes);
    }

    @FXML
    public void btnGuardar() {
        String nombre = txtNombre.getText();

        System.out.println("Guardando cliente: " + nombre);
        btnLimpiar();
    }

    @FXML
    public void btnActualizar() {
        Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {

        }
    }

    @FXML
    public void btnEliminar() {
        Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaClientes.remove(seleccionado);
        }
    }

    @FXML
    public void btnLimpiar() {
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
    }
}