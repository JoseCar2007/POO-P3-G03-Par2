package espol.poo.proyectopoo.modelo;

import java.time.LocalTime;

public class IngestaAgua {
    /**
     * @param hora: hora en la que se toma la ingesta de agua
     * @param cantidadTomada: cantidad de agua que se toma en ml
     */
    private String hora;
    private float cantidadTomada;
    public IngestaAgua(String hora, float cantidadTomada){
        this.hora = hora;
        this.cantidadTomada = cantidadTomada;
    }
    public String getHoraToma() {
        return hora;
    }

    public float getCantidadMl() {
        return cantidadTomada;
    }
}
