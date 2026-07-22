package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.UsuarioDAO;
import com.DonLiquox.licoreria.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class UsuarioController {
    @FXML private TextField txtNombre, txtCedula, txtEdad, txtCorreo;
    @FXML private TextField txtBuscar;
    @FXML private PasswordField txtClave;
    @FXML private ComboBox<String> cmbRol;
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colNombre, colRol, colCedula, colCorreo, colClave;
    @FXML private TableColumn<Usuario, Integer> colEdad;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ObservableList<Usuario> todosUsuarios = FXCollections.observableArrayList();

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
            todosUsuarios.clear();
            todosUsuarios.addAll(usuarioDAO.getUsuarios());
            tblUsuarios.setItems(todosUsuarios);
        } catch (SQLException | NumberFormatException e) { mostrarAlerta("Error al cargar: " + e.getMessage()); }
    }

    @FXML
    public void btnGuardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del usuario");
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la cedula del usuario");
            return;
        }
        if (txtEdad.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la edad del usuario");
            return;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el correo del usuario");
            return;
        }
        if (txtClave.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la contraseña del usuario");
            return;
        }
        if (cmbRol.getValue() == null) {
            mostrarAlerta("Seleccione un rol");
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
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();
        String rol = cmbRol.getValue();
        try {
            if (usuarioDAO.existeCedula(cedula)) {
                mostrarAlerta("Ya existe un usuario con esa cedula");
                return;
            }
            if (usuarioDAO.existeCorreo(correo)) {
                mostrarAlerta("Ya existe un usuario con ese correo");
                return;
            }
            Usuario u = new Usuario(0, nombre, cedula, edad, correo, clave, rol);
            usuarioDAO.ingresar(u);
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
        Usuario userS = tblUsuarios.getSelectionModel().getSelectedItem();
        if (userS == null) {
            mostrarAlerta("Seleccione un usuario de la tabla");
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del usuario");
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la cedula del usuario");
            return;
        }
        if (txtEdad.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la edad del usuario");
            return;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el correo del usuario");
            return;
        }
        if (txtClave.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la contraseña del usuario");
            return;
        }
        if (cmbRol.getValue() == null) {
            mostrarAlerta("Seleccione un rol");
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
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();
        String rol = cmbRol.getValue();
        try {
            if (usuarioDAO.existeCedula(cedula, userS.getId())) {
                mostrarAlerta("Ya existe otro usuario con esa cedula");
                return;
            }
            if (usuarioDAO.existeCorreo(correo, userS.getId())) {
                mostrarAlerta("Ya existe otro usuario con ese correo");
                return;
            }
            Usuario u = new Usuario(userS.getId(), nombre, cedula, edad, correo, clave, rol);
            usuarioDAO.actualizar(u);
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
        Usuario u = tblUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) {
            mostrarAlerta("Seleccione un usuario de la tabla");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar al usuario: " + u.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                if (usuarioDAO.tieneVentas(u.getId())) {
                    mostrarAlerta("Este usuario tiene ventas registradas, no se puede eliminar");
                    return;
                }
                usuarioDAO.eliminar(u.getId());
                cargarTabla();
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el usuario: " + e.getMessage());
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
        txtBuscar.clear();
        tblUsuarios.setItems(todosUsuarios);
    }
    @FXML
    public void btnBuscar(){
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tblUsuarios.setItems(todosUsuarios);
            return;
        }
        ObservableList<Usuario> filtrados = FXCollections.observableArrayList();
        for (Usuario u : todosUsuarios) {
            if (u.getNombre().toLowerCase().contains(texto) || u.getCorreo().toLowerCase().contains(texto)) {
                filtrados.add(u);
            }
        }
        tblUsuarios.setItems(filtrados);
    }
    public void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}