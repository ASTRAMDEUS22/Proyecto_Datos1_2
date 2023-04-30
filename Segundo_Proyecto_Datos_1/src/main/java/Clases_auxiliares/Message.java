package Clases_auxiliares;

import Clases_auxiliares.Platillo;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class Message implements Serializable {



    public Message(String nombreMetodo, String usuario, String password) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;
        this.password = password;
    }

    public Message(String nombreMetodo){
        this.nombreMetodo = nombreMetodo;
    }

    public Message(String nombreMetodo, ObservableList<Platillo> listaPlatillos){
        this.nombreMetodo = nombreMetodo;
        this.listaPlatillos = listaPlatillos;

    }

    //Definir atributos
    private String nombreMetodo,usuario,password;
    private int identificador = 2;
    private ObservableList<Platillo> listaPlatillos;



    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public ObservableList<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    /*public ObservableList<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    public void setListaPlatillos(ObservableList<Platillo> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }*/
}
