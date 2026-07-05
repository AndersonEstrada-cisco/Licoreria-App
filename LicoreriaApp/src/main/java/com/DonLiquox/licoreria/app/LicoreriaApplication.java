package com.DonLiquox.licoreria.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LicoreriaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LicoreriaApplication.class.getResource("/com/DonLiquox/licoreria/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        stage.setTitle("DonLiquox");
        stage.setScene(scene);
        stage.show();
    }
}
