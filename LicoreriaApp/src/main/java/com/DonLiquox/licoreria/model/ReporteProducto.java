package com.DonLiquox.licoreria.model;

public class ReporteProducto {
    private String nombre;
    private int cantidad;
    private double total;

    public ReporteProducto(String nombre, int cantidad, double total) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.total = total;
    }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
}