package Servidor;

import Clases_auxiliares.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.w3c.dom.*;


//Controlador de clientes
class ClientHandler extends Thread{

    //Entrada de mensajes textuales
    DataInputStream entradaTexto;
    DataOutputStream salidaTexto;

    //Entrada de mensajes tipo Object
    ObjectInputStream entradaObjects;
    ObjectOutputStream salidaObject;

    Socket socket;

    //Arboles binarios
    ArbolBinario arbolBinarioAdmins;
    ArbolBinario arbolBinarioClient;

    public ClientHandler(Socket socket,DataInputStream entradaTexto,DataOutputStream salidaTexto,ObjectInputStream entradaObjects,ObjectOutputStream salidaObject) {
        this.socket = socket;

        this.entradaTexto = entradaTexto;
        this.salidaTexto = salidaTexto;

        this.entradaObjects = entradaObjects;
        this.salidaObject = salidaObject;

        System.out.println("Se construye el client handler");
        arbolBinarioAdmins = obtenerListaAdministradores();
        arbolBinarioClient = obtenerListaClientes();

    }

    /**
     * Metodo que se encarga de buscar en un archivo XML la llave que almacena Objetos de tipo "user", después de eso
     * Se encarga de ir Objeto por Objeto obteniendo el valor del "username" y el "password" para asignárselo a un
     * Objeto Usuario que posteriormente será añadido como dato que almacenará un Nodo y será añadido a Lista Enlazada.
     * @return Devuelve la Lista Enlazada creada a partir de Nodos que almacenan objetos de tipo Usuario
     */
    public ArbolBinario obtenerListaClientes() {

        ArbolBinario arbolBinario = new ArbolBinario();
        try {
            File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archivos XML\\ClientUsers.xml");

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

                    //System.out.println("usuario: " + username + "     " + "password: " + password);

                    //Se crea un Objeto usuario con el valor de la contraseña y usuario
                    Usuario usuario = new Usuario(username, password);

                    //Se crea un Objeto nodo
                    Nodo nodo = new Nodo();

                    //Se asigna el valor que va a guardar el Nodo
                    nodo.setValor(usuario);

                    //Se agrega el Nodo a la lista enlazada
                    arbolBinario.insertar(nodo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arbolBinario;
    }

    /**
     * Metodo que se encarga de buscar en un archivo XML la llave que almacena Objetos de tipo "user", después de eso
     * Se encarga de ir Objeto por Objeto obteniendo el valor del "username" y el "password" para asignárselo a un
     * Objeto Usuario que posteriormente será añadido como dato que almacenará un Nodo y será añadido a Lista Enlazada.
     * @return Devuelve la Lista Enlazada creada a partir de Nodos que almacenan objetos de tipo Usuario
     */
    public ArbolBinario obtenerListaAdministradores(){

        ArbolBinario arbolBinario = new ArbolBinario();

        try {
            File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archivos XML\\AdminUsers.xml");

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

                    //System.out.println("usuario: " + username + "     " + "password: " + password);

                    //Se crea un Objeto usuario con el valor de la contraseña y usuario
                    Usuario usuario = new Usuario(username,password);

                    //Se crea un Objeto nodo
                    Nodo nodo = new Nodo();

                    //Se asigna el valor que va a guardar el Nodo
                    nodo.setValor(usuario);

                    //Se agrega el Nodo al árbol binario
                    arbolBinario.insertar(nodo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

      return arbolBinario;

    }

    private static void modificarUsuarioAdmin(String username, String newUsername, String newPassword) {
        try {
            File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String currentUsername = element.getElementsByTagName("username").item(0).getTextContent();

                    if (currentUsername.equals(username)) {
                        Element newUsernameElement = (Element) document.createElement("username");
                        newUsernameElement.appendChild(document.createTextNode(newUsername));
                        element.replaceChild(newUsernameElement, element.getElementsByTagName("username").item(0));

                        Element newPasswordElement = (Element) document.createElement("password");
                        newPasswordElement.appendChild(document.createTextNode(newPassword));
                        element.replaceChild(newPasswordElement, element.getElementsByTagName("password").item(0));

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        DOMSource source = new DOMSource(document);

                        StreamResult result = new StreamResult(file);
                        transformer.transform(source, result);

                        System.out.println("Usuario modificado correctamente.");
                        return;
                    }
                }
            }

            System.out.println("El usuario no existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verificarExistenciaAdmins(String user, String password){

        //ArbolBinario arbolBinario = obtenerListaAdministradores();

        boolean resultado = arbolBinarioAdmins.revisarLogin(user,password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }

    public boolean verificarExistenciaClientes(String user, String password){

        //arbolBinarioClients = obtenerListaClientes();

        //ArbolBinario arbolBinario = obtenerListaClientes();

        boolean resultado = arbolBinarioClient.revisarLogin(user, password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }



    //Hilo del administrador de clientes
    @Override
    public void run(){

        //Se mantiene el servidor a la escucha de mensajes entrantes
        while (true){

            try {

                Message message = (Message) entradaObjects.readObject();  //Lee un objeto enviado desde el cliente
                System.out.println("Mensaje cliente: " + message.getNombreMetodo());
                System.out.println("Nombre: " + message.getUsuario());
                System.out.println("Contraseña: " + message.getPassword());

                String usuario = message.getUsuario();
                String contra = message.getPassword();

                if (message.getNombreMetodo().equals("LoginAdmin")){

                    System.out.println("Se manda a revisar el login");

                    boolean existe = verificarExistenciaAdmins(usuario,contra);

                    System.out.println(existe);

                    if (existe){
                        System.out.println("Se intenta responder al client");
                        Message message1 = new Message("Login_Admin_Exitoso");
                        salidaObject.writeObject(message1);
                        System.out.println("Se respondió con éxito");
                    }

                }

                else if (message.getNombreMetodo().equals("LoginClient")){
                    System.out.println("Se manda a revisar el login");
                    /*String user = mensaje.getUsuario();
                    String password = mensaje.getPassword();
                    int identificador = mensaje.getIdentificador();*/

                    System.out.println("Se manda a revisar el login");

                    boolean existe = verificarExistenciaClientes(usuario,contra);

                    System.out.println(existe);

                    if (existe){
                        System.out.println("Se intenta responder al client");
                        Message message1 = new Message("Login_Client_Exitoso");
                        salidaObject.writeObject(message1);
                        System.out.println("Se respondió con éxito");
                    }
                }

                else if (message.getNombreMetodo().equals("Borre eso")){


                    Message message1 = new Message("Hola");

                    boolean existe = arbolBinarioAdmins.revisarLogin(message.getUsuario());
                    System.out.println(existe);
                    //Se manda a borrar al usuario
                    arbolBinarioAdmins.remove(message.getUsuario());
                    //Se verifica si se borro
                    boolean existe1 = arbolBinarioAdmins.revisarLogin(message.getUsuario());
                    System.out.println(existe1);
                    //Se intenta modificar una persona
                    arbolBinarioAdmins.modificarPersona("Erick","Josthin","123");
                    boolean existe2 = arbolBinarioAdmins.revisarLogin("Josthin");
                    System.out.println(existe2);
                    modificarUsuarioAdmin("2","Josthin","123");

                    System.out.println("Se intenta responder al client");
                    salidaObject.writeObject(message1);
                    System.out.println("Se logro responder");




                }


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }



    }

}


//CLass server
public class ServerApp {

    ServerSocket serverSocket;

    public ServerApp() throws IOException {
        this.serverSocket = new ServerSocket(1234);

        ArrancarServer arrancarServer = new ArrancarServer(serverSocket);

        Thread hilo = new Thread(arrancarServer);
        hilo.start();

        System.out.println("*Sonidos de motor arrancando*");

    }

    public static void main(String[] args){

    }

}


class ArrancarServer implements Runnable {

    ServerSocket serverSocket;

    public ArrancarServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();

                System.out.println("Un cliente se ha conectado: " + socket);

                //Maneja entrada y salida de texto
                DataInputStream inText = new DataInputStream(socket.getInputStream());
                DataOutputStream outText = new DataOutputStream(socket.getOutputStream());

                //Maneja entrada y salida de objetos
                ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());

                Thread thread = new ClientHandler(socket, inText, outText, inObject, outObject);
                thread.start();


            } catch (Exception e) {
                //socket.close();
                throw new RuntimeException(e);
            }
        }

    }
}


