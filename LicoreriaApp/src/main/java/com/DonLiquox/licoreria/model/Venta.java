package com.DonLiquox.licoreria.model;

import java.time.LocalDateTime;

public class Venta {
    private int idVenta;
    private Usuario usuario;
    private Cliente cliente;
    private double total;
    private LocalDateTime fecha;

    public Venta(int idVenta, Usuario usuario, Cliente cliente, double total) {
        this.idVenta = idVenta;
        this.usuario = usuario;
        this.cliente = cliente;
        setTotal(total);
        this.fecha = LocalDateTime.now();
    }


    public void setTotal(double total) {
        if (total <= 0) {
            throw new IllegalArgumentException("El total de venta debe ser positivo");
        }
        this.total = total;
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
}