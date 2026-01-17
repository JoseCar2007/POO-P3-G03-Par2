package espol.poo.proyectopoo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Actividad implements Serializable {
    protected int id;
    private static int idSetter = 1;
    protected String nombre;
    protected String fecha;
    protected int tiempoEstimado;
    protected String descripcion;
    protected String prioridad;
    protected int avance;
    private static ArrayList<Actividad> actividades = new ArrayList<>();
    protected tipoActividad tipo;

    public Actividad(String nombre, String fecha, int tiempoEstimado, String descripcion, String prioridad, int avance, tipoActividad tipo){
        this.id = idSetter;
        idSetter++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.avance = avance;
        this.tipo = tipo;
    }

    public int getId(){
        return id;
    }
    public String getTipo(){
        return tipo.toString();
    }
    public int getAvance(){
        return avance;
    }
    public String getNombre(){
        return nombre;
    }
    public String getFecha(){ return fecha; }
    public String getPrioridad(){ return prioridad; }
    public int getTiempoEstimado(){
        return tiempoEstimado;
    }
    public void setAvance(int a){
        this.avance = a < 100 ? a : 100;
    }

    public static void setData(){
        String[] lnombre = {"Actividad 1", "Actividad 2", "Actividad 3"};

        String[] lfecha = {"2026-01-01", "2026-01-02", "2026-01-03"};
        String[] lprioridad = {"Alta", "Media", "Baja"};
        int[] lavance = {10, 20, 30};
        for(int i=0; i<3; i++){
            Actividad a = new ActividadAcademica(lnombre[i], lfecha[i], 30 + i, "Tarea de id: " + i, lprioridad[i], "POO", lavance[i], tipoActividad.TAREA, "En curso");
            actividades.add(a);
        }
        actividades.add(new ActividadPersonal("Ir al doctor", "2026-01-01", 2, "Tarea de ejemplo", "Alta", 0, "Omni hospital", tipoActividad.PERSONAL));
        actividades.add(new ActividadAcademica("Examen Quimica", "2026-01-01", 2, "Repasar capitulos 3 y 4", "Alta", "Quimica", 0, tipoActividad.PROYECTO, "En curso"));
        for(int i=0; i<4;i++){
            ((ActividadAcademica) actividades.get(4)).registrarTecnicaEnfoque(new TecnicasEnfoque("Pomodoro", 25, 5, 4));
        }
        ((ActividadAcademica) actividades.get(4)).registrarTecnicaEnfoque(new TecnicasEnfoque("DeepWork", 90, 0, 1));
    }
    public static ArrayList<Actividad> getActividades(){
        return actividades;
    }
    public static void añadirActividad(Actividad a){
        actividades.add(a);
    }
    public static void removerActividad(Actividad a){
        actividades.remove(a);
    }
}
