package com.DonLiquox.licoreria.model;

public class Usuario extends Persona {
    private String correo;
    private String clave;
    private String rol;

    public Usuario(int id, String nombre, String cedula, int edad, String correo, String clave, String rol) {
        super(id, nombre, cedula, edad);
        setCorreo(correo);
        setClave(clave);
        setRol(rol);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Ingrese un correo valido");
        }
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        if (clave == null || clave.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        if (!rol.equals("Administrador") && !rol.equals("Cajero")){
            throw new IllegalArgumentException("El rol no se encunetra registrado");
        }
        this.rol = rol;
    }
}