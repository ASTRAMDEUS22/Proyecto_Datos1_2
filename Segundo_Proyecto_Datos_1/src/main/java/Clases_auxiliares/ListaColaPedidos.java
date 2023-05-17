package Clases_auxiliares;

public class ListaColaPedidos {

    private Nodo head;
    private Nodo last;
    private int size;

    public void insertar(Nodo nuevoNodo){

        if (head == null){

            this.head = nuevoNodo;
            this.last = nuevoNodo;

        }else {

            this.last.setNext(nuevoNodo);
            this.last = nuevoNodo;
            this.size++;

        }

        this.size++;


    }


    /**
     * Este método se encarga de hacer la cola de pedidos, y va eliminando el primer elemento conforme se va llamando
     */
    public void eliminarPrimero(){

        this.head = this.head.getNext();

        this.size--;

    }

    public Nodo getHead(){

        return this.head;

    }

    public void mostrarElementos(){

        Nodo current = this.head;

        while (current!= null){

            current.getListaEnlazadaAVL().mostrarElementos();

            current = current.getNext();

        }
    }

    public ListaEnlazadaAVL getListaNodo(){

        Nodo current = this.head;

        return current.getListaEnlazadaAVL();

    }

    public int getSize() {
        return size;
    }
}
