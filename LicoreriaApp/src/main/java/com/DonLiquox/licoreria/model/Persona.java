package com.DonLiquox.licoreria.model;

public abstract class Persona {
    private int id;
    private String nombre;
    private String cedula;
    private int edad;

    public Persona(int id, String nombre, String cedula, int edad) {
        this.id = id;
        setNombre(nombre);
        setCedula(cedula);
        setEdad(edad);
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if(edad < 18){
            throw new IllegalArgumentException("Compra para mayores de 18 años");
        }
        this.edad = edad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        validar(cedula,"cedula");
        if (!cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cédula solo debe contener números.");
        }
        if(cedula.length()!=10){
            throw new IllegalArgumentException("La cedula debe tener 10 numeros");
        }
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validar(nombre,"nombre");
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void validar(String valor, String campo){
        if(valor.trim().isEmpty()){
            throw new IllegalArgumentException("No puede dejar el campo " + campo + "vacio");
        }
    }
}
