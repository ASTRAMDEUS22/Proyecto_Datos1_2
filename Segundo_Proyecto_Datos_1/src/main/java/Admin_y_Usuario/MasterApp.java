package Admin_y_Usuario;

import Clases_auxiliares.ListaEnlazada;
import Clases_auxiliares.Message;
import Clases_auxiliares.Nodo;
import Clases_auxiliares.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MasterApp implements Runnable{

    //Cosas relacionadas al conectarse al servidor
    Socket socket;

    //Puente de salida de mensajes entre el cliente y server
    ObjectOutputStream out;
    //Puente de entrada de mensajes entre el cliente y server
    ObjectInputStream in;

    public MasterApp() {

        try {

            //Inicializa el socket en un host y puerto específico
            this.socket = new Socket("192.168.1.246", 1234);

            //Inicializa el envio de información
            this.out = new ObjectOutputStream(socket.getOutputStream());

            //Recibe las respuestas del server
            this.in = new ObjectInputStream(socket.getInputStream());

            //Crea un hilo
            Thread hilo = new Thread(this);
            hilo.start();




        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }




    //Definir objetos de la interfaz

    //Lista de personas de ejemplo
    ObservableList<Usuario> personas = FXCollections.observableArrayList();

    ListView<Usuario> listaPersonas = new ListView<>(personas);



    //Labels
    Label labelAgregarAdministrador,
            labelUserSelect;

    //Boton de opciones
    ComboBox<String> menuButton;

    //MainPane
    Pane paneMain,paneSuperior,paneOpcion1,paneOpcion2;

    //Cajas de texto
    TextField userField_Pane1,
            passWordField_Pane1,
            userField_Pane2,
            passWordField_Pane2;

    //TabPane y Tab
    TabPane tabPane;
    Tab tab1,tab2;

    //Botones
    Button botonAgregarAdministrador,botonEliminarAdministrador,botonEditarAdministrador;

    //Stage
    Stage stage = new Stage();

    //Indice de un elemento seleccionado de la lista
    int index;

    /**
     * Se encarga de cargar los elementos gráficos de la interfaz
     */
    public void elementosGraficos() throws IOException {
        paneMain = new Pane();

        //Panel de la zona superior, separa la barra de opciones de los otros Pane
        paneSuperior = new Pane();
        //paneSuperior.setStyle("-fx-border-color: #ff0000");
        paneSuperior.setMaxHeight(26);

        //Pane correspondiente a la opción #1 del ComboBox
        paneOpcion1 = new Pane();
        //paneOpcion1.setStyle("-fx-border-color: #0048ff");
        paneOpcion1.setMinWidth(1200);  //Anchura mínima
        paneOpcion1.setMinHeight(700);  //Altura mínima
        paneOpcion1.setTranslateY(26);

        //Pane correspondiente a la opción #2 del ComboBox
        paneOpcion2 = new Pane();
        //paneOpcion2.setStyle("-fx-border-color: #00ff23");
        paneOpcion2.setMinWidth(1200);  //Anchura mínima
        paneOpcion2.setMinHeight(700);  //Altura mínima
        paneOpcion2.setTranslateY(26);
        paneOpcion2.setVisible(false);

        //Es donde todos los elementos se alojan
        tabPane = new TabPane();

        /*
        Se define los Tab, las pestañas del TabPane
         */
        //Tab 1
        tab1 = new Tab();  //Pestaña del TabPane
        tab1.setText("Menus");
        tab1.setClosable(false);  //Desactivar el cierre

        //Tab 2
        tab2 = new Tab();  //Pestaña del TabPane
        tab2.setText("Opciones de administrador");
        tab2.setClosable(false);  //Desactivar el cierre

        //Menu de opciones
        menuButton = new ComboBox<>();
        menuButton.setPromptText("Herramientas");
        menuButton.getItems().addAll(
                "Agregar nuevo administrador",
                "Editar o eliminar un administrador"
        );


        //Elementos correspondientes al Tab 1

        //Elementos correspondientes al Tab 2

        //Detecta cuando un elemento de la lista es seleccionado
        menuButton.setOnAction(new EventHandler<ActionEvent>() {  //Detecta un evento al seleccionar un Item del ComboBox
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = menuButton.getSelectionModel().getSelectedIndex();  //Se obtiene el índice del evento

                if (selectedItem == 0){  //Si el item seleccionado es el primero, mostrar el Pane 1
                    paneOpcion1.setVisible(true);
                    paneOpcion2.setVisible(false);
                }else{  //Si el item seleccionado es el segundo, mostrar el Pane 2
                    paneOpcion1.setVisible(false);
                    paneOpcion2.setVisible(true);
                }

            }
        });




        //Elementos gráficos del paneOpción1

        //Creación de un nuevo administrador
        labelAgregarAdministrador = new Label();
        labelAgregarAdministrador.setText("Agregar un nuevo administrador");
        labelAgregarAdministrador.setTranslateX(570);
        labelAgregarAdministrador.setTranslateY(175);

        userField_Pane1 = new TextField();
        userField_Pane1.setPromptText("Nuevo usuario");
        userField_Pane1.setStyle("-fx-alignment: center");
        userField_Pane1.setTranslateX(570);
        userField_Pane1.setTranslateY(250);

        passWordField_Pane1 = new TextField();
        passWordField_Pane1.setPromptText("Contraseña");
        passWordField_Pane1.setStyle("-fx-alignment: center");
        passWordField_Pane1.setTranslateX(570);
        passWordField_Pane1.setTranslateY(300);

        botonAgregarAdministrador = new Button();
        botonAgregarAdministrador.setText("Agregar");
        botonAgregarAdministrador.setOnAction(e -> agregarNuevoAdmin(userField_Pane1.getText(),passWordField_Pane1.getText()));
        botonAgregarAdministrador.setTranslateX(590);
        botonAgregarAdministrador.setTranslateY(370);


        //Elementos correspondientes al paneOpcion2

        userField_Pane2 = new TextField();
        userField_Pane2.setPromptText("Nuevo usuario");
        userField_Pane2.setStyle("-fx-alignment: center");
        userField_Pane2.setTranslateX(570);
        userField_Pane2.setTranslateY(250);

        passWordField_Pane2 = new TextField();
        passWordField_Pane2.setPromptText("Contraseña");
        passWordField_Pane2.setStyle("-fx-alignment: center");
        passWordField_Pane2.setTranslateX(570);
        passWordField_Pane2.setTranslateY(300);

        botonEliminarAdministrador = new Button();
        botonEliminarAdministrador.setText("Eliminar");
        botonEliminarAdministrador.setOnAction(e -> eliminarAdministrador(labelUserSelect.getText()));
        botonEliminarAdministrador.setTranslateX(400);
        botonEliminarAdministrador.setTranslateY(260);

        botonEditarAdministrador = new Button();
        botonEditarAdministrador.setText("Editar");
        botonEditarAdministrador.setOnAction(e -> editarAdministrador(
                labelUserSelect.getText(),
                userField_Pane2.getText(),
                passWordField_Pane2.getText()
        ));
        botonEditarAdministrador.setTranslateX(570);
        botonEditarAdministrador.setTranslateY(360);


        labelUserSelect = new Label();
        labelUserSelect.setText("GG IZZI PIZZI");
        labelUserSelect.setTranslateX(400);
        labelUserSelect.setTranslateY(200);




        //ListView
        listaPersonas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>(){
            @Override
            public void changed(ObservableValue<? extends Usuario> observableValue,Usuario s,Usuario elementoSeleccionado){
                labelUserSelect.setText(elementoSeleccionado.getUsername());
                index = listaPersonas.getSelectionModel().getSelectedIndex();
            }
        });

        listaPersonas.setMaxHeight(300);

        //Ubicación de la ListView
        listaPersonas.setTranslateX(50);
        listaPersonas.setTranslateY(50);




        //Se agregan los elementos a un Pane funcionando como barra superior
        paneSuperior.getChildren().addAll(
                menuButton
        );

        //Se agregan los elementos gráficos a un panel
        paneOpcion1.getChildren().addAll(
                labelAgregarAdministrador,
                userField_Pane1,
                passWordField_Pane1,
                botonAgregarAdministrador
        );

        //Se agregan elementos gráficos a un panel de opciones
        paneOpcion2.getChildren().addAll(
                labelUserSelect,
                listaPersonas,
                botonEliminarAdministrador,
                botonEditarAdministrador,
                userField_Pane2,
                passWordField_Pane2
        );

        //Se agregan todos los Pane a un Pane principal
        paneMain.getChildren().addAll(
                paneSuperior,
                paneOpcion1,
                paneOpcion2
        );

        //Agrego el paneMain al tab2
        tab2.setContent(paneMain);


        //Agrego los Tab al TabPane
        tabPane.getTabs().addAll(  //Agregar los Tab al TabPane
                tab1,
                tab2
        );

        //Se le pide al servidor que envie una lista con la cuál se pueda formar la listView
        cargarListaUsers();

        //Escena donde se mostrará los elementos
        Scene masterApp = new Scene(tabPane,1200,700);
        stage.setResizable(false);
        stage.setTitle("MasterApp");
        stage.setScene(masterApp);
        stage.show();


    }

    public void agregarNuevoAdmin(String user,String contra){
        //Se crea un mensaje para el server
        Message message = new Message("agregarNuevoAdmin",user,contra);

        try {
            out.writeObject(message);
            System.out.println("Mensaje enviado");
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        personas.add(new Usuario(user,contra));

    }

    public void editarAdministrador(String user,String newUser,String newPassword){

        System.out.println("Entro aqui con: " + user + " " + newUser + " " + newPassword);

        Message message = new Message("editarAdministrador",user,newUser,newPassword);

        try {
            out.writeObject(message);
            System.out.println("Se mando el mensaje");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void editarPersonaLista(String user,String password){
        System.out.println("Entra en editar persona");

        personas.get(index).setUsername(user);
        personas.get(index).setPassword(password);

    }

    public void eliminarAdministrador(String user){

        Message message = new Message("eliminarAdministrador",user);

        try {
            out.writeObject(message);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        personas.remove(index);


    }

    public void crearListView(ListaEnlazada listaEnlazada){

        int size = listaEnlazada.getSize();

        Nodo current = listaEnlazada.getHead();

        for (int i = 0; i < size;i++){

            personas.add(current.getValor());

            current = current.getNext();

        }


        listaEnlazada.verElementos();
    }

    public void cargarListaUsers(){
        Message message = new Message("obtenerListaUsers");

        try {
            out.writeObject(message);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }


    /**
     * Metodo que ejecuta la interfaz de Runnable
     */
    @Override
    public void run(){

        try {
            while (true){

                //Lee la info del server
                Message message = (Message) in.readObject();

                if (message.getNombreMetodo().equals("crearListaUsers")) {
                    crearListView(message.getListaEnlazada());
                }

                else if (message.getNombreMetodo().equals("usuario modificado correctamente")){
                    System.out.println("Entra acá");
                    editarPersonaLista(message.getUsuario(),message.getPassword());
                }

            }












        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }


}
