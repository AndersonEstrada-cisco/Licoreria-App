package com.DonLiquox.licoreria.model;

public class Cliente extends Persona {
    private String telefono;
    private String email;
    private String direccion;

    public Cliente(int id, String nombre, String cedula, int edad,String telefono, String email, String direccion) {
        super(id, nombre, cedula, edad);
        setTelefono(telefono);
        setEmail(email);
        setDireccion(direccion);
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe tener 10 numeros");
        }
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Ingrese un correo válido");
        }
        this.email = email;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public String getEmail() {
        return email;
    }
    public String getDireccion() {
        return direccion;
    }
}