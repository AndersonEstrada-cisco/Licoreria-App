package com.DonLiquox.licoreria.dao;

import com.DonLiquox.licoreria.db.Conexion;
import com.DonLiquox.licoreria.model.Venta;
import com.DonLiquox.licoreria.model.DetalleVenta;
import java.sql.*;

public class VentasDAO {
    public void registrarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (id_usuario, id_cliente, total, fecha) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        Connection con = Conexion.getConneccion();
        try {
            con.setAutoCommit(false); // Transacción para seguridad

            // Insertar Venta
            PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, venta.getUsuario().getId()); // Asegúrate que Usuario tenga getId()
            psVenta.setInt(2, venta.getIdCliente());
            psVenta.setDouble(3, venta.getTotal());
            psVenta.setTimestamp(4, Timestamp.valueOf(venta.getFecha()));
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            int idVentaNueva = rs.next() ? rs.getInt(1) : 0;

            for (DetalleVenta d : venta.getDetalles()) {
                PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, idVentaNueva);
                psDetalle.setInt(2, d.getProducto().getIdProducto());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getSubtotal());
                psDetalle.executeUpdate();

                PreparedStatement psStock = con.prepareStatement(sqlStock);
                psStock.setInt(1, d.getCantidad());
                psStock.setInt(2, d.getProducto().getIdProducto());
                psStock.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
}