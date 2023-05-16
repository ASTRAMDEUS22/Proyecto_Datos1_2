package Clases_auxiliares;

/**
 * Clase que representa los platillos.
 * Contiene información sobre el nombre del platillo, las calorías, el precio y el tiempo de preparación.
 */
public class Platillo {

    // Atributos
    private String nombrePlatillo;
    private int calorias;
    private int precio;
    private float tiempo;

    /**
     * Constructor de la clase Platillo.
     *
     * @param nombrePlatillo El nombre del platillo.
     * @param calorias       Las calorías del platillo.
     * @param precio         El precio del platillo.
     * @param tiempo         El tiempo de preparación del platillo.
     */
    public Platillo(String nombrePlatillo, int calorias, int precio, float tiempo) {
        this.nombrePlatillo = nombrePlatillo;
        this.calorias = calorias;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    /**
     * Devuelve una representación en forma de cadena del platillo.
     *
     * @return El nombre del platillo.
     */
    @Override
    public String toString() {
        return nombrePlatillo;
    }

    /**
     * Obtiene el nombre del platillo.
     *
     * @return El nombre del platillo.
     */
    public String getNombrePlatillo() {
        return nombrePlatillo;
    }

    /**
     * Establece el nombre del platillo.
     *
     * @param nombrePlatillo El nombre del platillo a establecer.
     */
    public void setNombrePlatillo(String nombrePlatillo) {
        this.nombrePlatillo = nombrePlatillo;
    }

    /**
     * Obtiene las calorías del platillo.
     *
     * @return Las calorías del platillo.
     */
    public int getCalorias() {
        return calorias;
    }

    /**
     * Establece las calorías del platillo.
     *
     * @param calorias Las calorías del platillo a establecer.
     */
    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    /**
     * Obtiene el precio del platillo.
     *
     * @return El precio del platillo.
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del platillo.
     *
     * @param precio El precio del platillo a establecer.
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el tiempo de preparación del platillo.
     *
     * @return El tiempo de preparación del platillo.
     */
    public float getTiempo() {
        return tiempo;
    }

    /**
     * Establece el tiempo de preparación del platillo.
     *
     * @param tiempo El tiempo de preparación del platillo a establecer.
     */
    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }
}

