package Clases_auxiliares;

import java.io.Serializable;

/**
 * Esta clase representa La informacion de los usuarios.
 * Contiene información sobre el nombre de usuario y la contraseña.
 */
public class Usuario implements Serializable {
    // Atributos
    private String username;
    private String password;

    /**
     * Constructor de la clase Usuario.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     */
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
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
     * Establece el nombre de usuario.
     *
     * @param username El nombre de usuario a establecer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Establece la contraseña.
     *
     * @param password La contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve una representación en forma de cadena del usuario.
     *
     * @return El nombre de usuario.
     */
    @Override
    public String toString() {
        return username;
    }
}

