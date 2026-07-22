package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ClienteDAO;
import com.DonLiquox.licoreria.dao.ProductoDAO;
import com.DonLiquox.licoreria.dao.VentasDAO;
import com.DonLiquox.licoreria.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.SQLException;

public class VentaController {

    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<Producto> cmbProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView<DetalleVenta> tblCarrito;
    @FXML private TableColumn<DetalleVenta, String> colProd, colCant, colPrecio, colSubtotal;
    @FXML private Label lblTotal;

    private VentasDAO ventasDAO = new VentasDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
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
        cargarClientes();
        cargarProductos();
    }

    private void cargarClientes() {
        try {
            clienteDAO.mostrar();
            cmbCliente.setItems(clienteDAO.getClientes());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar clientes: " + e.getMessage());
            alert.show();
        }
    }

    private void cargarProductos() {
        try {
            productoDAO.mostrar();
            cmbProducto.setItems(productoDAO.getLicores());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar productos: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void btnAgregar() {
        if (txtCantidad.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ingrese la cantidad");
            alert.show();
            return;
        }
        try {
            Producto p = cmbProducto.getValue();
            if (p == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un producto");
                alert.show();
                return;
            }
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "La cantidad debe ser mayor a 0");
                alert.show();
                return;
            }
            if (cantidad > p.getStock()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No hay suficiente stock, solo hay " + p.getStock() + " unidades");
                alert.show();
                return;
            }
            DetalleVenta nuevoDetalle = new DetalleVenta(0, p, cantidad);
            listaCarrito.add(nuevoDetalle);
            double total = 0;
            for (DetalleVenta d : listaCarrito) {
                total += d.getSubtotal();
            }
            lblTotal.setText("Total: $" + String.format("%.2f", total));
            txtCantidad.clear();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ingrese una cantidad numerica");
            alert.show();
        }
    }

    @FXML
    public void btnComprar() {
        try {
            if (listaCarrito.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "El carrito esta vacio");
                alert.show();
                return;
            }
            if (usuarioActual == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha identificado el usuario");
                alert.show();
                return;
            }
            Cliente clienteSeleccionado = cmbCliente.getValue();
            if (clienteSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un cliente");
                alert.show();
                return;
            }
            Venta nuevaVenta = new Venta(0, usuarioActual, clienteSeleccionado);
            for (DetalleVenta d : listaCarrito) {
                nuevaVenta.agregarDetalle(d);
            }
            ventasDAO.registrarVenta(nuevaVenta);
            listaCarrito.clear();
            lblTotal.setText("Total: $0.00");
            cargarProductos();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Venta realizada con exito");
            alert.show();
        } catch (SQLException e) {
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