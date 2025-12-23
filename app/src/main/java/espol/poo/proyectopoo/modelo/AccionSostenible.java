package espol.poo.proyectopoo.modelo;

public class AccionSostenible {
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
