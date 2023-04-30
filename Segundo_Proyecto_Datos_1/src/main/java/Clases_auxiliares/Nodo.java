package Clases_auxiliares;

public class Nodo {

    private Usuario valor;
    private Nodo derecha;
    private Nodo izquierda;

    /**
     * Constructor del objeto que almacenará usuarios
     */
    public Nodo(){
        this.derecha = null;  //Empieza sin apuntarle a alguien
        this.izquierda = null;
    }

    public Nodo getDerecha() {
        return derecha;
    }

    public void setDerecha(Nodo derecha) {
        this.derecha = derecha;
    }

    public Nodo getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Nodo izquierda) {
        this.izquierda = izquierda;
    }

    public Usuario getValor() {
        return valor;
    }

    public void setValor(Usuario valor) {
        this.valor = valor;
    }
}
