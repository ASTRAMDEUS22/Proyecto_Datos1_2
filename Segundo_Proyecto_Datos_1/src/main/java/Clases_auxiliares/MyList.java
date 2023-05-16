package Clases_auxiliares;

/**
 * Esta clase representa una lista genérica.
 * Permite almacenar elementos de cualquier tipo.
 *
 * @param <T> El tipo de elementos que se almacenarán en la lista.
 */
public class MyList<T> {

    private Object[] elements;
    private int size;

    private T[] array;

    private static final int Tamano = 7;

    /**
     * Constructor de la clase MyList.
     * Inicializa una lista vacía con capacidad de 7 elementos.
     */
    public MyList() {
        array = (T[]) new Object[Tamano];
        size = 0;
    }

    /**
     * Agrega un elemento a la lista.
     * Si la lista alcanza su capacidad máxima, se duplica su tamaño.
     *
     * @param item El elemento a agregar.
     */
    public void add(T item) {
        if (size == array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = item;
    }

    /**
     * Obtiene el tamaño actual de la lista.
     *
     * @return El tamaño de la lista.
     */
    public int size() {
        return size;
    }

    /**
     * Obtiene el elemento en el índice especificado de la lista.
     *
     * @param index El índice del elemento a obtener.
     * @return El elemento en el índice especificado.
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
     */
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }
}




