module sample.segundoproyectodatos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens sample.segundoproyectodatos1 to javafx.fxml;
    exports sample.segundoproyectodatos1;
}