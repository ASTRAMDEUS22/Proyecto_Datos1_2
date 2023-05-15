package Clases_auxiliares;

public class AvlNodo {

    private Platillo platillo;
    private int altura;
    private AvlNodo derecho,izquierdo;

    public AvlNodo(Platillo platillo){
        this.platillo = platillo;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public AvlNodo getDerecho() {
        return derecho;
    }

    public void setDerecho(AvlNodo derecho) {
        this.derecho = derecho;
    }

    public AvlNodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(AvlNodo izquierdo) {
        this.izquierdo = izquierdo;
    }
}
