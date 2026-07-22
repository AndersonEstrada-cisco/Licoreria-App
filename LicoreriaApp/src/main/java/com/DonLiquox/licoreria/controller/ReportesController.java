package com.DonLiquox.licoreria.controller;

import com.DonLiquox.licoreria.dao.ReporteDAO;
import com.DonLiquox.licoreria.model.ReporteProducto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ReportesController {
    @FXML private DatePicker fecha;
    @FXML private TableView<ReporteProducto> tblReporte;
    @FXML private TableColumn<ReporteProducto, String> colProducto;
    @FXML private TableColumn<ReporteProducto, Integer> colCantidad;
    @FXML private TableColumn<ReporteProducto, Double> colTotal;
    @FXML private PieChart graficoVentas;

    private ReporteDAO reporteDAO = new ReporteDAO();

    @FXML
    public void initialize() {
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    @FXML
    public void generarReporte() {
        if (fecha.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, seleccione una fecha.").show();
            return;
        }
        try {
            var lista = reporteDAO.obtenerReportePorFecha(fecha.getValue());
            tblReporte.setItems(FXCollections.observableArrayList(lista));
            graficoVentas.getData().clear();
            for (ReporteProducto r : lista) {
                graficoVentas.getData().add(new PieChart.Data(r.getNombre(), r.getCantidad()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al obtener reporte: " + e.getMessage()).show();
        }
    }
    @FXML
    public void exportarCSV() {
        if (tblReporte.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No hay datos para exportar").show();
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte");
        fileChooser.setInitialFileName("reporte.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File archivo = fileChooser.showSaveDialog(tblReporte.getScene().getWindow());
        if (archivo == null) {
            return;
        }
        try (PrintWriter writer = new PrintWriter(archivo)) {
            writer.println("Producto,Cantidad,Total");
            for (ReporteProducto r : tblReporte.getItems()) {
                writer.println(r.getNombre() + "," + r.getCantidad() + "," + r.getTotal());
            }
            new Alert(Alert.AlertType.INFORMATION, "Reporte exportado correctamente").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error al exportar el archivo").show();
        }
    }
}