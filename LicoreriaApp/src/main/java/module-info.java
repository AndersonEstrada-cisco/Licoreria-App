module com.DonLiquox.licoreria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.DonLiquox.licoreria.app to javafx.fxml;
    opens com.DonLiquox.licoreria.controller to javafx.fxml;
    exports com.DonLiquox.licoreria.app;
}