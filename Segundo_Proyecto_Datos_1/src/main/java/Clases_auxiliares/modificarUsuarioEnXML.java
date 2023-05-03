package Clases_auxiliares;

import com.dlsc.formsfx.model.structure.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import static javax.script.ScriptEngine.FILENAME;

public class modificarUsuarioEnXML {


/*    private static void modificarUsuarioEnXML(String username, String newUsername, String newPassword) {
        try {
            File file = new File(FILENAME);
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

    private static void eliminarUsuarioDeXML(String username) {
        List<Usuario> usuarios = cargarUsuariosDesdeXML();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        Usuario usuarioAEliminar = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                usuarioAEliminar = usuario;
                break;
            }
        }

        if (usuarioAEliminar == null) {
            System.out.println("No se encontró el usuario " + username + ".");
            return;
        }

        try {
            File file = new File(FILENAME);
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);

            System.out.println("Usuario eliminado correctamente.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerConfigurationException |
                 TransformerException e) {
            e.printStackTrace();
        }
    }
*/

}
