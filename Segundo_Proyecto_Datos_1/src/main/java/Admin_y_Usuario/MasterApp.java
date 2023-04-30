package Admin_y_Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterApp{

    //Definir objetos de la interfaz

    //Labels
    Label labelAgregarAdministrador,
            labelPrueba2;

    //Boton de opciones
    ComboBox<String> menuButton;

    //MainPane
    Pane paneMain,paneSuperior,paneOpcion1,paneOpcion2;

    //Cajas de texto
    TextField nombreUsuario,passwordUsuario;

    //TabPane y Tab
    TabPane tabPane;
    Tab tab1,tab2;

    //Botones
    Button botonAgregarAdministrador;

    //Stage
    Stage stage = new Stage();

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




        //Elementos correspondientes al Tab 1
        labelPrueba2 = new Label();
        labelPrueba2.setText("ME QUIERO MUERTAR");


        //Elementos correspondientes al Tab 2

        //Elementos gráficos del paneOpción1

        //Menu de opciones
        menuButton = new ComboBox<>();
        menuButton.setPromptText("Herramientas");
        menuButton.getItems().addAll(
                "Agregar nuevo administrador",
                "Editar o eliminar un administrador"
        );
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

        //Creación de un nuevo administrador
        labelAgregarAdministrador = new Label();
        labelAgregarAdministrador.setText("Agregar un nuevo administrador");
        labelAgregarAdministrador.setTranslateX(570);
        labelAgregarAdministrador.setTranslateY(175);

        nombreUsuario = new TextField();
        nombreUsuario.setPromptText("Nuevo usuario");
        nombreUsuario.setStyle("-fx-alignment: center");
        nombreUsuario.setTranslateX(570);
        nombreUsuario.setTranslateY(250);

        passwordUsuario = new TextField();
        passwordUsuario.setPromptText("Contraseña");
        passwordUsuario.setStyle("-fx-alignment: center");
        passwordUsuario.setTranslateX(570);
        passwordUsuario.setTranslateY(300);

        botonAgregarAdministrador = new Button();
        botonAgregarAdministrador.setText("Agregar");
        botonAgregarAdministrador.setTranslateX(590);
        botonAgregarAdministrador.setTranslateY(370);


        //Elementos correspondientes al paneOpcion2

        //Lista de personas de ejemplo
        ObservableList<String> personas = FXCollections.observableArrayList(
                "Persona1","Persona2","Persona3","Persona4"
        );

        ListView<String> listaPersonas = new ListView<>(personas);
        listaPersonas.setTranslateX(200);
        listaPersonas.setTranslateY(200);
        


        //Se agregan los elementos a un Pane funcionando como barra superior
        paneSuperior.getChildren().addAll(
                menuButton
        );

        //Se agregan los elementos gráficos a un panel
        paneOpcion1.getChildren().addAll(
                labelAgregarAdministrador,
                nombreUsuario,
                passwordUsuario,
                botonAgregarAdministrador
        );

        //Se agregan elementos gráficos a un panel de opciones
        paneOpcion2.getChildren().addAll(
                labelPrueba2,
                listaPersonas
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



        //Escena donde se mostrará los elementos
        Scene masterApp = new Scene(tabPane,1200,700);
        stage.setResizable(false);
        stage.setTitle("MasterApp");
        stage.setScene(masterApp);
        stage.show();


    }
}
