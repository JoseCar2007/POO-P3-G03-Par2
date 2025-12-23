package espol.poo.proyectopoo.modelo;
import java.util.ArrayList;
public class ActividadAcademica extends Actividad{
    private String asignatura;
    private String estado;
    private ArrayList<TecnicasEnfoque> registroTecnicas = new ArrayList<>();
    //tecnicaEstudio
    public ActividadAcademica(String nombre, String fecha, int horasEstimadas, String descripcion, String prioridad, String asignatura, int avance, tipoActividad tipo, String estado){
        super(nombre, fecha, horasEstimadas, descripcion, prioridad, avance, tipo);
        this.asignatura = asignatura;
        this.estado = estado;
    }
    public String[] devolverInfoAcademica(){
        String[] retornable = {nombre, tipo.toString(), asignatura, prioridad, estado, fecha, String.valueOf(tiempoEstimado), String.valueOf(avance) + "%"};
        return retornable;
    }
    public void registrarTecnicaEnfoque(TecnicasEnfoque te){
        registroTecnicas.add(te);
    }
    public ArrayList<TecnicasEnfoque> getTecnicas(){
        return registroTecnicas;
    }
}
