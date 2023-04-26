package sample.segundoproyectodatos1;

import java.io.Serializable;

public class Message implements Serializable {

    public Message(String nombreMetodo,String usuario,String password) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;
        this.password = password;


    }
    private String nombreMetodo,usuario,password;


    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
