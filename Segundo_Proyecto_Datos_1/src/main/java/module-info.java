module sample.segundoproyectodatos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    requires java.scripting;

    opens MainClass to javafx.fxml;
    exports MainClass;
    exports Servidor;
    opens Servidor to javafx.fxml;
    exports Admin_y_Usuario;
    opens Admin_y_Usuario to javafx.fxml;
    exports Clases_auxiliares;
    opens Clases_auxiliares to javafx.fxml;
}