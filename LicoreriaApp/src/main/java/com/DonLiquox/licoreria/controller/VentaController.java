package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.VentasDAO;
import com.DonLiquox.licoreria.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class VentaController {

    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<Producto> cmbProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView<DetalleVenta> tblCarrito;
    @FXML private TableColumn<DetalleVenta, String> colProd, colCant, colPrecio, colSubtotal;
    @FXML private Label lblTotal;

    private VentasDAO ventasDAO = new VentasDAO();
    private ObservableList<DetalleVenta> listaCarrito = FXCollections.observableArrayList();
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        colProd.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getProducto().getNombre()));
        colCant.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getCantidad())));
        colPrecio.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.format("%.2f", d.getValue().getProducto().getPrecio())));
        colSubtotal.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.format("%.2f", d.getValue().getSubtotal())));
        tblCarrito.setItems(listaCarrito);
        configurarComboBox(cmbCliente, "Cliente");
        configurarComboBox(cmbProducto, "Producto");
    }

    @FXML
    public void btnAgregar() {
        Producto p = cmbProducto.getValue();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        if (p != null && cantidad > 0) {
            DetalleVenta nuevoDetalle = new DetalleVenta(0, p, cantidad);
            listaCarrito.add(nuevoDetalle);
            double total = listaCarrito.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
            lblTotal.setText("Total: $" + String.format("%.2f", total));
        }
    }

    @FXML
    public void btnComprar() {
        try {
            Venta nuevaVenta = new Venta(0, usuarioActual, cmbCliente.getValue());
            for (DetalleVenta d : listaCarrito) {
                nuevaVenta.agregarDetalle(d);
            }
            ventasDAO.registrarVenta(nuevaVenta);
            listaCarrito.clear();
            lblTotal.setText("Total: $0.00");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Venta realizada con éxito");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar: " + e.getMessage());
            alert.show();
        }
    }
    private <T> void configurarComboBox(ComboBox<T> combo, String tipo) {
        combo.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T objeto) {
                if (objeto == null){
                    return "Seleccione " + tipo;
                }
                if (objeto instanceof Cliente) {
                    return ((Cliente) objeto).getNombre();
                } else {
                    return ((Producto) objeto).getNombre();
                }
            }
            @Override
            public T fromString(String string) { return null; }
        });
    }
    public void setUsuarioActual(Usuario usuario) { this.usuarioActual = usuario; }
}