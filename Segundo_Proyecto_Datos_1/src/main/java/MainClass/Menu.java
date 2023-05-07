package MainClass;

import Admin_y_Usuario.ClientApp;
import Admin_y_Usuario.MasterApp;
import Clases_auxiliares.Message;
import Servidor.ServerApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.crypto.MacSpi;
import java.io.*;
import java.net.Socket;

public class Menu extends Application implements Runnable {



    Socket socket;
    //Puente de salida
    ObjectOutputStream enviar_objeto_server;


    public Menu() throws IOException {
        ServerApp servidorSocket = new ServerApp();  //Se arranca el servidor

        this.socket = new Socket("192.168.1.246",1234);


        this.enviar_objeto_server = new ObjectOutputStream(socket.getOutputStream());

        Thread hilo = new Thread(this);
        hilo.start();


    }

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


        Scene menu = new Scene(mainPane,500,700);
        stage.setTitle("MenuApp");
        stage.setScene(menu);




        stage.show();



    }

    public void ejecutarVentanaCliente() throws IOException {

        Platform.runLater(() -> {
            ClientApp clientApp = new ClientApp();
            try {
                clientApp.elementosGraficos();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void ejecutarVentanaAdmin() throws IOException {

        Platform.runLater(() -> {
            MasterApp masterApp = new MasterApp();
            try {
                masterApp.elementosGraficos();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void comprobarLoginClient() {

        String usuario = usuarioCliente.getText();  //Texto en el nombre de usuario
        String password = passwordCliente.getText();  ////Texto en la contraseña*/
        String tiposUsuario = opcionesDeUsuario.getValue();

        if (tiposUsuario.equals("Administrador")) {  //Si el usuario es de tipo administrador

            //Mensaje para el server
            Message message = new Message("LoginAdmin",usuario,password);


            try {

                //Se envía el mensaje al servidor
                enviar_objeto_server.writeObject(message);
                System.out.println("Se manda el mensaje");


            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }




        }else {  //Si el usuario es de tipo cliente
            //Mensaje para el server
            Message message = new Message("LoginClient",usuario,password);

            try {

                //Se envía el mensaje al servidor
                enviar_objeto_server.writeObject(message);
                System.out.println("Se manda el mensaje");

            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void run() {
        try {


            //Se mantiene a la espera de una respuesta del servidor
            while (true){

                //Recibe un mensaje de tipo Object por parte del server
                ObjectInputStream respuestaServer = new ObjectInputStream(socket.getInputStream());

                //Se guarda el contenido del mensaje en un Object
                Message respuesta = (Message) respuestaServer.readObject();


                if (respuesta.getNombreMetodo().equals("Login_Admin_Exitoso")){
                    ejecutarVentanaAdmin();
                    //respuestaServer.close();
                }

                else if(respuesta.getNombreMetodo().equals("Login_Client_Exitoso")) {  //Si login fue correcto, ejecuta la ventana correspondiente
                    ejecutarVentanaCliente();
                    //respuestaServer.close();
                }

                else {
                    System.out.println("El mensaje del server es: " + respuesta);
                }

            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}