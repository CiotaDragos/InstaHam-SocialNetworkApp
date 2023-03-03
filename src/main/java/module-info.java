module com.example.social_network_gradle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.social_network_gradle to javafx.fxml;
    exports com.example.social_network_gradle;
}