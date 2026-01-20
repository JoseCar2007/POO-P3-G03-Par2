package espol.poo.proyectopoo.modelo;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Actividad implements Serializable, Comparable<Actividad> {
    /**
     * Variables importantes para la clase Actividad
     * @param id: identificador unico para cada actividad
     * @param nombre: nombre de la actividad
     * @param fecha: fecha de vencimiento de la actividad
     * @param tiempoEstimado: tiempo estimado de la actividad
     * @param descripcion: descripcion de la actividad
     * @param prioridad: prioridad de la actividad
     * @param avance: avance de la actividad
     * @param tipo: tipo de actividad (tipo enum)
     * @param actividades: variable estática para tener todas las actividades creadas
     * @param filtroOrdenamiento: variable para ordenar las actividades según avance nombre o tipo
     * @param idSetter: variable para asignar un id a cada actividad automáticamente
     *                es estática ya que se actualiza con cada actividad creada.
     *
     */
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
    private static String filtroOrdenamiento;
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
    //getters y setters de las variables básicas
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
    public String getDescripcion(){
        return descripcion;
    }
    public String getFecha(){ return fecha; }
    public String getPrioridad(){ return prioridad; }
    public int getTiempoEstimado(){
        return tiempoEstimado;
    }
    public void setAvance(int a){
        this.avance = a < 100 ? a : 100;
    }

    /**
     * Setter estático para definir como se van a filtrar las actividades
     * @param filtro: filtro para ordenar actividades
     */
    public static void setFiltroOrdenamiento(String filtro){
        filtroOrdenamiento = filtro;
    }

    /**
     * Metodo estatico para tener una lista de actividades
     * ya creada antes de iniciar la aplicacion
     */

    public static void cargarDatos(Context context){
        try(FileInputStream fis = context.openFileInput("actividades.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            actividades = (ArrayList<Actividad>) ois.readObject();
        }catch(IOException e){
            setData();
        }catch(Exception e){
            setData();
        }
    }
    private static void setData(){
        String[] lnombre = {"Tarea de Hilos", "Tarea de Android", "Tarea de Lambda"};

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
    //Getters y setters de las variables de la actividad
    public static ArrayList<Actividad> getActividades(){
        return actividades;
    }
    public static void añadirActividad(Actividad a){
        actividades.add(a);
    }
    public static void removerActividad(Actividad a){
        actividades.remove(a);
    }

    /**
     *
     * @param a el objeto a comparar
     * @return entero que indica si el objeto es mayor o menor que
     *         ingresado
     * Nombre: ordenamiento A-Z
     * Fecha: ordenamiento descendente
     * Avance: ordenamiento descendente
     */
    public int compareTo(Actividad a){
        if(filtroOrdenamiento == null) return 0;
        switch (filtroOrdenamiento) {
            case "Nombre":
                return this.nombre.compareTo(a.nombre);
            case "Fecha":
                return a.fecha.compareTo(this.fecha);
            case "Avance":
                return a.avance - this.avance;
        }
        return 0;
    }

}
