package Servidor;

import Clases_auxiliares.*;
import com.google.gson.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//CLass server
public class ServerApp implements Runnable {

    ServerSocket serverSocket;

    public ServerApp() throws IOException {

        this.serverSocket = new ServerSocket(1234);

        System.out.println("*Server noises turning on*");

        Thread hilo = new Thread(this);
        hilo.start();

        this.arbolBinarioClient = obtenerListaClientes();
        this.arbolBinarioAdmins = obtenerListaAdministradores();
        this.arbolAVL = obtenerPlatillos();

        this.preparacionActual = new PreparacionPedido();

        Thread hilo1 = new Thread(preparacionActual);
        hilo1.start();

        //restarContador();


    }

    PreparacionPedido preparacionActual;

    //Arboles binarios
    ArbolBinario arbolBinarioAdmins;
    ArbolBinario arbolBinarioClient;

    ArbolALV arbolAVL;

    //Lista enlazada
    ListaEnlazada listaEnlazada;
    ListaEnlazadaAVL listaEnlazadaAVL;

    /**
     * Metodo que se encarga de buscar en un archivo XML la llave que almacena Objetos de tipo "user", después de eso
     * Se encarga de ir Objeto por Objeto obteniendo el valor del "username" y el "password" para asignárselo a un
     * Objeto Usuario que posteriormente será añadido como dato que almacenará un Nodo y será añadido a Lista Enlazada.
     * @return Devuelve la Lista Enlazada creada a partir de Nodos que almacenan objetos de tipo Usuario
     */
    private ArbolBinario obtenerListaClientes() {

        ArbolBinario arbolBinario = new ArbolBinario();
        try {
            //File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archivos XML\\ClientUsers.xml");

            File file = new File("Archivos XML\\ClientUsers.xml");

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
                    nodo.setUsuario(usuario);

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
    private ArbolBinario obtenerListaAdministradores(){

        ArbolBinario arbolBinario = new ArbolBinario();

        try {
            File file = new File("Archivos XML\\AdminUsers.xml");

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

                    //Se crea un Objeto usuario con el valor de la contraseña y usuario
                    Usuario usuario = new Usuario(username,password);

                    //Se crea un Objeto nodo
                    Nodo nodo = new Nodo();

                    //Se asigna el valor que va a guardar el Nodo
                    nodo.setUsuario(usuario);

                    //Se agrega el Nodo al árbol binario
                    arbolBinario.insertar(nodo);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arbolBinario;

    }

    private ArbolALV obtenerPlatillos(){

        ArbolALV arbolALVTemp = new ArbolALV();

        JsonArray jsonArray = null;

        try (Reader reader = new FileReader("Archvos JSON\\Platillos1.JSON")){
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader,JsonArray.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        //Buscar los platillos en el JSON
        for (int i = 0; i < jsonArray.size(); i++){

            JsonObject platilloJson = jsonArray.get(i).getAsJsonObject();

            //Crear el objeto platillo
            String nombre = platilloJson.get("nombre").getAsString();
            int calorias = platilloJson.get("calorias").getAsInt();
            int tiempoPreparacion = platilloJson.get("tiempoPreparacion").getAsInt();
            float precio = platilloJson.get("precio").getAsFloat();

            Platillo platillo = new Platillo(nombre,calorias,precio,tiempoPreparacion);

            //Agregar el platillo al árbol
            arbolALVTemp.insertar(platillo);


        }


        //arbolALVTemp.preorden(arbolALVTemp.getRaiz());

        return arbolALVTemp;
    }

    private void modificarUsuarioAdmin(String username, String newUsername, String newPassword) {
        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList nodeList = document.getElementsByTagName("user");

            listaEnlazada.verElementos();



            System.out.println("wewewewe" + username + " " + newUsername);

            //Se elimina del árbol binario
            arbolBinarioAdmins.eliminarNodo(username);

            //Se crea un nuevo Nodo
            Nodo nodo = new Nodo();
            Usuario usuario = new Usuario(newUsername,newPassword);
            nodo.setUsuario(usuario);

            //Se agrega el nuevo nodo con los nuevos valores de usuario y contraseña
            arbolBinarioAdmins.insertar(nodo);

            //Se edita el elemento en la lista enlazada
            listaEnlazada.editarElemento(username,newUsername,newPassword);

            listaEnlazada.verElementos();

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
                        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
                        DOMSource source = new DOMSource(document);

                        StreamResult result = new StreamResult(file);
                        transformer.transform(source, result);

                        Message message = new Message("usuarioModificado_correctamente",newUsername,newPassword);

                        //salidaObject.writeObject(message);
                        enviarMensaje_Admin(message);

                    }
                }
            }

            //System.out.println("El usuario no existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método que se encarga de añadir una persona al XML en formato "username" y "password".
     * El método analiza un archivo de tipo XML, crea un objeto de tipo "user" y unos parámetros de tipo "username" y
     * "password", a los parámetros se les asigna el valor textual pasado en los atributos del método, se hace un salto
     * de línea y al final crea un objeto de tipo "usuario" que será añadido al árbol binario de búsqueda y a una lista
     * enlazada.
     * @param username parámetro de tipo String que contiene el texto de un TextField.
     * @param password parámetro de tipo String que contiene el texto de un TextField.
     */
    private void agregarUsuarioXML(String username,String password){
        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = (Element) document.getDocumentElement();

            Element user = (Element) document.createElement("user");
            Element usernameElement = (Element) document.createElement("username");
            Element passwordElement = (Element)  document.createElement("password");

            usernameElement.appendChild(document.createTextNode(username));
            passwordElement.appendChild(document.createTextNode(password));

            user.appendChild(usernameElement);
            user.appendChild(passwordElement);

            root.appendChild(user);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            Usuario usuario = new Usuario(username,password);
            Nodo nodo = new Nodo();
            nodo.setUsuario(usuario);

            listaEnlazada.insertarNuevoNodo(nodo);
            arbolBinarioAdmins.insertar(nodo);

            System.out.println("Usuario agregado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que busca un elemento de tipo String en el árbol binario y lista enlazada eliminándolo.
     * El método analiza un archivo XML que contiene a los usuarios administradores, dentro se busca los objetos con
     * la etiqueta "user", después, va objeto en objeto analizando el atributo "username" verificando que su contenido
     * coincida con el parámetro del método "username", habiendo encontrado un elemento coincidente, elimina el objeto
     * del XML y también del árbol binario así como de la lista enlazada.
     * @param username parámetro de tipo String que indica el objeto que será eliminado del XML.
     */
    private void eliminarUsuarioDeXML(String username) {

        listaEnlazada.eliminarNodo(username);
        arbolBinarioAdmins.eliminarNodo(username);
        boolean existe=  listaEnlazada.devolverElemento(username);
        System.out.println(existe);

        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = (Element) document.getDocumentElement();
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String usernameElement = element.getElementsByTagName("username").item(0).getTextContent();
                    if (usernameElement.equals(username)) {
                        root.removeChild(node);
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);


        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void eliminarPLatilloJson(String platilloEliminar){

        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject platillo = jsonArray.get(i).getAsJsonObject();
            if (platillo.get("nombre").getAsString().equals(platilloEliminar)) {
                jsonArray.remove(i);
                break;
            }
        }



        Platillo platillo = new Platillo(platilloEliminar);

        arbolAVL.eliminar(platillo);

        System.out.println("El platillo " + platilloEliminar + " ha sido eliminado.");


        // Escribir el JsonArray modificado en el archivo JSON
        try (Writer writer = new FileWriter("Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Se responde al master que un platillo fue eliminado
        Message message = new Message("platilloEliminado");

        enviarMensaje_Admin(message);


    }

    private boolean verificarExistenciaAdmins(String user, String password){

        boolean resultado = arbolBinarioAdmins.revisarLogin(user,password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }

    private boolean verificarExistenciaClientes(String user, String password){

        boolean resultado = arbolBinarioClient.revisarLogin(user, password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }

    private void verificarAdmin(String usuario, String contra){

        boolean existe = verificarExistenciaAdmins(usuario, contra);
        System.out.println(existe);

        if (existe){
            Message message1 = new Message("LoginAdmin_Exitoso");

            enviarMensaje_Admin(message1);

        }else {
            Message message1 = new Message("LoginAdmin_Fallido");
            enviarMensaje_Admin(message1);
        }

    }

    private void verificarClients(String usuario,String  contra){

        boolean existe = verificarExistenciaClientes(usuario,contra);

        System.out.println(existe);

            if (existe) {

                Message message1 = new Message("LoginClient_Exitoso");

                enviarMensaje_Cliente(message1);

            }else {


                Message message1 = new Message("LoginClient_Faliido");
                enviarMensaje_Cliente(message1);

            }


    }

    private void obtenerListaPlatillos(){

        Message message = new Message("crearListaPlatillos");

        //Se genera la lista
        arbolAVL.crearListaPlatillos();
        listaEnlazadaAVL = arbolAVL.getListaEnlazada();
        message.setListaEnlazadaAVL(listaEnlazadaAVL);

        enviarMensaje_Cliente(message);


    }

    private void obtenerListaUsers(){
        Message message1 = new Message("crearListaUsers");

        //Se genera la lista
        arbolBinarioAdmins.crearListaUsers();
        listaEnlazada = arbolBinarioAdmins.getListaEnlazada();
        message1.setListaEnlazada(listaEnlazada);

        System.out.println("Se trata de responder al masterapp");

        enviarMensaje_Admin(message1);

        System.out.println("Se ha respondido con éxito");
    }


    private void enviarMensaje_Cliente(Message message){
        try {
            Socket respuesta = new Socket("localhost", 2020);  //El puerto corresponde al client

            ObjectOutputStream out = new ObjectOutputStream(respuesta.getOutputStream());



            out.writeObject(message);


            respuesta.close();
            out.close();

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    ListaColaPedidos colaPedidos = new ListaColaPedidos();

    //Tiempo preparación de todos los pedidos
    int tiempoPlatilloListo;
    int tiempoPlatilloActual;

    //ListaEnlazadaAVL pedido;


    /**
     * Se envia un pedido, el cuál será almacenado en un nodo, luego ese nodo será almacenado en la lista
     * de "colaPedidos"
     *
     */
    private void agregarElementoColaPedidos(ListaEnlazadaAVL pedido,Socket socket){

        /*try {
            localsocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(localsocket.isClosed());*/

        //Creamos un Nodo temporal
        Nodo nodo = new Nodo();

        //Al nodo se le asigna el valor del pedido
        nodo.setListaEnlazadaAVL(pedido);

        colaPedidos.insertar(nodo);

        tiempoPlatilloListo += nodo.getListaEnlazadaAVL().calcularTiempoPreparacion();

        System.out.println(tiempoPlatilloListo);


        //Se queda pegado aqui
        preparacionActual.agregarNuevoPedido(pedido,socket);
        //preparacionActual.run();
        System.out.println("weweee");


    }

    private void actualizarLista(ListaEnlazadaAVL pedido,Socket socket){



        agregarElementoColaPedidos(pedido,socket);

    }

    /*public void preparacionActual(){

        //Mientras la cola de pedidos tenga elementos, ejecute
        while (colaPedidos.getHead() != null) {

            //Muetra las listas con pedidos en la cola
            colaPedidos.mostrarElementos();

            //Mientras la lista AVL tenga platos, ejecute
            while (colaPedidos.getListaNodo().getHead() != null) {

                //Tiempo del plato actual
                int time = colaPedidos.getListaNodo().getHead().getPlatillo().getTiempo();

                //Contador que termina de ejecutar al llegar a un número negativo
                while (time > -1) {

                    System.out.println("Tiempo platillo actual: " + time);

                    //Disminuye el tiempo
                    time--;

                    //Pausa de 1 segundo
                    try {

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {

                        throw new RuntimeException(e);

                    }

                }

                //Habiendo terminado la preparación del plato actual, se prepara el siguiente
                colaPedidos.getListaNodo().eliminarPrimero();

            }

            //Habiendo terminado de preparar todos los platillos, sigue con el siguiente pedido
            colaPedidos.eliminarPrimero();

        }

    }*/

    public void restarContador(){
        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            if (tiempoPlatilloListo > 0) {
                tiempoPlatilloListo -= 1;
            } else  {
                tiempoPlatilloListo = 0;
            }
            //System.out.println("Tiempo de todos los pedidos: " + tiempoPlatilloListo);
        }


    }


    private void enviarMensaje_Admin(Message message){
        try {
            System.out.println("Entra al enviarCliente");
            Socket respuesta = new Socket("localhost", 4040);  //El puerto corresponde al client
            System.out.println("se contruye el socket");
            ObjectOutputStream out = new ObjectOutputStream(respuesta.getOutputStream());
            System.out.println("se contruye el out");


            out.writeObject(message);
            System.out.println("se envia el mensaje al cliente");

            respuesta.close();
            out.close();
            System.out.println("Se cierra el socket");

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }






    public static void main(String[] args) throws IOException {
        ServerApp serverApp = new ServerApp();

    }

    @Override
    public void run(){


        try {
            while (true){




                //Aceptar conexiones
                Socket socket = serverSocket.accept();



                System.out.println("Un cliente se ha conectado: " + socket);

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());



                Message message = (Message) in.readObject();
                System.out.println(message.getNombreMetodo());



                switch (message.getNombreMetodo()){

                    /*-------CONEXIONES CON EL CLIENTE--------*/
                    case "LoginClient" -> {
                        //Message message1 = new Message("prueba");
                        String user = message.getUsuario();
                        String contra = message.getPassword();

                        verificarClients(user,contra);
                        //obtenerPlatillos();

                    }

                    case "obtenerListaPlatillos" -> {
                        obtenerListaPlatillos();
                    }

                    case "agregarPedidoCola" -> {
                        //agregarElementoColaPedidos(message.getListaEnlazadaAVL(),socket);
                        Message newMessage = new Message();
                        newMessage.setListaEnlazadaAVL(message.getListaEnlazadaAVL());

                        actualizarLista(newMessage.getListaEnlazadaAVL(),socket);

                        //socket.close();
                        System.out.println("socket close");
                    }



                    /*---------CONEXIONES CON EL ADMIN---------*/
                    case "LoginAdmin" -> {
                        //Message message1 = new Message("prueba");

                        String user = message.getUsuario();
                        String contra = message.getPassword();

                        verificarAdmin(user,contra);

                    }

                    case "obtenerListaUsers" -> {
                        obtenerListaUsers();
                    }

                    case "editarAdministrador" -> {

                        String user = message.getUsuario();
                        String newUser = message.getNewUsuario();
                        String newContra = message.getNewPassword();

                        modificarUsuarioAdmin(user,newUser,newContra);
                    }

                    case "agregarNuevoAdmin" -> {

                        String user = message.getUsuario();
                        String contra = message.getPassword();

                        agregarUsuarioXML(user,contra);
                    }

                    case "eliminarAdministrador" -> {
                        String user = message.getUsuario();
                        eliminarUsuarioDeXML(user);
                    }


                }

                socket.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}





