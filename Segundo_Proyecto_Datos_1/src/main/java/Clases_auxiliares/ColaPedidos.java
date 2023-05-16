package Clases_auxiliares;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ColaPedidos {

    private static final String CLIENT_USERS_XML_FILE = "Archivos XML\\ClientUsers.xml";

    public static void main(String[] args) {
        MyList<JsonObject> listaPlatillos = new MyList<>();
        int sumaTiempos = 0;

        // Leer el archivo JSON existente en un objeto JsonArray
        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir los platillos del archivo JSON
        System.out.println("Platillos disponibles:");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject platillo = jsonArray.get(i).getAsJsonObject();
            String nombre = platillo.get("nombre").getAsString();
            int calorias = platillo.get("calorias").getAsInt();
            int tiempoPreparacion = platillo.get("tiempoPreparacion").getAsInt();
            float precio = platillo.get("precio").getAsFloat();

            System.out.println(nombre + ": " + calorias + " calorías, " + tiempoPreparacion + " minutos de preparación, ₡" + precio);
        }

        // Obtener el nombre de usuario desde el archivo XML
        String nombreUsuario = obtenerNombreUsuarioDesdeXML(CLIENT_USERS_XML_FILE);
        System.out.println("Nombre de usuario: " + nombreUsuario);

        // Añadir platillos a la lista y sumar sus tiempos de preparación
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los nombres de los platillos que desea agregar a la lista (escriba 'fin' para terminar):");
        String nombrePlatillo = scanner.nextLine();
        while (!nombrePlatillo.equals("fin")) {
            // Buscar el platillo en el archivo JSON
            JsonObject platillo = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject platilloActual = jsonArray.get(i).getAsJsonObject();
                if (platilloActual.get("nombre").getAsString().equals(nombrePlatillo)) {
                    platillo = platilloActual;
                    break;
                }
            }
            if (platillo != null) {
                listaPlatillos.add(platillo);
                sumaTiempos += platillo.get("tiempoPreparacion").getAsInt();
            } else {
                System.out.println("El platillo ingresado no se encuentra en la lista.");
            }

            nombrePlatillo = scanner.nextLine();
        }

        // Imprimir la lista de platillos y la suma de tiempos
        System.out.println("Lista de platillos agregados:");
        for (int i = 0; i < listaPlatillos.size(); i++) {
            JsonObject platillo = listaPlatillos.get(i);
            String nombre = platillo.get("nombre").getAsString();
            int tiempoPreparacion = platillo.get("tiempoPreparacion").getAsInt();
            System.out.println(nombre + ": " + tiempoPreparacion + " minutos de preparación");
        }

        System.out.println("Tiempo total de preparación de los platillos agregados: " + sumaTiempos + " minutos");

        int tiempoTotal = 0;
        for (int i = 0; i < listaPlatillos.size(); i++) {
            JsonObject platillo = listaPlatillos.get(i);
            tiempoTotal += platillo.get("tiempoPreparacion").getAsInt();
        }

        System.out.println("Tiempo estimado de preparacion del pedido: " + tiempoTotal + " minutos");

        // Actualizar el historial del usuario en el archivo XML
        actualizarHistorialUsuarioEnXML(CLIENT_USERS_XML_FILE, nombreUsuario, listaPlatillos);

        int tiempoRestante = tiempoTotal;
        while (tiempoRestante > 0) {
            System.out.println("Tiempo restante: " + tiempoRestante + " segundos");
            try {
                Thread.sleep(1000); // Esperar un segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tiempoRestante--;
        }
        System.out.println("El pedido está listo");
    }

    private static String obtenerNombreUsuarioDesdeXML(String filePath) {
        String nombreUsuario = "";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            Element userElement = (Element) root.getElementsByTagName("user").item(0);
            Element usernameElement = (Element) userElement.getElementsByTagName("username").item(0);
            nombreUsuario = usernameElement.getTextContent();
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        return nombreUsuario;
    }


    private static void actualizarHistorialUsuarioEnXML(String filePath, String nombreUsuario, MyList<JsonObject> listaPlatillos) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            Element userElement = (Element) root.getElementsByTagName("user").item(0);

            // Crear un nuevo elemento <Pedido> para el historial
            Element pedidoElement = document.createElement("Pedido");
            pedidoElement.setAttribute("fecha", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

            for (int i = 0; i < listaPlatillos.size(); i++) {
                JsonObject platillo = listaPlatillos.get(i);
                String nombrePlatillo = platillo.get("nombre").getAsString();

                // Crear un nuevo elemento <Platillo> dentro del elemento <Pedido>
                Element platilloElement = document.createElement("Platillo");
                platilloElement.setTextContent(nombrePlatillo);

                pedidoElement.appendChild(platilloElement);
            }

            // Agregar el elemento <Pedido> al elemento <User>
            userElement.appendChild(pedidoElement);

            // Guardar los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(filePath);
            transformer.transform(source, result);

            System.out.println("Historial de pedidos actualizado para el usuario: " + nombreUsuario);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}




