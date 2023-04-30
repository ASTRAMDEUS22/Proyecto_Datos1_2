package MainClass;// Clase del servidor
import Admin_y_Usuario.ClientApp;
import Clases_auxiliares.Message;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread(new ManejadorClientes(clientSocket));
            t.start();
        }
    }
}

// Clase que maneja la comunicación con un cliente
class ManejadorClientes implements Runnable {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private BufferedReader in;

    public ManejadorClientes(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje del cliente " + clientSocket.getChannel() + ": " + inputLine);

                if (inputLine.equals("Hola")){
                    Message message = new Message("Nombre del metodo respondido por el servidor");
                    out.writeObject(message);
                } else if (inputLine.equals("Adios")) {
                    Message message = new Message("Salvatierra pura mierda");
                    out.writeObject(message);
                }

                //clientSocket.close();

                //out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase del cliente
//import java.net.*;
//import java.io.*;

class Client1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4444);
        PrintWriter enviarDatos = new PrintWriter(socket.getOutputStream(), true);
        ObjectInputStream recibirDatos = new ObjectInputStream(socket.getInputStream());

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;  //Entrada de texto del usuario

        Message mensajeServidor;



        while ((userInput = stdIn.readLine()) != null) {
            enviarDatos.println(userInput);
            mensajeServidor = (Message) recibirDatos.readObject();
            System.out.println("Mensaje from server: " + mensajeServidor.getNombreMetodo());  //Lee el mensaje del server

            if (mensajeServidor.getNombreMetodo().equals("Nombre del metodo respondido por el servidor")){

                System.out.println("helowis");
                ejecutarJuego();


            }

        }
    }

    public static void ejecutarJuego() throws IOException {
        System.out.println("Entra aqui");
        ClientApp clientApp = new ClientApp();
        clientApp.elementosGraficos();
    }


}

class Client2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4444);
        PrintWriter enviarDatos = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader recibirDatos = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            enviarDatos.println(userInput);
            System.out.println("Mensaje from server: " + recibirDatos.readLine());  //Lee el mensaje del server
        }
    }
}

