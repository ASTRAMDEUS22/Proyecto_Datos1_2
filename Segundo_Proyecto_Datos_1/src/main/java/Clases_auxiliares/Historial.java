package Clases_auxiliares;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Clase para obtener el historial de pedidos de un usuario a partir de un archivo XML.
 */
public class Historial {
    /**
     * Método principal que busca y muestra en la consola los pedidos ligados a un usuario específico.
     *
     * @param args Los argumentos de la línea de comandos .
     */
    public static void main(String[] args) {
        String username = "Client"; // Ingresa el nombre de usuario para buscar sus pedidos

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("Archivos XML\\ClientUsers.xml");

            NodeList userList = document.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                String currentUsername = getTextContent(user, "username");

                if (currentUsername.equals(username)) {
                    NodeList pedidoList = user.getElementsByTagName("Pedido");

                    for (int j = 0; j < pedidoList.getLength(); j++) {
                        Element pedido = (Element) pedidoList.item(j);
                        String fecha = pedido.getAttribute("fecha");
                        System.out.println("Fecha del pedido: " + fecha);

                        NodeList platilloList = pedido.getElementsByTagName("Platillo");

                        for (int k = 0; k < platilloList.getLength(); k++) {
                            Element platillo = (Element) platilloList.item(k);
                            String platilloNombre = platillo.getTextContent();
                            System.out.println("Platillo: " + platilloNombre);
                        }

                        System.out.println();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el contenido de texto de un elemento con el nombre de etiqueta especificado.
     *
     * @param element  El elemento del que se desea obtener el contenido de texto.
     * @param tagName  El nombre de la etiqueta del elemento cuyo contenido de texto se desea obtener.
     * @return El contenido de texto del elemento con el nombre de etiqueta especificado.
     */
    private static String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Element tagElement = (Element) nodeList.item(0);
        return tagElement.getTextContent();
    }
}


