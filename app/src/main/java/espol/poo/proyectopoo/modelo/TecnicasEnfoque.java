package espol.poo.proyectopoo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class TecnicasEnfoque implements Serializable {
    /**
     * @param duracionTrabajo: duración del trabajo en minutos
     * @param duracionDescanso: duración del descanso en minutos
     *                        (opcional)
     * @param ciclos: número de ciclos de trabajo-descanso
     * @param fechaSesion: fecha en que se realizó la sesión
     * @param tecnica: técnica utilizada en la sesión
     */
    private int duracionTrabajo;
    private int duracionDescanso;
    private int ciclos;
    private String fechaSesion;
    private String tecnica;

    public TecnicasEnfoque(){
        establecerFecha();
    }

    public TecnicasEnfoque(String t, int dt, int dd, int c){
        tecnica = t;
        duracionTrabajo = dt;
        duracionDescanso = dd;
        ciclos = c; 
        establecerFecha();
    }

    /**
     * Metodo para establecer la fecha en la que se realiza
     * la sesion
     */
    private void establecerFecha(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");  //Ya definido dia-mes-año
        fechaSesion = currentTime.format(formatter);
    }
    //getters y setters
    public void setCiclos(int ciclos){
        this.ciclos = ciclos;
    }
    public int getCiclos(){
        return ciclos;
    }
    public String getFecha(){
        return fechaSesion;
    }
    public String getTecnica(){
        return tecnica;
    }
    public int getDuracion(){
        return duracionTrabajo;
    }
}
