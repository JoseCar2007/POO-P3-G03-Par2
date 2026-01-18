package espol.poo.proyectopoo.modelo;

public class ActividadPersonal extends Actividad{
    /**
     * @param lugar: lugar donde se realiza la actividad
     */
    private String lugar;
    public ActividadPersonal(String nombre, String fecha, int horasEstimadas, String descripcion, String prioridad, int avance, String lugar, tipoActividad tipo){
        super(nombre, fecha, horasEstimadas, descripcion, prioridad, avance,tipo);
        this.lugar = lugar;
    }
    public String getLugar(){
        return lugar;
    }
}
