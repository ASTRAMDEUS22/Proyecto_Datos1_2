package sample.segundoproyectodatos1;

import javafx.application.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp implements Runnable{

    static final int port = 31416;


    //Lista de usuarios clientes
    List<Usuario> usuarioList;

    public ServerApp() {
        Thread hilo = new Thread(this);
        hilo.start();
    }

    //Obtiene la lista de personas en el XML y comprueba la información enviada desde el login con la info de las listas
    public void comprobarLogin(String usuario,String password){
        obtenerListaAdministradores();  //Se obtiene una lista de las personas administradoras del XML

        int i = 0;

        while (i < usuarioList.size()){

            //Si el usuario coincide
            if (usuarioList.get(i).getUsername().equals(usuario)){
                //Si la contraseña coincide
                if (usuarioList.get(i).getPassword().equals(password)) {
                    ClientApp clientApp = new ClientApp();

                    //Si coincide el usuario y contraseña, ejecute la app cliente
                    try {
                        clientApp.elementosGraficos();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;  //Salga del ciclo una vez se haya generado la clientApp
                }
            }

            i++;  //Incremente el índice

        }




    }

    public void obtenerListaAdministradores(){

        List<Usuario> usuarios = new ArrayList<>();

        try {

            File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\.idea\\Users.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(file);

            document.getDocumentElement().normalize();

            //Obtener lista de elementos con la etiqueta "user"
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    usuarios.add(new Usuario(username, password));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        usuarioList = usuarios;  //Le añade los valores creados en este método a una lista previamente creada

    }


    @Override
    public void run() {

            try {

                ServerSocket servidor = new ServerSocket(1234);

                while (true) {
                    Socket socketRecibido = servidor.accept();  //Se acepta lo enviado por el puerto 1234

                    ObjectInputStream recibir_datos = new ObjectInputStream(socketRecibido.getInputStream());  //Lee lo enviado desde un socket

                    Message message = (Message) recibir_datos.readObject();

                    System.out.println(message);

                    Platform.runLater(() -> {  //
                        if (message.getNombreMetodo().equals("comprobarLogin")) {
                            comprobarLogin(message.getUsuario(), message.getPassword());
                        }
                    });
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }
}
