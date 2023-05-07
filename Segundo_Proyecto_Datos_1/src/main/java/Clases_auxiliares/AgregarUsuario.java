package Clases_auxiliares;

import com.dlsc.formsfx.model.structure.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;



public class AgregarUsuario {
    private static void agregarUsuarioAXML(String username, String password) {
        try {
            File file = new File("C:\\Users\\XPC\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\.idea\\Users.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = (Element) document.getDocumentElement();

            Element user = (Element) document.createElement("user");
            Element usernameElement = (Element) document.createElement("username");
            Element passwordElement = (Element) document.createElement("password");

            usernameElement.appendChild(document.createTextNode(username));
            passwordElement.appendChild(document.createTextNode(password));

            user.appendChild(usernameElement);
            user.appendChild(passwordElement);

            root.appendChild(user);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("Usuario agregado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
