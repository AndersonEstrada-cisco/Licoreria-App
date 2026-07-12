package com.DonLiquox.licoreria.model;

public class DetalleVenta {
    private final int idDetalle;
    private final Producto producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(int idDetalle, Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("Producto requerido");
        this.idDetalle = idDetalle;
        this.producto = producto;
        setCantidad(cantidad);
    }

    public final void setCantidad(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    private void actualizarSubtotal() {
        this.subtotal = this.producto.getPrecio() * this.cantidad;
    }

    public int getIdDetalle() { return idDetalle; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}