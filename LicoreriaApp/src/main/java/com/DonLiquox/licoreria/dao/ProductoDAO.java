package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO implements ICRUD<Producto>{
    private ObservableList<Producto> licores = FXCollections.observableArrayList();

    @Override
    public void ingresar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (nombre,categoria,precio,stock) VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, p.getNombre());
            pr.setString(2, p.getCategoria());
            pr.setDouble(3, p.getPrecio());
            pr.setInt(4, p.getStock());
            pr.executeUpdate();
        }
        mostrar();
    }

    @Override
    public void mostrar() throws SQLException {
        licores.clear();
        String sql = "SELECT * FROM productos";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()){
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                Producto licor = new Producto(id,nombre,categoria,precio,stock);
                licores.add(licor);
            }
        }catch (Exception e){
            throw new SQLException("Error al registrar los datos");
        }
    }

    @Override
    public void actualizar(Producto p) throws SQLException {
        String sql ="UPDATE productos SET nombre = ?,categoria = ?, precio = ?, stock = ? WHERE id_producto = ?";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1,p.getNombre());
            pr.setString(2,p.getCategoria());
            pr.setDouble(3,p.getPrecio());
            pr.setInt(4,p.getStock());
            pr.setInt(5,p.getIdProducto());
            pr.executeUpdate();
            mostrar();
        }catch (Exception e){
            throw new SQLException("Error al actualizar en la base de datos");
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql ="DELETE FROM productos WHERE  id_producto= ?";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1,id);
            pr.executeUpdate();
            mostrar();
        }catch (Exception e){
            throw new SQLException("Error al eliminaren la base de datos");
        }
    }

    public ObservableList<Producto> getLicores() {
        return licores;
    }
}
