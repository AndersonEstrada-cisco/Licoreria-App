package com.DonLiquox.licoreria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int idVenta;
    private Usuario usuario;
    private Cliente cliente;
    private double total;
    private LocalDateTime fecha;
    private List<DetalleVenta> detalles;

    public Venta(int idVenta, Usuario usuario, Cliente cliente) {
        this.idVenta = idVenta;
        this.usuario = usuario;
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.detalles = new ArrayList<>();
        this.total = 0;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        total += detalle.getSubtotal();
    }


    public int getIdVenta() {
        return idVenta;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public double getTotal() {
        return total;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<DetalleVenta> getDetalles() { return detalles; }
}