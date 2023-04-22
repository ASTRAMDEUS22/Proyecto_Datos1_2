package sample.segundoproyectodatos1;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientApp {

    //Definir objetos de la interfaz

    //Labels
    Label labelPrueba;


    //Stage
    Stage stage = new Stage();


    public void elementosGraficos() {

        StackPane mainPane = new StackPane();


        labelPrueba = new Label();
        labelPrueba.setText("LOGUE EXITOSO EN CLIENTAPP");


        mainPane.getChildren().addAll(labelPrueba);












        //Escena donde se mostrará los elementos
        Scene clientApp = new Scene(mainPane,1200,700);
        stage.setTitle("ClientApp");
        stage.setScene(clientApp);
        stage.show();


    }






}
