package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.ReporteProducto;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {
    public List<ReporteProducto> obtenerReportePorFecha(LocalDate fecha) throws SQLException {
        List<ReporteProducto> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, SUM(dv.cantidad) as total_cant, SUM(dv.subtotal) as total_ingreso " +
                "FROM detalle_venta dv " +
                "JOIN productos p ON dv.id_producto = p.id_producto " +
                "JOIN ventas v ON dv.id_venta = v.id_venta " +
                "WHERE DATE(v.fecha) = ? " +
                "GROUP BY p.nombre ORDER BY total_ingreso DESC";
        try (Connection con = Conexion.getConneccion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ReporteProducto(
                            rs.getString("nombre"),
                            rs.getInt("total_cant"),
                            rs.getDouble("total_ingreso")
                    ));
                }
            }
        }
        return lista;
    }
}