package com.DonLiquox.licoreria.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
public class DashboardController {
    @FXML
    private StackPane contentArea; //Falta definir en que cual se trabajara de la pantalla se cargara
    public void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent vista = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
