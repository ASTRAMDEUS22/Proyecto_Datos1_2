package Clases_auxiliares;

public class AVLNode {

    private int height;
    private int balanceFactor;
    private int value;
    private AVLNode left;
    private AVLNode right;

    public AVLNode(int value) {
        this.value = value;
        this.height = 1;
        this.balanceFactor = 0;
        this.left = null;
        this.right = null;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) { //cambiar a Usuario value cuando sirva
        this.value = value;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

}