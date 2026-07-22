package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO implements ICRUD<Cliente> {
    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    @Override
    public void ingresar(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, cedula, edad, telefono, email, direccion) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, c.getNombre());
            pr.setString(2, c.getCedula());
            pr.setInt(3, c.getEdad());
            pr.setString(4, c.getTelefono());
            pr.setString(5, c.getEmail());
            pr.setString(6, c.getDireccion());
            pr.executeUpdate();
            mostrar();
        }
    }

    @Override
    public void mostrar() throws SQLException {
        clientes.clear();
        String sql = "SELECT * FROM clientes";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String cedula = rs.getString("cedula");
                int edad = rs.getInt("edad");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String direccion = rs.getString("direccion");
                Cliente c = new Cliente(id, nombre, cedula, edad, telefono, email, direccion);
                clientes.add(c);
            }
        }
    }

    @Override
    public void actualizar(Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET nombre=?, cedula=?, edad=?, telefono=?, email=?, direccion=? WHERE id_cliente=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, c.getNombre());
            pr.setString(2, c.getCedula());
            pr.setInt(3, c.getEdad());
            pr.setString(4, c.getTelefono());
            pr.setString(5, c.getEmail());
            pr.setString(6, c.getDireccion());
            pr.setInt(7, c.getId());
            pr.executeUpdate();
            mostrar();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            pr.executeUpdate();
            mostrar();
        }
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public boolean existeCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, cedula);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean existeCedula(String cedula, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ? AND id_cliente != ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, cedula);
            pr.setInt(2, idExcluir);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean tieneVentas(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas WHERE id_cliente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}
