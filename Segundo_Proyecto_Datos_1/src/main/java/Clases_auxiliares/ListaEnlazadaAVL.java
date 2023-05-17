package Clases_auxiliares;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class ListaEnlazadaAVL implements Serializable {

    private AvlNodo head;
    private AvlNodo last;
    private int size;

    /**
     * Constructor de la clase Lista Enlazada
     */
    public ListaEnlazadaAVL(){
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    public void insertarNuevoNodo(AvlNodo nuevo_nodo){

        //Si la cabeza de la lista esta vacía, se asigna un nuevo valor
        if (this.head == null){

            this.head = nuevo_nodo;
            this.last = nuevo_nodo;
            this.size++;

        }
        else{

            //El nuevo objeto ingresado pasa a ser apuntando por el último
            this.last.setNext(nuevo_nodo);

            //Se asigna un nuevo valor final
            this.last = nuevo_nodo;

            //Se incrementa el tamaño de la lista
            this.size++;

        }

    }

    public void mostrarElementos(){

        AvlNodo current = this.head;

        while (current != null){

            System.out.print("Platillo: " + current.getPlatillo().getNombrePlatillo() + "   ");

            current = current.getNext();

        }

    }

    public void eliminarPrimero(){

        if (this.head == null){
            return;
        }else {
            this.head = this.head.getNext();
        }

    }


    public void eliminarNodo(AvlNodo avlNodo){

        AvlNodo current = this.head;
        AvlNodo refAnterior = this.head;

        //Si el Nodo a eliminar es el head
        if (current.getPlatillo().getNombrePlatillo().equals(avlNodo.getPlatillo().getNombrePlatillo())){
            this.head = current.getNext();
        }

        while (current != null){

            if (current.getPlatillo().getNombrePlatillo().equals(avlNodo.getPlatillo().getNombrePlatillo())){

                refAnterior.setNext(current.getNext());
                current.setNext(null);  //Se elimina el apuntador
                return;
            }

            refAnterior = current;
            current = current.getNext();

        }

        System.out.println("No se encontro a la persona");


    }

    public AvlNodo getLast(){
        return this.last;
    }

    public void verElementos(){

        AvlNodo current = this.head;

        while (current != null){
            System.out.println(current.getPlatillo().getNombrePlatillo());

            current = current.getNext();

        }
    }

    public int calcularTiempoPreparacion(){

        AvlNodo current = this.head;

        int tiempo = 0;

        while (current != null){

            tiempo += current.getPlatillo().getTiempo();

            current = current.getNext();

        }
        return tiempo;
    }

    public int getSize() {
        return size;
    }

    public AvlNodo getHead() {
        return head;
    }
}
