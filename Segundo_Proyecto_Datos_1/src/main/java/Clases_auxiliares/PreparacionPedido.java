package Clases_auxiliares;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.io.FileDescriptor.out;

public class PreparacionPedido implements Runnable{


    private ListaColaPedidos colaPedidos = new ListaColaPedidos();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void agregarNuevoPedido(ListaEnlazadaAVL pedido, Socket socket){
        System.out.println("Se añade nuevo pedido");
        Nodo nodo = new Nodo();
        nodo.setListaEnlazadaAVL(pedido);
        colaPedidos.insertar(nodo);
        colaPedidos.mostrarElementos();

        System.out.println("Añade pedido, nuevo tamaño: " + colaPedidos.getSize());

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        run();


    }

    @Override
    public void run() {

        //Permite que el hilo se ejecute sin bloquear la ejecución de otros métodos en el mismo hilo.
        Future<?> future = executorService.submit(() -> {

            //Mientras la cola de pedidos tenga elementos, ejecute




            while (colaPedidos.getHead() != null) {


                //Muetra las listas con pedidos en la cola
                colaPedidos.mostrarElementos();



                System.out.println("-----------------------PEDIDO-------------------------");

                //Mientras la lista AVL tenga platos, ejecute
                while (colaPedidos.getListaNodo().getHead() != null) {



                    //Tiempo del plato actual
                    int time = colaPedidos.getListaNodo().getHead().getPlatillo().getTiempo();
                    String nombrePlato = colaPedidos.getListaNodo().getHead().getPlatillo().getNombrePlatillo();

                    //Contador que termina de ejecutar al llegar a un número negativo
                    while (time > -1) {

                        System.out.println("Platillo " + nombrePlato + ": " + time);

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
                System.out.println("Elimina pedido, tamaño resultante: " + colaPedidos.getSize());

            }
        });
    }


}
