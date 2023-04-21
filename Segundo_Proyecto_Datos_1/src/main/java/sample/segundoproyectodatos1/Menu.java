package sample.segundoproyectodatos1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {

    //Definir objetos de la interfaz

    //Labels
    Label labelMain,
            labelOpcion,
            labelCliente,
            labelAdmin;

    //Botones
    Button botonAdministrador,
            botonCliente;

    //Caja de texto
    TextField usuarioAdmin,
            usuarioCliente,
            passwordAdmin,
            passWordCliente;




    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane();  //StackPane principal
        StackPane clientPane = new StackPane();  //StackPane del cliente
        StackPane adminPane = new StackPane();  //StackPane del admin

        //mainPane--------------------------------------------
        //Labels
        labelMain = new Label();
        labelMain.setText("Seleccione el tipo de usuario con el cuál desea ingresar");
        labelMain.setTranslateX(0);
        labelMain.setTranslateY(-250);
        labelMain.setStyle("-fx-font-size: 20;");

        //clientPane--------------------------------------------
        //Label
        labelCliente = new Label();
        labelCliente.setText("Cliente");
        labelCliente.setTranslateX(0);
        labelCliente.setTranslateY(-100);
        labelCliente.setStyle("-fx-font-size: 15;");

        //Botones
        botonCliente = new Button();
        botonCliente.setText("Seleccionar");
        botonCliente.setStyle("-fx-font-size: 15;");

        //Caracteristicas del StackPane del cliente
        clientPane.setStyle("-fx-border-color: #000");
        clientPane.setMaxWidth(220);
        clientPane.setMaxHeight(300);
        clientPane.setTranslateX(-400);

        //adminPane---------------------------------------------
        //Label
        labelAdmin = new Label();
        labelAdmin.setText("Administrador");
        labelAdmin.setTranslateX(0);
        labelAdmin.setTranslateY(-100);
        labelAdmin.setStyle("-fx-font-size: 15;");

        //Botones
        //Botones
        botonAdministrador = new Button();
        botonAdministrador.setText("Seleccionar");
        botonAdministrador.setStyle("-fx-font-size: 15;");

        //Caracteristicas del StackPane del administrador
        adminPane.setStyle("-fx-border-color: #000");
        adminPane.setMaxWidth(220);
        adminPane.setMaxHeight(300);
        adminPane.setTranslateX(400);



        //Agregar elementos graficos al clientPane
        clientPane.getChildren().addAll(
                labelCliente,
                botonCliente
        );

        //Agregar elementos graficos al clientPane
        adminPane.getChildren().addAll(
                labelAdmin,
                botonAdministrador
        );

        //Agregar elementos graficos al mainPane
        mainPane.getChildren().addAll(
                labelMain,
                clientPane,
                adminPane
        );


        Scene menu = new Scene(mainPane,1200,700);
        stage.setTitle("MasterApp");
        stage.setScene(menu);




        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}