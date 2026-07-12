package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ClienteDAO;
import com.DonLiquox.licoreria.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ClienteController {

    @FXML private TextField txtNombre, txtCedula, txtTelefono, txtEmail, txtDireccion, txtEdad;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colId,colEdad;
    @FXML private TableColumn<Cliente, String> colNombre, colCedula, colTelefono, colEmail,colDireccion;
    private ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colEdad.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEdad()).asObject());
        colCedula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCedula()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDireccion()));
        cargarTabla();
        tblClientes.setOnMouseClicked(event -> {
            Cliente c = tblClientes.getSelectionModel().getSelectedItem();
            if (c != null) {
                txtNombre.setText(c.getNombre());
                txtCedula.setText(c.getCedula());
                txtEdad.setText(String.valueOf(c.getEdad()));
                txtTelefono.setText(c.getTelefono());
                txtEmail.setText(c.getEmail());
                txtDireccion.setText(c.getDireccion());
            }
        });
    }
    private void cargarTabla() {
        try {
            clienteDAO.mostrar();
            tblClientes.setItems(clienteDAO.getClientes());
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar la tabla: " + e.getMessage());
        }
    }

    @FXML
    public void btnGuardar() {
        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            Cliente c = new Cliente(0,nombre,cedula,edad,telefono,email,direccion);
            clienteDAO.ingresar(c);
            cargarTabla();
            btnLimpiar();
        } catch (Exception e) {
            mostrarAlerta("Error: Verifique que la edad sea numérica y campos no vacíos.");
        }
    }

    @FXML
    public void btnActualizar() {
        Cliente clienteS = tblClientes.getSelectionModel().getSelectedItem();
        if (clienteS == null) {
            mostrarAlerta("Seleccione un cliente");
            return;
        }
        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            Cliente c = new Cliente(clienteS.getId(),nombre,cedula,edad,telefono,email,direccion);
            clienteDAO.actualizar(c);
            cargarTabla();
            btnLimpiar();
        } catch (Exception e) { mostrarAlerta("Error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    public void btnEliminar() {
        Cliente c = tblClientes.getSelectionModel().getSelectedItem();
        if (c == null) {
            mostrarAlerta("Seleccione un cliente de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar al cliente: " + c.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                clienteDAO.eliminar(c.getId());
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el producto: " + e.getMessage());
            }
        }
    }

    @FXML
    public void btnBuscar(){

    }
    public void btnLimpiar() {
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error: " + mensaje);
        alert.show();
    }
}