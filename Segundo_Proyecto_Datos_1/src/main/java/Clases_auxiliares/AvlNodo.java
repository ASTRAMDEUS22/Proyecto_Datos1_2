package Clases_auxiliares;

import java.io.Serializable;

public class AvlNodo implements Serializable {

    private Platillo platillo;
    private int altura;
    private AvlNodo derecho,izquierdo;
    private AvlNodo next;

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


    public AvlNodo getNext() {
        return next;
    }

    public void setNext(AvlNodo next) {
        this.next = next;
    }
}
