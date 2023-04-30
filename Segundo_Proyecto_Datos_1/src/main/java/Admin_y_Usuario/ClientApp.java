package Admin_y_Usuario;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Clases_auxiliares.Message;
import Clases_auxiliares.Platillo;

import java.io.*;
import java.net.Socket;

public class ClientApp{

    //Definir objetos de la interfaz

    //Labels
    Label labelMenu,
            labelInfoPlatillo,
            labelNombre,
            labelCantidadCalorias,
            labelPrecio,
            labelNombrePlatillo,
            labelCantidadCaloriasPlatillo,
            labelPrecioPlatillo;

    //Pane
    Pane mainPane,
            paneMenu;

    //Stage
    Stage stage = new Stage();


    //Lista de personas de ejemplo
    ObservableList<Platillo> listaPlatillos = FXCollections.observableArrayList();


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
        labelNombre.setTranslateY(100);

        Label labelNombrePlatillo = new Label();
        labelNombrePlatillo.setTranslateX(300);
        labelNombrePlatillo.setTranslateY(120);

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



        paneMenu = new Pane();
        paneMenu.setStyle("-fx-border-color: #000;-fx-border-width: 2");
        paneMenu.setMinWidth(500);  //Anchura mínima
        paneMenu.setMinHeight(425);  //Altura mínima
        paneMenu.setTranslateX(50);
        paneMenu.setTranslateY(50);

        /*Se va a crear un Socket que comunicará la ClientApp y el ServerApp para crear la lista de Platillos
        obtenidos a partir del archivo JSON.
         */
       //solicitarListaPlatillos();


        ListView<Platillo> listViewPlatillos = new ListView<>(listaPlatillos);

        Platillo platillo1 = new Platillo("Arroz con Pollo",2500,1200);
        Platillo platillo2 = new Platillo("Olla de carne",6000,3400);
        Platillo platillo3 = new Platillo("Batido de fresa",1200,2500);
        Platillo platillo4 = new Platillo("Bistec",3000,2360);
        Platillo platillo5 = new Platillo("Cerdo",20000,32520);

        //Añadir los platillos temporales a la lista
        listaPlatillos.addAll(
                platillo1,
                platillo2,
                platillo3,
                platillo4,
                platillo5
        );


        //Detecta la selección de un elemento en la lista
        listViewPlatillos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Platillo>() {
            @Override
            public void changed(ObservableValue<? extends Platillo> observableValue, Platillo s, Platillo elementoSeleccionado) {

                //Se cambian los textos de los Labels por los del Objeto de la lista seleccionado
                labelNombrePlatillo.setText(elementoSeleccionado.getNombrePlatillo());
                labelCantidadCaloriasPlatillo.setText(String.valueOf(elementoSeleccionado.getCalorias()));
                labelPrecioPlatillo.setText("₡ " + elementoSeleccionado.getPrecio());
            }
        });


        listViewPlatillos.setMaxHeight(375);
        listViewPlatillos.setMinHeight(350);
        listViewPlatillos.setTranslateX(25);
        listViewPlatillos.setTranslateY(25);


        //crearListaEjemplos();  //Crea temporalmente una lista de personas



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
                listViewPlatillos
        );


        //Agrega al Pane principal los demás contenedores
        mainPane.getChildren().addAll(
                paneMenu);


        //Escena donde se mostrará los elementos
        Scene clientApp = new Scene(mainPane,1200,700);
        stage.setTitle("ClientApp");
        stage.setScene(clientApp);
        stage.show();


    }

    private void solicitarListaPlatillos(){
        //System.out.println("solicitarLitaPlatillo");
        try {

            Socket socket = new Socket("192.168.1.246",1234);

            Message message = new Message("obtenerListaPlatillos");

            ObjectOutputStream envio_de_datos = new ObjectOutputStream(socket.getOutputStream());

            envio_de_datos.writeObject(message);

            socket.close();


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
