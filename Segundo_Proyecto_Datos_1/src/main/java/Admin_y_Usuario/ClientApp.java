package Admin_y_Usuario;

import Clases_auxiliares.AvlNodo;
import Clases_auxiliares.ListaEnlazadaAVL;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Clases_auxiliares.Message;
import Clases_auxiliares.Platillo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Esta clase representa la aplicación cliente que se comunica con un servidor.
 * Proporciona una interfaz gráfica para que el usuario inicie sesión y acceda a un menú de platillos.
 */

public class ClientApp extends Application implements Runnable{

    //Stage
    Stage stage;
    /**
     * Constructor de la clase ClientApp.
     * Crea un hilo para ejecutar la aplicación.
     */
    public ClientApp(){
            //Crea un hilo
            Thread hilo = new Thread(this);
            hilo.start();
    }

    //Label
    Label labelMain,
    labelCliente;

    //Botones
    Button botonIniciarSesion;

    //Cajas de texto
    TextField usuario;
    PasswordField password;

    /**
     * Método de inicio de la aplicación JavaFX.
     * Crea y muestra la interfaz gráfica de la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */

    @Override
    public void start(Stage primaryStage){

        stage = primaryStage;
        StackPane mainPane = new StackPane();  //StackPane principal
        StackPane loginPane = new StackPane();  //StackPane del login

        //mainPane--------------------------------------------
        //Labels
        labelMain = new Label();
        labelMain.setText("Clientes");
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
        botonIniciarSesion = new Button();
        botonIniciarSesion.setText("Iniciar sesión");
        botonIniciarSesion.setStyle("-fx-font-size: 12;");
        botonIniciarSesion.setOnAction(e -> comprobarLogin(usuario.getText(), password.getText()));
        botonIniciarSesion.setTranslateY(100);

        //Cajas de texto
        usuario = new TextField();
        usuario.setPromptText("Usuario");
        usuario.setMaxWidth(120);
        usuario.setTranslateY(-30);

        password = new PasswordField();
        password.setPromptText("Contraseña");
        password.setMaxWidth(120);
        password.setTranslateY(10);

        //Caracteristicas del StackPane del cliente
        loginPane.setStyle("-fx-border-color: #000");
        loginPane.setMaxWidth(220);
        loginPane.setMaxHeight(300);

        //Agregar elementos graficos al loginPane
        loginPane.getChildren().addAll(
                labelCliente,
                botonIniciarSesion,
                usuario,
                password
        );

        //Agregar elementos graficos al mainPane
        mainPane.getChildren().addAll(
                labelMain,
                loginPane
        );


        Scene menu = new Scene(mainPane, 500, 700);
        primaryStage.setTitle("ClientApp");
        primaryStage.setScene(menu);

        primaryStage.show();




    }



    //Definir objetos de la interfaz

    //Labels
    Label labelMenu,
            labelInfoPlatillo,
            labelNombre,
            labelCantidadCalorias,
            labelPrecio,
            labelNombrePlatillo,
            labelCantidadCaloriasPlatillo,
            labelPrecioPlatillo,
            labelTiempo,
            labelTiempoPlatillo;


    //Pane
    Pane mainPane,
            paneMenu,
            panePedidos;


    //Buttons
    Button agregarPlatillo,
            enviarPedido;

    //Indice de la lista de platillos
    int indexPlatillos;

    //Lista de personas de ejemplo
    ObservableList<Platillo> listaPlatillos = FXCollections.observableArrayList();
    ListView<Platillo> listViewPlatillos = new ListView<>(listaPlatillos);

    ObservableList<Platillo> listaPedido = FXCollections.observableArrayList();
    ListView<Platillo> listViewPedido = new ListView<>(listaPedido);

    //Lista enlazada del pedido
    ListaEnlazadaAVL listaEnlazadaAVL = new ListaEnlazadaAVL();

    /**
     * Método para crear los elementos gráficos de la interfaz.
     *
     * @throws IOException Si ocurre un error al leer los datos de los platillos.
     */
    public void elementosGraficos() throws IOException {



        mainPane = new Pane();



        //Menu
        labelMenu = new Label();
        labelMenu.setText("Menu de platillos");
        labelMenu.setTranslateX(25);

        Label labelInfoPlatillo = new Label();
        labelInfoPlatillo.setText("Información del platillo");
        labelInfoPlatillo.setTranslateX(300);

        Label labelNombre = new Label();
        labelNombre.setText("Nombre:");
        labelNombre.setTranslateX(300);
        labelNombre.setTranslateY(120);

        Label labelNombrePlatillo = new Label();
        labelNombrePlatillo.setTranslateX(300);
        labelNombrePlatillo.setTranslateY(140);

        labelCantidadCalorias = new Label();
        labelCantidadCalorias.setText("Calorías:");
        labelCantidadCalorias.setTranslateX(300);
        labelCantidadCalorias.setTranslateY(180);

        labelCantidadCaloriasPlatillo = new Label();
        labelCantidadCaloriasPlatillo.setTranslateX(300);
        labelCantidadCaloriasPlatillo.setTranslateY(200);

        labelPrecio = new Label();
        labelPrecio.setText("Precio:");
        labelPrecio.setTranslateX(300);
        labelPrecio.setTranslateY(240);

        labelPrecioPlatillo = new Label();
        labelPrecioPlatillo.setTranslateX(300);
        labelPrecioPlatillo.setTranslateY(260);

        labelTiempo = new Label();
        labelTiempo.setText("Tiempo: ");
        labelTiempo.setTranslateX(300);
        labelTiempo.setTranslateY(300);

        labelTiempoPlatillo = new Label();
        labelTiempoPlatillo.setTranslateX(300);
        labelTiempoPlatillo.setTranslateY(320);

        agregarPlatillo = new Button();
        agregarPlatillo.setText("Agregar al pedido");
        agregarPlatillo.setOnAction(e -> agregarElementoPedido(listaPlatillos.get(indexPlatillos)));
        agregarPlatillo.setTranslateX(300);
        agregarPlatillo.setTranslateY(380);


        paneMenu = new Pane();
        paneMenu.setStyle("-fx-border-color: #000;-fx-border-width: 2");
        paneMenu.setMinWidth(500);  //Anchura mínima
        paneMenu.setMinHeight(425);  //Altura mínima
        paneMenu.setTranslateX(50);
        paneMenu.setTranslateY(50);


        //Detecta la selección de un elemento en la lista
        listViewPlatillos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Platillo>() {
            @Override
            public void changed(ObservableValue<? extends Platillo> observableValue, Platillo s, Platillo elementoSeleccionado) {
                //Hace una acción cuando se selecciona un elemento de la lista
                //Se cambian los textos de los Labels por los del Objeto de la lista seleccionado
                labelNombrePlatillo.setText(elementoSeleccionado.getNombrePlatillo());
                labelCantidadCaloriasPlatillo.setText(String.valueOf(elementoSeleccionado.getCalorias()));
                labelPrecioPlatillo.setText("₡ " + elementoSeleccionado.getPrecio());
                labelTiempoPlatillo.setText("s: " + elementoSeleccionado.getTiempo());
                indexPlatillos = listViewPlatillos.getSelectionModel().getSelectedIndex();
            }
        });

        listViewPlatillos.setMaxHeight(375);
        listViewPlatillos.setMinHeight(350);
        listViewPlatillos.setTranslateX(25);
        listViewPlatillos.setTranslateY(25);


        //Se agregan estos elementos al paneMenu
        paneMenu.getChildren().addAll(
                labelMenu,
                labelInfoPlatillo,
                labelNombre,
                labelCantidadCalorias,
                labelPrecio,
                labelNombrePlatillo,
                labelPrecioPlatillo,
                labelCantidadCaloriasPlatillo,
                listViewPlatillos,
                agregarPlatillo,
                labelTiempo,
                labelTiempoPlatillo
        );

//--------------------------PEDIDOS-----------------------------

        panePedidos = new Pane();
        panePedidos.setStyle("-fx-border-color: #000;-fx-border-width: 2");
        panePedidos.setMinWidth(300);  //Anchura mínima
        panePedidos.setMinHeight(500);  //Altura mínima
        panePedidos.setTranslateX(625);
        panePedidos.setTranslateY(50);

        //Detecta la selección de un elemento en la lista
        listViewPedido.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Platillo>() {
            @Override
            public void changed(ObservableValue<? extends Platillo> observableValue, Platillo s, Platillo elementoSeleccionado) {
                //Hace una acción cuando se selecciona un elemento de la lista
                //Se cambian los textos de los Labels por los del Objeto de la lista seleccionado
                /*labelNombrePlatillo.setText(elementoSeleccionado.getNombrePlatillo());
                labelCantidadCaloriasPlatillo.setText(String.valueOf(elementoSeleccionado.getCalorias()));
                labelPrecioPlatillo.setText("₡ " + elementoSeleccionado.getPrecio());*/
            }
        });

        listViewPedido.setMaxHeight(375);
        listViewPedido.setMinHeight(350);
        listViewPedido.setTranslateX(25);
        listViewPedido.setTranslateY(25);

        enviarPedido = new Button();
        enviarPedido.setText("Enviar pedido");
        enviarPedido.setOnAction(e -> enviarPedidoServer());
        enviarPedido.setTranslateX(150);
        enviarPedido.setTranslateY(465);



        panePedidos.getChildren().addAll(
                listViewPedido,
                enviarPedido
        );


        //Agrega al Pane principal los demás contenedores
        mainPane.getChildren().addAll(
                paneMenu,
                panePedidos);


        //Escena donde se mostrará los elementos
        Scene clientApp = new Scene(mainPane,1200,700);
        stage.setTitle("ClientApp");
        stage.setScene(clientApp);
        stage.show();


    }
    /**
     * Método para enviar un mensaje al servidor.
     *
     * @param mensaje El mensaje a enviar.
     */

    private void enviarMensajeServidor(Message mensaje){
        try {
            Socket socket = new Socket("localhost", 1234);
            ObjectOutputStream enviarObjeto = new ObjectOutputStream(socket.getOutputStream());

            enviarObjeto.writeObject(mensaje);

            System.out.println("Se manda mensaje al Servidor");

            socket.close();
            enviarObjeto.close();

        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }

    private void enviarPedidoServer(){

        Message message = new Message("agregarPedidoCola");
        message.setListaEnlazadaAVL(listaEnlazadaAVL);

        enviarMensajeServidor(message);

        listaEnlazadaAVL = new ListaEnlazadaAVL();

        listaPedido.clear();

    }

    /**
     * Método para comprobar el inicio de sesión del usuario.
     *
     * @param user   El nombre de usuario.
     * @param contra La contraseña.
     */

    private void comprobarLogin(String user,String contra){

        Message message = new Message("LoginClient",user,contra);
        enviarMensajeServidor(message);

    }

    private void agregarElementoPedido(Platillo platillo){

        //Agrega un elemento a la lista observable
        listaPedido.add(platillo);

        //Crea un Nodo temporal con el valor del platillo
        AvlNodo nodo = new AvlNodo(platillo);

        //A la lista enlazada le añade el nodo con el platillo
        listaEnlazadaAVL.insertarNuevoNodo(nodo);

    }

    /**
     * Método para ejecutar la interfaz gráfica después de un inicio de sesión exitoso.
     * Solicita la lista de platillos al servidor y muestra los elementos gráficos.
     */

    private void ejecutarInterfaz(){
        //Se manda a crear una lista de platillos que son tomadas del json correspondiente
        solicitarListaPlatillos();
        try {

            elementosGraficos();

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    /**
     * Método para solicitar la lista de platillos al servidor.
     * Envia un mensaje al servidor para obtener la lista de platillos.
     */
    private void solicitarListaPlatillos(){
            Message message = new Message("obtenerListaPlatillos");

            enviarMensajeServidor(message);
    }

    private void crearListView(ListaEnlazadaAVL listaEnlazadaAVL){

        AvlNodo current = listaEnlazadaAVL.getHead();

        while (current != null){

            listaPlatillos.add(current.getPlatillo());

            current = current.getNext();

        }



        listaEnlazadaAVL.verElementos();

    }



    /**
     * Método que se ejecuta constantemente a la espera de algún mensaje por parte del servidor.
     * Escucha los mensajes entrantes del servidor y realiza acciones según el tipo de mensaje recibido.
     */
    @Override
    public void run(){

        try {

            ServerSocket servidor_local = new ServerSocket(2020);

            Socket socket;

            Message message;

            while (true){

                //Acepta la información entrante de otros clientes o del propio servidor
                socket = servidor_local.accept();

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                message = (Message) in.readObject();

                System.out.println(message.getNombreMetodo());

                switch (message.getNombreMetodo()){

                    case "LoginClient_Exitoso" -> {
                        Platform.runLater(this::ejecutarInterfaz);
                    }

                    case "LoginClient_Fallido" -> {
                        System.out.println("Se mamo");
                    }

                    case "crearListaPlatillos" -> {
                        crearListView(message.getListaEnlazadaAVL());
                    }


                }


            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * Método principal para ejecutar la aplicación.
     *
     * @param args Los argumentos de la línea de comandos.
     */

    public static void main(String[] args){
        launch(args);
    }


}
