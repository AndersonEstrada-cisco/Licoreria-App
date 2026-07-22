package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ClienteDAO;
import com.DonLiquox.licoreria.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ClienteController {

    @FXML private TextField txtNombre, txtCedula, txtTelefono, txtEmail, txtDireccion, txtEdad;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colId,colEdad;
    @FXML private TableColumn<Cliente, String> colNombre, colCedula, colTelefono, colEmail,colDireccion;
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ObservableList<Cliente> todosClientes = FXCollections.observableArrayList();

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
            todosClientes.clear();
            todosClientes.addAll(clienteDAO.getClientes());
            tblClientes.setItems(todosClientes);
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar la tabla: " + e.getMessage());
        }
    }

    @FXML
    public void btnGuardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del cliente");
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la cedula del cliente");
            return;
        }
        if (txtEdad.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la edad del cliente");
            return;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el telefono del cliente");
            return;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el correo del cliente");
            return;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la direccion del cliente");
            return;
        }
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("La edad debe ser un numero entero");
            return;
        }
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        try {
            if (clienteDAO.existeCedula(cedula)) {
                mostrarAlerta("Ya existe un cliente con esa cedula");
                return;
            }
            Cliente c = new Cliente(0, nombre, cedula, edad, telefono, email, direccion);
            clienteDAO.ingresar(c);
            cargarTabla();
            btnLimpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    public void btnActualizar() {
        Cliente clienteS = tblClientes.getSelectionModel().getSelectedItem();
        if (clienteS == null) {
            mostrarAlerta("Seleccione un cliente de la tabla");
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del cliente");
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la cedula del cliente");
            return;
        }
        if (txtEdad.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la edad del cliente");
            return;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el telefono del cliente");
            return;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el correo del cliente");
            return;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la direccion del cliente");
            return;
        }
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("La edad debe ser un numero entero");
            return;
        }
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        try {
            if (clienteDAO.existeCedula(cedula, clienteS.getId())) {
                mostrarAlerta("Ya existe otro cliente con esa cedula");
                return;
            }
            Cliente c = new Cliente(clienteS.getId(), nombre, cedula, edad, telefono, email, direccion);
            clienteDAO.actualizar(c);
            cargarTabla();
            btnLimpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta("Error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    public void btnEliminar() {
        Cliente c = tblClientes.getSelectionModel().getSelectedItem();
        if (c == null) {
            mostrarAlerta("Seleccione un cliente de la tabla");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar al cliente: " + c.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                if (clienteDAO.tieneVentas(c.getId())) {
                    mostrarAlerta("Este cliente tiene ventas registradas, no se puede eliminar");
                    return;
                }
                clienteDAO.eliminar(c.getId());
                cargarTabla();
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el cliente: " + e.getMessage());
            }
        }
    }

    @FXML
    public void btnBuscar(){
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tblClientes.setItems(todosClientes);
            return;
        }
        ObservableList<Cliente> filtrados = FXCollections.observableArrayList();
        for (Cliente c : todosClientes) {
            if (c.getNombre().toLowerCase().contains(texto) || c.getCedula().contains(texto)) {
                filtrados.add(c);
            }
        }
        tblClientes.setItems(filtrados);
    }
    public void btnLimpiar() {
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
        txtEdad.clear();
        txtBuscar.clear();
        tblClientes.setItems(todosClientes);
    }
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.show();
    }
}