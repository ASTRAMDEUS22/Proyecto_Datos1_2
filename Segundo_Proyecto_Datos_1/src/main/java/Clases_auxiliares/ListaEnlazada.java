package Clases_auxiliares;

import java.io.Serializable;

/**
 * Esta clase representa una lista enlazada.
 */
public class ListaEnlazada implements Serializable {

    private Nodo head;
    private Nodo last;
    private int size;

    /**
     * Constructor de la clase ListaEnlazada.
     * Inicializa una lista enlazada vacía.
     */
    public ListaEnlazada() {
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Inserta un nuevo nodo al final de la lista.
     *
     * @param nuevo_nodo El nodo a insertar.
     */
    public void insertarNuevoNodo(Nodo nuevo_nodo) {
        if (this.head == null) {
            // Si la lista está vacía, el nuevo nodo se convierte en la cabeza y en el último elemento.
            this.head = nuevo_nodo;
            this.last = nuevo_nodo;
            this.size++;
        } else {
            // El nuevo nodo se enlaza con el último nodo existente y se convierte en el nuevo último elemento.
            this.last.setNext(nuevo_nodo);
            this.last = nuevo_nodo;
            this.size++;
        }
    }

    /**
     * Verifica si un elemento dado está presente en la lista.
     *
     * @param user El elemento a buscar.
     * @return true si el elemento está presente, false de lo contrario.
     */
    public boolean devolverElemento(String user) {
        Nodo current = this.head;
        while (current != null) {
            if (current.getValor().getUsername().equals(user)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Elimina un nodo con el elemento dado de la lista.
     *
     * @param user El elemento a eliminar.
     */
    public void eliminarNodo(String user) {
        Nodo current = this.head;
        Nodo refAnterior = this.head;

        // Si el Nodo a eliminar es el head
        if (current.getValor().getUsername().equals(user)) {
            this.head = current.getNext();
        }

        while (current != null) {
            if (current.getValor().getUsername().equals(user)) {
                refAnterior.setNext(current.getNext());
                current.setNext(null);  // Se elimina el apuntador
                return;
            }
            refAnterior = current;
            current = current.getNext();
        }

        System.out.println("No se encontró a la persona");
    }

    /**
     * Edita el elemento de un nodo dado en la lista.
     *
     * @param user        El elemento a editar.
     * @param newUser     El nuevo valor para el nombre de usuario.
     * @param newPassword La nueva contraseña.
     */
    public void editarElemento(String user, String newUser, String newPassword) {
        Nodo current = this.head;
        while (current != null) {
            if (current.getValor().getUsername().equals(user)) {
                current.getValor().setUsername(newUser);
                current.getValor().setPassword(newPassword);
                System.out.println("Se encontró el Nodo en la lista enlazada");
                return;
            }
            current = current.getNext();
        }
        System.out.println("No se encontró a la persona");
    }

    /**
     * Muestra los elementos de la lista enlazada.
     */

    public void verElementos(){

        Nodo current = this.head;

        while (current != null){
            System.out.println(current.getValor().getUsername());

            current = current.getNext();

        }
    }


    public int getSize() {
        return size;
    }

    public Nodo getHead() {
        return head;
    }
}
