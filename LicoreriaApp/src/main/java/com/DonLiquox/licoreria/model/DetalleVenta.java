package com.DonLiquox.licoreria.model;

public class DetalleVenta {
    private int idDetalle;
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(int idDetalle, Producto producto, int cantidad) {
        this.idDetalle = idDetalle;
        setProducto(producto);
        setCantidad(cantidad);
        this.subtotal = producto.getPrecio() * cantidad;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.cantidad = cantidad;
    }

    public int getIdDetalle() { return idDetalle; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}

