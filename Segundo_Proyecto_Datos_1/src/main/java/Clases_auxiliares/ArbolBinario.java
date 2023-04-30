package Clases_auxiliares;

public class ArbolBinario {

    Nodo raiz;

    public ArbolBinario(){

        this.raiz = null;  //Empieza sin ningún valor

    }

    public void insertarNodo(Nodo nodo){

        //Verifica si la raíz esta vacía
        if (this.raiz == null){
            this.raiz = nodo;
        }

        else {
            Nodo nodoRaiz = raiz;
            /*
            Se convierte los caracteres de forma que puedan ser leídos con un valor de jerarquía.

            Se evalua si el nuevo nodo es mayor a la raíz o menor a la raíz
             */

            char mensaje = 'i';

            while (true) {

                //System.out.println(Integer.parseInt(nodo.getValor().getUsername()));

                int result = nodo.getValor().getUsername().compareTo(nodoRaiz.getValor().getUsername());

                //Si el Nodo es mayor a la raíz, este irá a la derecha
                //if (Integer.parseInt(nodo.getValor().getUsername()) > Integer.parseInt(nodoRaiz.getValor().getUsername())) {
                if (result > 0){

                    //Si el Nodo a la derecha está vacío, asigne un nuevo valor y salga del ciclo
                    if (nodoRaiz.getDerecha() == null){

                        nodoRaiz.setDerecha(nodo);
                        break;
                    }

                    //Si el Nodo derecho no está vacío, baje a él y siga comparando.
                    nodoRaiz = nodoRaiz.getDerecha();

                }

                //Si el Nodo es menor a la raíz, este irá a la derecha
                else if (result < 0){

                    //Si el Nodo a la izquierda está vacío, asigne un nuevo valor y salga del ciclo
                    if (nodoRaiz.getIzquierda() == null){
                        nodoRaiz.setIzquierda(nodo);
                        break;
                    }

                    //Si el Nodo izquierdo no está vacío, baje a él y siga comparando.
                    nodoRaiz = nodoRaiz.getIzquierda();

                }

            }

        }

    }

    public boolean buscarPersona(String user,String contra){

        //Nodo auxiliar que recorrerá el árbol
        Nodo actual = raiz;



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

        //Si llega hasta acá es porque no encontró el usuario en los Nodos
        //return false;


    }




}
