package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ProductoDAO;
import com.DonLiquox.licoreria.model.Producto;
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
    @FXML private TableView<Producto> tableproductos;
    @FXML private TableColumn<Producto,String> colCategoria,colNombre;
    @FXML private TableColumn<Producto,Integer> colStock,colId;
    @FXML private TableColumn<Producto,Double> colPrecio;
    private ProductoDAO pro_db = new ProductoDAO();

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
            tableproductos.setItems(pro_db.getLicores());
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar la tabla: " + e.getMessage());
        }
    }
    public void btnGuardar() throws SQLException {
        try {
            String nombre = txtNombre.getText();
            String categoria = txtCategoria.getText();
            int stock = Integer.parseInt(txtStock.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            Producto p = new Producto(0, nombre, categoria, precio, stock);
            pro_db.ingresar(p);
            cargarTabla();
        }catch (NumberFormatException e){
            mostrarAlerta("Los datos deben ser numericos");
        }
    }
    public void btnActualizar() throws SQLException {
        Producto p = tableproductos.getSelectionModel().getSelectedItem();
        if (p == null) {
            mostrarAlerta("Seleccione un producto de la tabla para actualizar.");
            return;
        }
        try {
            Producto actu = new Producto(p.getIdProducto(), txtNombre.getText(), txtCategoria.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
            pro_db.actualizar(actu);
            cargarTabla();
            btnLimpiar();
        }catch (IllegalArgumentException e){
            mostrarAlerta("No se pudo actualizar el producto");
        }
    }
    public void btnEliminar(){
        Producto p = tableproductos.getSelectionModel().getSelectedItem();
        if (p == null) {
            mostrarAlerta("Seleccione un producto de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar el producto: " + p.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            try {
                pro_db.eliminar(p.getIdProducto());
                btnLimpiar();
            } catch (SQLException e) {
                mostrarAlerta("No se pudo eliminar el producto: " + e.getMessage());
            }
        }
    }
    @FXML
    public void btnBuscar(){

    }
    public void btnLimpiar(){
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
    }
    public void mostrarAlerta(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error: " + mensaje);
        alert.show();
    }
}
