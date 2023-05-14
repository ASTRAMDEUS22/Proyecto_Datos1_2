package Clases_auxiliares;

/**
 * Árbol binario de busqueda encargado de manejar la información con respecto a los usuarios administradores y clientes.
 * Árbol binario que almacena nodos que a su vez almacenan objetos de tipo usuario con la finalidad de guardar la
 * información de los distintos usuarios escritos en un XML, agrega nuevos nodos buscando el extremo vacío viajando de
 * derecha a izquierda según un orden jerárquico de los caracteres de texto ingresados.
 */
public class ArbolBinario {

    Nodo root;

    ListaEnlazada listaEnlazada = new ListaEnlazada();

    /**
     * Constructor del árbol binario, se inicia la raíz en null
     */
    public ArbolBinario(){

        this.root = null;  //Empieza sin ningún valor

    }

    /**
     * Método que inserta un nuevo nodo en una de las hojas del árbol.
     * Este método inserta un nuevo nodo en alguna hoja del árbol yendo de derecha a izquierda haciendo comparaciones
     * hasta encontrar un nodo que apunta a nulo, el nodo a añadir será agregado al árbol en ese espacio nulo que le
     * corresponde.
     * @param nuevoNodo Objeto de tipo nodo que será insertado en el árbol binario de búsqueda mediante una comparación
     * entre el valor que almacena el usuario que contiene y el valor que almacena el árbol binario.
     */
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
                        return;
                    }
                    nodoActual = nodoActual.getIzquierda();
                } else {
                    if (nodoActual.getDerecha() == null) {
                        nodoActual.setDerecha(nuevoNodo);
                        return;
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

    public void eliminarNodo(String user){
        eliminarPersona(this.root,user);
    }
    
    //Referencia: https://parzibyte.me/blog/2023/01/18/java-eliminar-nodo-arbol-binario-abb/
    private Nodo eliminarPersona(Nodo nodo, String user) {

        if (nodo == null) {
            return null;
        }
        
        int valor = nodo.getValor().getUsername().compareTo(user);
        
        if (valor > 0) {
            nodo.setDerecha(this.eliminarPersona(nodo.getDerecha(), user));
        } else if (valor < 0) {
            nodo.setIzquierda(this.eliminarPersona(nodo.getIzquierda(), user));
        } else {
            if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
                nodo = null;
            } else if (nodo.getDerecha() != null) {
                nodo.setValor(this.sucesor(nodo));
                nodo.setDerecha(this.eliminarPersona(nodo.getDerecha(), user));
            } else {
                nodo.setValor(this.predecesor(nodo));
                nodo.setIzquierda(this.eliminarPersona(nodo.getIzquierda(), user));
            }
        }
        return nodo;
    }

    private Usuario sucesor(Nodo nodo) {
        nodo = nodo.getDerecha();
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        return nodo.getValor();
    }

    private Usuario predecesor(Nodo nodo) {
        nodo = nodo.getIzquierda();
        while (nodo.getDerecha() != null) {
            nodo = nodo.getDerecha();
        }
        return nodo.getValor();
    }

    /*public void eliminarNodo(String key) {
        Nodo current = root;
        Nodo parent = root;
        boolean isLeftChild = true;


        Ejecuta mientras el current no sea nulo y el elemento buscado no coincida con algún Nodo

        while (current != null && !current.getValor().getUsername().equals(key)) {
            parent = current;

            //System.out.println("Current: " + current.getValor().getUsername() + " " + "key: " + key);

            if (key.compareTo(current.getValor().getUsername()) < 0) {
                isLeftChild = true;
                current = current.getIzquierda();
            } else {
                isLeftChild = false;
                current = current.getDerecha();
            }
        }

        System.out.println("El nodo encontrado en el árbol a eliminar es: " + current.getValor().getUsername());


        Pregunta si es un Nodo hoja

        if (current.getIzquierda() == null && current.getDerecha() == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.setIzquierda(null);
            } else {
                parent.setDerecha(null);
            }
        }

        Pregunta si solo tiene un hijo a la derecha

        else if (current.getIzquierda() == null) {
            if (current == root) {
                root = current.getDerecha();
            } else if (isLeftChild) {
                parent.setIzquierda(current.getDerecha());
            } else {
                parent.setDerecha(current.getDerecha());
            }
        }

        Pregunta si solo tiene un hijo a la izquierda

        else if (current.getDerecha() == null) {
            if (current == root) {
                root = current.getIzquierda();
            } else if (isLeftChild) {
                parent.setIzquierda(current.getIzquierda());
            } else {
                parent.setDerecha(current.getIzquierda());
            }
        }

        Caso donde tiene 2 Nodos hijos

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
    }*/


    public void crearListaUsers(){

        recursiAgregaUsers(this.root);

    }


    private void recursiAgregaUsers(Nodo current){

        if (current == null){
            return;
        }

        //personas.add(current.getValor());
        listaEnlazada.insertarNuevoNodo(current);
        System.out.println("Funcion agregarUsers: " + current.getValor().getUsername());

        recursiAgregaUsers(current.getDerecha());
        recursiAgregaUsers(current.getIzquierda());

    }

    public ListaEnlazada getListaEnlazada() {
        return listaEnlazada;
    }



}
