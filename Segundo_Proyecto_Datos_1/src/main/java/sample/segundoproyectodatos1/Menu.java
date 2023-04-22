package sample.segundoproyectodatos1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {

    //Definir objetos de la interfaz

    //Labels
    private Label labelMain,
            labelCliente;

    //Botones
    Button botonCliente;

    //Caja de texto
    TextField usuarioCliente;


    PasswordField passwordCliente;

    //Caja de opciones
    ComboBox<String> opcionesDeUsuario;

    //Stage
    Stage stage = new Stage();


    /**
     * Empieza la aplicacion del menu
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane();  //StackPane principal
        StackPane loginPane = new StackPane();  //StackPane del login

        this.stage = stage;

        //mainPane--------------------------------------------
        //Labels
        labelMain = new Label();
        labelMain.setText("Seleccione el tipo de usuario con el cuál desea ingresar");
        labelMain.setTranslateX(0);
        labelMain.setTranslateY(-250);
        labelMain.setStyle("-fx-font-size: 20;");

        //loginPane--------------------------------------------
        //Label
        labelCliente = new Label();
        labelCliente.setText("Login");
        labelCliente.setTranslateX(0);
        labelCliente.setTranslateY(-100);
        labelCliente.setStyle("-fx-font-size: 15;");

        //Botones
        botonCliente = new Button();
        botonCliente.setText("Iniciar sesión");
        botonCliente.setStyle("-fx-font-size: 12;");
        botonCliente.setOnAction(e -> comprobarLoginClient());
        botonCliente.setTranslateY(100);

        //Cajas de texto
        usuarioCliente = new TextField();
        usuarioCliente.setPromptText("Usuario");
        usuarioCliente.setMaxWidth(120);
        usuarioCliente.setTranslateY(-30);

        passwordCliente = new PasswordField();
        passwordCliente.setPromptText("Contraseña");
        passwordCliente.setMaxWidth(120);
        passwordCliente.setTranslateY(10);

        //Caja de opciones
        opcionesDeUsuario = new ComboBox<>();
        opcionesDeUsuario.setPromptText("Tipo de usuario");
        opcionesDeUsuario.getItems().addAll(
                "Administrador",
                "Cliente"
        );
        opcionesDeUsuario.setTranslateY(50);
        opcionesDeUsuario.setMaxWidth(100);

        //Caracteristicas del StackPane del cliente
        loginPane.setStyle("-fx-border-color: #000");
        loginPane.setMaxWidth(220);
        loginPane.setMaxHeight(300);

        //Agregar elementos graficos al loginPane
        loginPane.getChildren().addAll(
                labelCliente,
                botonCliente,
                usuarioCliente,
                passwordCliente,
                opcionesDeUsuario
        );


        //Agregar elementos graficos al mainPane
        mainPane.getChildren().addAll(
                labelMain,
                loginPane
        );


        Scene menu = new Scene(mainPane,1200,700);
        stage.setTitle("MenuApp");
        stage.setScene(menu);




        stage.show();



    }

    public void comprobarLoginClient(){

        String usuario = usuarioCliente.getText();  //Texto en el nombre de usuario
        String password = passwordCliente.getText();  ////Texto en la contraseña
        String tiposUsuario = opcionesDeUsuario.getValue();

        String prueba = "user",contra = "1234";

        /*
        if (!usuario.equals(prueba)){  //Verifica que el usuario sea uno registrado
            return;
        }
        if (!password.equals(contra)){  //Verifica que la contraseña sea una registrada
            return;
        }*/

        //Ejecutar código después de validar la información del login



        if (tiposUsuario.equals("Administrador")){  //Si el usuario es de tipo administrador
            MasterApp masterApp = new MasterApp();  //Crear una ventana MasterApp
            masterApp.elementosGraficos();  //Mostrar la ventana MasterApp
            //stage.close();  //Cerrar
        }else {  //Si el usuario es de tipo cliente
            ClientApp clientApp = new ClientApp();  //Crear una ventana ClientApp
            clientApp.elementosGraficos();  //Mostrar la ventana ClientApp
            //stage.close();  //Cerrar
        }




    }








    public static void main(String[] args) {
        launch();
    }
}