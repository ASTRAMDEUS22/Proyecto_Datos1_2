package Clases_auxiliares;

import java.io.Serializable;

/**
 * Esta clase representa un mensaje utilizado para comunicarse entre componentes.
 * Puede contener diferentes atributos dependiendo del contexto.
 */
public class Message implements Serializable {

    //Definir atributos
    private String nombreMetodo,usuario,password,newUsuario,newPassword;
    private Nodo nodo;
    private ListaEnlazada listaEnlazada;
    private ListaEnlazadaAVL listaEnlazadaAVL;
    private int time;

    public Message(){

    }

    /**
     * Constructor de Message utilizado cuando se requieren el nombre del método, usuario y contraseña.
     *
     * @param nombreMetodo El nombre del método.
     * @param usuario      El nombre de usuario.
     * @param password     La contraseña.
     */
    public Message(String nombreMetodo, String usuario, String password) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Constructor de Message utilizado cuando se requiere solo el nombre del método.
     *
     * @param nombreMetodo El nombre del método.
     */
    public Message(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    /**
     * Constructor de Message utilizado cuando se requieren el nombre del método y el usuario.
     *
     * @param nombreMetodo El nombre del método.
     * @param usuario      El nombre de usuario.
     */
    public Message(String nombreMetodo, String usuario) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;
    }

    /**
     * Constructor de Message utilizado cuando se requieren el nombre del método, usuario, nuevo usuario y nueva contraseña.
     *
     * @param nombreMetodo El nombre del método.
     * @param user         El usuario a editar.
     * @param newUser      El nuevo valor para el nombre de usuario.
     * @param newPassword  La nueva contraseña.
     */
    public Message(String nombreMetodo, String user, String newUser, String newPassword) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = user;
        this.newUsuario = newUser;
        this.newPassword = newPassword;
    }

    /**
     * Obtiene el nombre del método.
     *
     * @return El nombre del método.
     */
    public String getNombreMetodo() {
        return nombreMetodo;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Obtiene la contraseña.
     *
     * @return La contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el nodo asociado al mensaje.
     *
     * @return El nodo asociado.
     */
    public Nodo getNodo() {
        return nodo;
    }

    /**
     * Obtiene la lista enlazada asociada al mensaje.
     *
     * @return La lista enlazada asociada.
     */
    public ListaEnlazada getListaEnlazada() {
        return listaEnlazada;
    }

    /**
     * Establece la lista enlazada asociada al mensaje.
     *
     * @param listaEnlazada La lista enlazada a establecer.
     */
    public void setListaEnlazada(ListaEnlazada listaEnlazada) {
        this.listaEnlazada = listaEnlazada;
    }


    public ListaEnlazadaAVL getListaEnlazadaAVL() {
        return listaEnlazadaAVL;
    }

    public void setListaEnlazadaAVL(ListaEnlazadaAVL listaEnlazadaAVL) {
        this.listaEnlazadaAVL = listaEnlazadaAVL;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Obti
     ene el nuevo nombre de usuario.
     *
     * @return El nuevo nombre de usuario.
     */
    public String getNewUsuario() {
        return newUsuario;
    }

    /**
     * Obtiene la nueva contraseña.
     *
     * @return La nueva contraseña.
     */
    public String getNewPassword() {
        return newPassword;
    }
}
