package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.model.Producto;

import java.sql.SQLException;

public interface ICRUD<T> {
    void ingresar(T p) throws SQLException;
    void mostrar() throws SQLException;
    void actualizar(T p) throws SQLException;
    void eliminar(int id) throws SQLException;
}
