package espol.poo.proyectopoo.modelo;
import java.util.ArrayList;
public class ActividadAcademica extends Actividad{
    /**
     * @param asignatura: asignatura a la que pertenece la actividad
     * @param estado: estado de la actividad (en curso, finalizada
     *              este último se coloca cuando avance = 100)
     * @param registroTecnicas: lista de tecnicas de enfoque de la actividad
     *                        para construir historial de gestión de tiempo
     */
    private String asignatura;
    private String estado;
    private ArrayList<TecnicasEnfoque> registroTecnicas = new ArrayList<>();
    //tecnicaEstudio
    public ActividadAcademica(String nombre, String fecha, int horasEstimadas, String descripcion, String prioridad, String asignatura, int avance, tipoActividad tipo, String estado){
        super(nombre, fecha, horasEstimadas, descripcion, prioridad, avance, tipo);
        this.asignatura = asignatura;
        this.estado = estado;
    }
    //getters y setters
    public String getAsignatura(){
        return asignatura;
    }
    public String getEstado(){
        return estado;
    }
    public void registrarTecnicaEnfoque(TecnicasEnfoque te){
        registroTecnicas.add(te);
    }
    public ArrayList<TecnicasEnfoque> getTecnicas(){
        return registroTecnicas;
    }
}
