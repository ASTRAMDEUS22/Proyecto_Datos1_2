package sample.segundoproyectodatos1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Usuarios {
    /*public void obtenerUsuarios() {
        List<Usuario> usuarios = cargarUsuariosDesdeXML();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa tu nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Ingresa tu contraseña: ");
        String password = scanner.nextLine();

        Usuario usuarioAutenticado = autenticarUsuario(usuarios, username, password);
        if (usuarioAutenticado != null) {
            System.out.println("Inicio de sesión exitoso para el usuario " + usuarioAutenticado.getUsername() + ".");
        } else {
            System.out.println("El nombre de usuario o la contraseña son incorrectos.");
        }
    }*/

    public List<Usuario> cargarUsuariosDesdeXML() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\src\\main\\java\\Archivos_XML");

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
        return usuarios;
    }

    /*private static Usuario autenticarUsuario(List<Usuario> usuarios, String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }*/
}

class Usuario {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", password=" + password + '}';
    }
}







