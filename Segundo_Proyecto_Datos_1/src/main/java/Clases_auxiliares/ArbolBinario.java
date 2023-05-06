package Clases_auxiliares;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.UUID;

public class ArbolBinario {

    Nodo root;

    ListaEnlazada listaEnlazada = new ListaEnlazada();

    //ListView<Usuario> listView = new ListView<>(personas);

    public ArbolBinario(){

        this.root = null;  //Empieza sin ningún valor

    }

    public void insertar(Nodo nuevoNodo) {

        if (this.root == null) {
            this.root = nuevoNodo;
        } else {
            Nodo nodoActual = this.root;
            while (true) {

                int valor = nuevoNodo.getValor().getUsername().compareTo(nodoActual.getValor().getUsername());

                //Si el nuevo Nodo es menor al Nodo actual, va para la izquierda, si no, para la derecha
                if (valor < 0) {
                    if (nodoActual.getIzquierda() == null) {
                        nodoActual.setIzquierda(nuevoNodo);
                        break;
                    }
                    nodoActual = nodoActual.getIzquierda();
                } else {
                    if (nodoActual.getDerecha() == null) {
                        nodoActual.setDerecha(nuevoNodo);
                        break;
                    }
                    nodoActual = nodoActual.getDerecha();
                }
            }
        }
    }

    public boolean revisarLogin(String user, String contra){

        //Nodo auxiliar que recorrerá el árbol
        Nodo actual = root;

        //Si el usuario ingresado es igual al usuario guardado en algún nodo
        while (true) {
            //Compara repetidas veces el valor de actual y user para saber si debe ir por los subarboles de la derecha o izquierda
            int result = actual.getValor().getUsername().compareTo(user);

            System.out.println("Nodo actual: " + actual.getValor().getUsername());

            if (actual.getValor().getUsername().equals(user)) {
                if (actual.getValor().getPassword().equals(contra)) {
                    return true;
                }else {
                    return false;
                }
            } else if (result < 0) {
                System.out.println("Derecha");
                if (actual.getDerecha() == null){
                    return false;
                }
                actual = actual.getDerecha();
            } else {
                System.out.println("Izquierda");
                if (actual.getIzquierda() == null){
                    return false;
                }
                actual = actual.getIzquierda();
            }
        }

    }

    public void modificarPersona(String user,String newUser,String newPassword){
        //Nodo auxiliar que recorrerá el árbol
        Nodo actual = root;

        //Si el usuario ingresado es igual al usuario guardado en algún nodo
        while (true) {
            //Compara repetidas veces el valor de actual y user para saber si debe ir por los subarboles de la derecha o izquierda
            int result = actual.getValor().getUsername().compareTo(user);

            System.out.println("Nodo actual: " + actual.getValor().getUsername());

            if (actual.getValor().getUsername().equals(user)) {

                actual.getValor().setUsername(newUser);
                actual.getValor().setPassword(newPassword);
                return;

            } else if (result < 0) {
                System.out.println("Derecha");
                if (actual.getDerecha() == null){
                    System.out.println("No se encontro yendo a la derecha");
                    return;
                }
                actual = actual.getDerecha();
            } else {
                System.out.println("Izquierda");
                if (actual.getIzquierda() == null){
                    System.out.println("No se encontró yendo a la izquierda");
                    return;
                }
                actual = actual.getIzquierda();
            }
        }
    }

    public void eliminarNodo(String key) {
        Nodo current = root;
        Nodo parent = root;
        boolean isLeftChild = true;

        /*
        Ejecuta mientras el current no sea nulo y el elemento buscado no coincida con algún Nodo
         */
        while (current != null && !current.getValor().getUsername().equals(key)) {
            parent = current;

            if (key.compareTo(current.getValor().getUsername()) < 0) {
                isLeftChild = true;
                current = current.getIzquierda();
            } else {
                isLeftChild = false;
                current = current.getDerecha();
            }
        }

        if (current == null) {
            return;
        }

        /*
        Pregunta si es un Nodo hoja
         */
        if (current.getIzquierda() == null && current.getDerecha() == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.setIzquierda(null);
            } else {
                parent.setDerecha(null);
            }
        }
        /*
        Pregunta si solo tiene un hijo a la derecha
         */
        else if (current.getIzquierda() == null) {
            if (current == root) {
                root = current.getDerecha();
            } else if (isLeftChild) {
                parent.setIzquierda(current.getDerecha());
            } else {
                parent.setDerecha(current.getDerecha());
            }
        }
        /*
        Pregunta si solo tiene un hijo a la izquierda
         */
        else if (current.getDerecha() == null) {
            if (current == root) {
                root = current.getIzquierda();
            } else if (isLeftChild) {
                parent.setIzquierda(current.getIzquierda());
            } else {
                parent.setDerecha(current.getIzquierda());
            }
        }
        /*
        Caso donde tiene 2 Nodos hijos
         */
        else {
            Nodo successor = getSuccessor(current);

            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setIzquierda(successor);
            } else {
                parent.setDerecha(successor);
            }

            successor.setIzquierda(current.getIzquierda());
        }

    }

    private Nodo getSuccessor(Nodo node) {
        Nodo successorParent = node;
        Nodo successor = node;
        Nodo current = node.getDerecha();

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getIzquierda();
        }

        if (successor != node.getDerecha()) {
            successorParent.setIzquierda(successor.getDerecha());
            successor.setDerecha(node.getDerecha());
        }

        return successor;
    }

    /**
     * Método que se encarga de crear una recursión para añadirle a una ObservableList los users del árbol binario, se
     * llama al método recursivo con un nodo inicial que es la raíz
     * @return ObservableList de users
     */
    public void crearListaUsers(){

        recursiAgregaUsers(this.root);



    }

    /**
     * Metodo recursivo que se encarga de agregar a la ObservableList todos los nodos del árbol binario, preguntando si
     * el nodo actual es nulo, indicando que llego al final de alguno de los subárboles, deteniendo la recursividad,
     * en caso contrario, añada el valor del nodo actual a la lista y baje a los siguientes nodos
     * @param current
     */
    private void recursiAgregaUsers(Nodo current){

        if (current == null){
            return;
        }

        //personas.add(current.getValor());
        listaEnlazada.insertarNuevoNodo(current);

        recursiAgregaUsers(current.getDerecha());
        recursiAgregaUsers(current.getIzquierda());

    }

    public ListaEnlazada getListaEnlazada() {
        return listaEnlazada;
    }

    /*public Nodo buscarPadre(Nodo nodo){

        Nodo current = this.root;

        while (true){

            int valor = current.getValor().getUsername().compareTo(nodo.getValor().getUsername());

            //Se pregunta si es la raíz
            if (current.getValor().getUsername().equals(this.root.getValor().getUsername())){
                return null;  //Devuelve que no hay padre ya que es la raíz
            }

            //Preguntamos si alguno de los Nodos hijos es el que estamos buscando
            if (current.getValor().getUsername().equals(current.getDerecha().getValor().getUsername())){
                return current;  //Devuelve el padre
            }
            else if (current.getValor().getUsername().equals(current.getIzquierda().getValor().getUsername())) {
                return current;  //Devuelve el padre
            }

            if (valor < 0){
                current = current.getDerecha();  //Avanza a la derecha

            }else {
                current = current.getIzquierda();  //Avanza a la izquierda
            }


        }


    }*/

}
