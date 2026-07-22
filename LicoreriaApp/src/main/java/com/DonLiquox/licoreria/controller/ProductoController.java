package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ProductoDAO;
import com.DonLiquox.licoreria.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ProductoController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Producto> tableproductos;
    @FXML private TableColumn<Producto,String> colCategoria,colNombre;
    @FXML private TableColumn<Producto,Integer> colStock,colId;
    @FXML private TableColumn<Producto,Double> colPrecio;
    private ProductoDAO pro_db = new ProductoDAO();
    private ObservableList<Producto> todosProductos = FXCollections.observableArrayList();

    public void initialize(){
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdProducto()).asObject());
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategoria()));
        colStock.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStock()).asObject());
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrecio()).asObject());
        cargarTabla();
        tableproductos.setOnMouseClicked(event -> {
            Producto p = tableproductos.getSelectionModel().getSelectedItem();
            if (p != null) {
                txtNombre.setText(p.getNombre());
                txtCategoria.setText(p.getCategoria());
                txtStock.setText(String.valueOf(p.getStock()));
                txtPrecio.setText(String.valueOf(p.getPrecio()));
            }
        });
    }
    private void cargarTabla() {
        try {
            pro_db.mostrar();
            todosProductos.clear();
            todosProductos.addAll(pro_db.getLicores());
            tableproductos.setItems(todosProductos);
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar la tabla: " + e.getMessage());
        }
    }
    public void btnGuardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del producto");
            return;
        }
        if (txtCategoria.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la categoria del producto");
            return;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el precio del producto");
            return;
        }
        if (txtStock.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el stock del producto");
            return;
        }
        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El precio debe ser un número valido");
            return;
        }
        try {
            stock = Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El stock debe ser un número entero");
            return;
        }
        try {
            Producto p = new Producto(0, nombre, categoria, precio, stock);
            pro_db.ingresar(p);
            cargarTabla();
            btnLimpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta("Error al guardar: " + e.getMessage());
        }
    }
    public void btnActualizar() {
        Producto p = tableproductos.getSelectionModel().getSelectedItem();
        if (p == null) {
            mostrarAlerta("Seleccione un producto de la tabla");
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el nombre del producto");
            return;
        }
        if (txtCategoria.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese la categoria del producto");
            return;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el precio del producto");
            return;
        }
        if (txtStock.getText().trim().isEmpty()) {
            mostrarAlerta("Ingrese el stock del producto");
            return;
        }
        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El precio debe ser un numero valido");
            return;
        }
        try {
            stock = Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El stock debe ser un numero entero");
            return;
        }
        try {
            Producto actu = new Producto(p.getIdProducto(), nombre, categoria, precio, stock);
            pro_db.actualizar(actu);
            cargarTabla();
            btnLimpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta("Error al actualizar: " + e.getMessage());
        }
    }
    public void btnEliminar(){
        Producto p = tableproductos.getSelectionModel().getSelectedItem();
        if (p == null) {
            mostrarAlerta("Seleccione un producto de la tabla");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar el producto: " + p.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                if (pro_db.tieneVentas(p.getIdProducto())) {
                    mostrarAlerta("Este producto tiene ventas registradas, no se puede eliminar");
                    return;
                }
                pro_db.eliminar(p.getIdProducto());
                cargarTabla();
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el producto: " + e.getMessage());
            }
        }
    }
    @FXML
    public void btnBuscar(){
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            tableproductos.setItems(todosProductos);
            return;
        }
        ObservableList<Producto> filtrados = FXCollections.observableArrayList();
        for (Producto p : todosProductos) {
            if (p.getNombre().toLowerCase().contains(texto) || p.getCategoria().toLowerCase().contains(texto)) {
                filtrados.add(p);
            }
        }
        tableproductos.setItems(filtrados);
    }
    public void btnLimpiar(){
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtBuscar.clear();
        tableproductos.setItems(todosProductos);
    }
    public void mostrarAlerta(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.show();
    }
}
