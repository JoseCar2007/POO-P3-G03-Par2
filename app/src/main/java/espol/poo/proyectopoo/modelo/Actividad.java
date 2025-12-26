package espol.poo.proyectopoo.modelo;

import java.io.Serializable;

public class Actividad implements Serializable {
    protected int id;
    private static int idSetter = 1;
    protected String nombre;
    protected String fecha;
    protected int tiempoEstimado;
    protected String descripcion;
    protected String prioridad;
    protected int avance;
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


    /*
        Devolver información general dependiendo el caso de uso
        modo 2: Se usa para el apartado de tabla de avances
        modo 3: se usa para el aparatado de tabla en eliminar actividad y registro de tecnicas de enfoque
        modo por defecto: se usa para el apartado de tabla general
    */
    public String[] devolverInfo(int modo){
        if(modo == 2){
            String[] retornable = {String.valueOf(id), tipo.toString(), nombre, String.valueOf(avance) + "%"};
            return retornable;
        }
        else if(modo == 3){
            String[] retornable = {String.valueOf(id), tipo.toString(), nombre};
            return retornable;
        }
        else{
            String[] retornable = {String.valueOf(id), tipo.toString(), nombre, fecha, prioridad, String.valueOf(avance)+ "%"};
            return retornable;
        }
    }
}
