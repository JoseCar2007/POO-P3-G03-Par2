package espol.poo.proyectopoo.modelo;

import java.io.Serializable;

public class AccionSostenible implements Serializable {
    /**
     * @param fecha: fecha en la que se realiza la acción
     * @param descripcion: descripción de la acción
     */
    private String fecha;
    private String descripcion; 
    public AccionSostenible(String fecha, String descripcion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
