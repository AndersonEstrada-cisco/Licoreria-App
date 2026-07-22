package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class UsuarioDAO implements ICRUD<Usuario> {
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @Override
    public void ingresar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, cedula, edad, correo, clave, rol) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, u.getNombre());
            pr.setString(2, u.getCedula());
            pr.setInt(3, u.getEdad());
            pr.setString(4, u.getCorreo());
            pr.setString(5, u.getClave());
            pr.setString(6, u.getRol());
            pr.executeUpdate();
        }
        mostrar();
    }

    @Override
    public void mostrar() throws SQLException {
        usuarios.clear();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql); ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("cedula"),
                        rs.getInt("edad"), rs.getString("correo"), rs.getString("clave"), rs.getString("rol")
                ));
            }
        }
    }

    @Override
    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET nombre=?, cedula=?, edad=?, correo=?, clave=?, rol=? WHERE id_usuario=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, u.getNombre()); pr.setString(2, u.getCedula());
            pr.setInt(3, u.getEdad()); pr.setString(4, u.getCorreo());
            pr.setString(5, u.getClave()); pr.setString(6, u.getRol());
            pr.setInt(7, u.getId());
            pr.executeUpdate();
            mostrar();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            pr.executeUpdate();
            mostrar();
        }
    }

    public ObservableList<Usuario> getUsuarios() { return usuarios; }

    public boolean existeCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cedula = ?";
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
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cedula = ? AND id_usuario != ?";
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

    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, correo);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean existeCorreo(String correo, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ? AND id_usuario != ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, correo);
            pr.setInt(2, idExcluir);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean tieneVentas(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas WHERE id_usuario = ?";
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