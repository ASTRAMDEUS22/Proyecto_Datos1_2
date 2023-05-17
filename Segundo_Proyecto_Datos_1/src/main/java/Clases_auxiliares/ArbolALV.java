package Clases_auxiliares;

public class ArbolALV {

    //Ráiz del árbol
    AvlNodo raiz;

    //Lista enlazada para manejar de forma diferente la información
    ListaEnlazadaAVL listaEnlazada = new ListaEnlazadaAVL();

    /**
     * Devuelve la altura actual del nodo.
     * @param nodo Es el nodo del cual se desea conocer la altura.
     * @return Devuelve la altura del nodo
     */
    int altura(AvlNodo nodo) {
        //Si el nodo esta vacío, devuelve un valor neutro
        if (nodo == null)
            return 0;
        //Devuelve la altura del nodo
        return nodo.getAltura();
    }

    /**
     * Función que devuelve el valor máximo entre 2 números, es decir cuál de los 2 vale más.
     *
     * Este método es utilizado al momento de insertar un nuevo nodo en el árbol, para saber la altura que tendrá.
     *
     * @param nodo1 es un elemento de tipo int que será comparado con otro elemento para saber cuál es más grande
     * @param nodo2 es un elemento de tipo int que será comparado con otro elemento para saber cuál es más grande
     * @return Devuelve el valor más grande de los 2 elementos comparados.
     */
    private int maximo(int nodo1, int nodo2) {
        return Math.max(nodo1, nodo2);
    }

    /**
     * Se encarga de rotar el árbol a la derecha debido a un desequilibrio en el lado izquierdo del árbol al
     * insertar o eliminar un nodo.
     *
     * Rota a la derecha el árbol debido a un desequilibrio, el nodo que causa el desequilibrio pasa a ser el
     * hijo derecho de su hijo izquierdo, logrando emparejar el árbol derecho con el árbol izquierdo.
     *
     * En este caso, la variable "x" es el hijo izquierdo del nodo desequilibrado, la variable T2 es el hijo
     * derecho del hijo izquierdo del nodo desequilibrado.
     *
     *
     *                      y
     *                    /
     *                   x
     *                    \
     *                     T2
     *
     *         Resultado de la rotación:
     *
     *                 x
     *                  \
     *                   y
     *                  /
     *                 T2
     *
     * @param y Es el nodo que será rotado de posición.
     * @return se retorna el nodo x que es la nueva raíz del subárbol rotado.
     */
    private AvlNodo rotacionDerecha(AvlNodo y) {
        //Hijo izquierdo de y
        AvlNodo x = y.getIzquierdo();

        //Hijo derecho de x
        AvlNodo T2 = x.getDerecho();

        //Se realiza la rotación de los nodos
        x.setDerecho(y);
        y.setIzquierdo(T2);

        //Se actualiza la altura de los nodos
        y.setAltura(maximo(altura(y.getIzquierdo()), altura(y.getDerecho())) + 1);
        x.setAltura(maximo(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);

        return x;  //Nuevo nodo raíz del subárbol
    }

    /**
     * Se encarga de rotar el árbol a la izquierda debido a un desequilibrio en el lado derecho del árbol al
     * insertar o eliminar un nodo.
     *
     * Rota a la izquierda el árbol debido a un desequilibrio, el nodo que causa el desequilibrio pasa a ser el
     * hijo izquierdo de su hijo derecho, logrando emparejar el árbol izquierdo con el árbol derecho.
     *
     * En este caso, la variable "x" es el hijo derecho del nodo desequilibrado, la variable T2 es el hijo
     * izquierdo del hijo derecho del nodo desequilibrado.
     *
     *              nr
     *                \
     *                 x
     *                /
     *               T2
     *
     *        Resultado de la rotación:
     *
     *               x
     *              /
     *             nr
     *              \
     *               T2
     *
     *
     * @param nodoRotar Es el nodo que será rotado de posición.
     * @return se retorna x que será la nueva raíz del subárbol
     */
    private AvlNodo rotacionIzquierda(AvlNodo nodoRotar) {
        //Hijo derecho de nodoRotar
        AvlNodo x = nodoRotar.getDerecho();

        //Hijo izquierdo de x
        AvlNodo T2 = x.getIzquierdo();

        //Se realiza la rotación de los nodos
        x.setIzquierdo(nodoRotar);
        nodoRotar.setDerecho(T2);

        //Se actualiza la altura de los nodos
        nodoRotar.setAltura(maximo(altura(nodoRotar.getIzquierdo()), altura(nodoRotar.getDerecho())) + 1);
        x.setAltura(maximo(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);

        return x;  //Nuevo nodo raíz del subárbol
    }

    /**
     * Devuelve el valor de balance del nodo.
     *
     * Recibe un nodo y se pregunta si es nulo, caso contrario se devuelve el nivel de balanceo calculado a partir de
     * los nodos hijos.
     *
     * @param nodo Nodo al que se le determinará el FB.
     * @return devuelve el FB del nodo ingresado.
     */
    private int obtenerBalance(AvlNodo nodo) {
        if (nodo == null)
            return 0;
        return altura(nodo.getIzquierdo()) - altura(nodo.getDerecho());
    }

    /**
     * Metodo público que llama a un método recursivo que inserta un platillo en el árbolAVL
     * @param valor Platillo que será insertado en el árbol AVL
     */
    public void insertar(Platillo valor){

        this.raiz = insertarNodo(this.raiz,valor);

    }

    /**
     * Método recursivo que busca un nodo sin hijo e inserta en esa posición el nodo que almacenará un objeto
     * de tipo platillo.
     *
     * Guarda el valor de la raíz en el parámetro "nodo" y lleva guardado el elemento a insertar en el parámetro
     * platillo.
     *
     * Al preguntar si el nodo es null significa que llego a un nodo en el cual había un espacio vacío para insertar el
     * nuevo elemento por lo que procede a crear en esa posición un nodo y le asigna como valor el platillo.
     * Caso contrario, compara el valor de jerarquía del nombre de los platillos y va avanzando a la izquierda y derecha
     * del árbol de forma recursiva hasta encontrar un nodo vacío. Si se presentara el caso donde hay 2 elementos
     * iguales, rompe la iteración y devuelve el elemento repetido.
     *
     * Conforme va avanzando por los nodos, se va preguntando el FB de los nodos y se realizan las rotaciones
     * correspondientes.
     *
     * @param nodo Primero es la raíz del árbol, después conforme se va reiterando el método, será la raíz del subárbol
     * con un espacio disponible.
     * @param platillo Elemento a ser insertado en el árbol AVL
     * @return Devuelve y crea un nuevo nodo en el árbol con el valor del platillo
     *
     */
    private AvlNodo insertarNodo(AvlNodo nodo, Platillo platillo) {

        //Agrega el elemento al espacio disponible
        if (nodo == null){
            return this.raiz = new AvlNodo(platillo);
        }

        //Va avanzando a la izquierda buscando un espacio para el nuevo nodo
        if (platillo.getNombrePlatillo().compareTo(nodo.getPlatillo().getNombrePlatillo()) < 0) {
            nodo.setIzquierdo(insertarNodo(nodo.getIzquierdo(), platillo));
        }

        //Va avanzando a la derecha buscando un espacio para el nuevo nodo
        else if (platillo.getNombrePlatillo().compareTo(nodo.getPlatillo().getNombrePlatillo()) > 0) {
            nodo.setDerecho(insertarNodo(nodo.getDerecho(), platillo));
        }

        //Sale de la recursividad porque encontro otro elemento igual a él
        else {
            return nodo;
        }

        //Actualiza la altura del nodo
        nodo.setAltura(1 + maximo(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));

        //Obtiene el balance actual del nodo
        int balance = obtenerBalance(nodo);

        /*
        Si el balance es mayor a 1, o sea, desequilibrio a la izquierda, y pregunta si el elemento a insertar está
        en el subárbol izquierdo del hijo izquierdo del nodo, de ser así, aplica una rotación simple a la derecha
         */
        if (balance > 1 && platillo.getNombrePlatillo().compareTo(nodo.getIzquierdo().getPlatillo().getNombrePlatillo()) < 0) {
            return rotacionDerecha(nodo);
        }

        /*
        Si el balance es mayor a -1, o sea, desequilibrio a la derecha y pregunta si el elemento a insertar está
        en el subárbol derecho del hijo derecho del nodo, de ser así, aplica una rotación simple a la izquierda.
         */
        if (balance < -1 && platillo.getNombrePlatillo().compareTo(nodo.getDerecho().getPlatillo().getNombrePlatillo()) > 0) {
            return rotacionIzquierda(nodo);
        }

        /*
        Si el balance es mayor a 1, o sea, desequilibrio a la izquierda y si el elemento a insertar está en el
        subárbol derecho del hijo izquierdo del nodo, se hace una rotación doble, primero una rotación a la
        izquierda del hijo izquierdo del nodo y una rotación a la derecha del nodo.
         */
        if (balance > 1 && platillo.getNombrePlatillo().compareTo(nodo.getIzquierdo().getPlatillo().getNombrePlatillo()) > 0) {
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }

        /*
        Si el balance es menor a -1, o sea, desequilibrio a la derecha y si el elemento a insertar está en el
        subárbol izquierdo del hijo derecho del nodo, se realiza una rotación doble, primero una rotación a la
        derecha del hijo derecho del nodo, luego una rotación a la izquierda del nodo.
         */
        if (balance < -1 && platillo.getNombrePlatillo().compareTo(nodo.getDerecho().getPlatillo().getNombrePlatillo()) < 0) {
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    /**
     * Manda a llamar un método recursivo que busca un platillo en concreto y lo elimina del árbol.
     * Primero pregunta si el nodo es nulo, significando que llego al final del árbol y termina la recursión.
     *
     * @param valor es el objeto de tipo platillo que será eliminado
     */
    public void eliminar(Platillo valor) {
        this.raiz = eliminar(this.raiz, valor);
    }

    /**
     * Método recursivo que busca un Platillo en específico dentro del árbol y lo elimina para después hacer rotaciones.
     *
     * Pregunta por el valor de jerarquía del nombre de los platillos, esto con el fin de ir recorriendo el árbol por
     * la derecha o por la izquierda. En caso de encontrar al elemento, es decir, que ambos nombres de los platillos
     * sean idénticos, se pregunta si el nodo no tiene hijos o al menos tiene uno, la variable temp funciona a forma de
     * revisar los nodos hijos por si existe la posibilidad de que haya uno que no sea nulo.
     *
     * Caso donde no hay hijos: primero pregunta si el hijo izquierdo es nulo, en caso de ser así, a temp se le asigna
     * el valor del hijo derecho y se pregunta nuevamente si es nulo (tiene el valor del hijo derecho), esto con la
     * intención de ver que ambos hijos son nulos, en caso de que ambos resulten nulos, al nodo padre se le da un valor
     * de nulo.
     *
     * Caso en el que solo hay un hijo: Se pregunta si el izquierdo es nulo, si lo es, significa que el único hijo es
     * el derecho y su padre pasa a tener el valor de él, caso contrario, significa que en el hijo izquierdo si hay un
     * nodo, por ende, el padre pasa a tener el valor de él.
     *
     * @param nodo Es el nodo actual del árbol, empieza con el valor inicial de la raíz, conforme se va avanzando en el
     *             árbol buscando el elemento a eliminar, esta se va actualizando.
     * @param valor Es el objeto platillo a eliminar dentro del nodo, se utiliza el parámetro "nombrePlatillo" para
     *              recorrer el árbol en busca de coincidencias.
     * @return Devuelve el elemento eliminado del árbol.
     */
    private AvlNodo eliminar(AvlNodo nodo, Platillo valor) {
        //Pregunta si llego al final del árbol
        if (nodo == null) {
            return nodo;
        }

        int resultCompara = valor.getNombrePlatillo().compareTo(nodo.getPlatillo().getNombrePlatillo());

        //Si el valor es menor al valor del nodo, va para la izquierda.
        if (resultCompara < 0) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), valor));
        }

        //Si el valor es mayor al valor del nodo, va para la derecha.
        else if (resultCompara > 0) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), valor));
        }

        //En caso de que encuentre el elemento
        else {
            //Pregunta si alguno de los 2 es nulo
            if ((nodo.getIzquierdo() == null) || (nodo.getDerecho() == null)) {

                //Nodo temporal
                AvlNodo temp = null;

                //Pregunta si el izquierdo es nulo, si es así, adquiere el valor del hijo derecho
                if (temp == nodo.getIzquierdo()) {
                    temp = nodo.getDerecho();
                }
                //Caso contrario, adquiere el valor del hijo izquierdo
                else {
                    temp = nodo.getIzquierdo();
                }

                /*
                Si a pesar de las elecciones anteriores, cae en esta condicional, significa que ambos son nulos y se le
                asigna un valor de nulo al nodo
                 */
                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                    System.out.println("Nodo eliminado");
                }
                //Caso contrario, el hijo toma la posición del padre
                else {
                    nodo = temp;
                    System.out.println("Nodo eliminado");
                }
            }
            /*
            Si hay 2 hijos, se obtiene el nodo de menor valor y al nodo actual se le asigna el valor del más pequeño y
            se manda a eliminar
             */

            else {
                //Nodo con el elemento mas pequeño del subárbol derecho
                AvlNodo temp = nodoMasIzquierdo(nodo.getDerecho());

                //El nodo a eliminar almacena el valor del nodo más pequeño
                nodo.setPlatillo(temp.getPlatillo());

                //Se manda e eliminar el nodo temp del subárbol derecho
                nodo.setDerecho(eliminar(nodo.getDerecho(), temp.getPlatillo()));
            }
        }

        //Si el nodo queda con valor nulo, salir de la recursión.
        if (nodo == null) {
            return nodo;
        }

        //Actualiza la altura
        nodo.setAltura(1 + maximo(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));

        //Obtiene el FB del nodo
        int balance = obtenerBalance(nodo);

        /*
        Verifica si el FB es mayor a 1, o sea, desequilibrio a la izquierda y si el factor de balance del hijo izquierdo
        es mayor o igual a 0, se realiza una rotación simple a la derecha.
         */
        if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) >= 0) {
            return rotacionDerecha(nodo);
        }

        /*
        Verifica si el FB es mayor a 1, o sea, desequilibrio a la izquierda y si el factor de balance del hijo izquierdo
        es menor a 0, se realiza una rotación doble. Se procede una rotación simple a la izquierda y luego una rotación
        simple a la derecha en el nodo original.
         */
        if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) < 0) {
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }

        /*
        Verifica si el FB es menor a -1 (indicando un desequilibrio hacia la derecha) y
        si el factor de balance del hijo derecho es menor o igual a 0, se realiza una rotación simple a
        la izquierda.
         */
        if (balance < -1 && obtenerBalance(nodo.getDerecho()) <= 0) {
            return rotacionIzquierda(nodo);
        }

        /*
        Esta condición verifica si el FB es menor a -1, o sea, desequilibrio a la derecha y si el factor de balance del
        hijo derecho es mayor a 0, se realiza una rotación doble. Se hace una rotación simple a la derecha y luego
        una rotación simple a la izquierda en el nodo original.
         */
        if (balance < -1 && obtenerBalance(nodo.getDerecho()) > 0) {
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }

        //Si no entra en las anteriores es porque ya está balanceado.
        return nodo;
    }

    /**
     * Método que se encarga de recorrer el árbol hasta encontrar el elemento más pequeño del árbol.
     * @param nodo Es el árbol o subárbol del cuál se necesita saber su nodo más pequeño.
     * @return Devuelve el nodo más pequeño del árbol.
     */
    private AvlNodo nodoMasIzquierdo(AvlNodo nodo) {
        //Variable que guarda el valor del elemento más pequeño
        AvlNodo actual = nodo;

        //Mientras no encuentre al último elemento a la izquierda, ejecute.
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }

        return actual;
    }

    public void crearListaPlatillos(){

        recursiAgregarPlat(this.raiz);

    }

    public void recursiAgregarPlat(AvlNodo current){

        if (current == null){
            return;
        }

        listaEnlazada.insertarNuevoNodo(current);

        recursiAgregarPlat(current.getDerecho());
        recursiAgregarPlat(current.getIzquierdo());

    }

    /**
     * Método recursivo que recorre el árbol mostrando cada elemento.
     * @param nodo Es la raíz del árbol o subárbol del cuál se desea conocer sus nodos.
     */
    public void preorden(AvlNodo nodo) {
        if (nodo != null) {
            System.out.println(nodo.getPlatillo().getNombrePlatillo() + " ");
            preorden(nodo.getIzquierdo());
            preorden(nodo.getDerecho());
        }
    }

    public ListaEnlazadaAVL getListaEnlazada() {
        return listaEnlazada;
    }


    /**
     * Devuelve la raíz del árbol.
     * @return Raíz del árbol.
     */
    public AvlNodo getRaiz() {
        return raiz;
    }
}