package espol.poo.proyectopoo.modelo;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroAgua {
    /**
     * @param ingestaDiaria: lista de ingresos diarios de agua
     * @param metaDiaria: meta diaria de agua
     */
    private static ArrayList<IngestaAgua> ingestaDiaria = new ArrayList<>();
    static int metaDiaria=2500;
    private String fecha;
    public RegistroAgua(){
        this.ingestaDiaria = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fecha = LocalDate.now().format(dtf);
    }

    /**
     * Metodo para registrar una ingesta de agua
     * con la fecha de actual en que se realizó
     * @param cantidadMl: cantidad de agua en militros
     */
    public void registrarIngesta(float cantidadMl){
       DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
       String horaActual = LocalTime.now().format(tf);
       this.ingestaDiaria.add(new IngestaAgua(horaActual, cantidadMl));
    }
    // OPCION PARA LA FECHA= LA QUE USA EL RELOJ NUEVO
    public void registrarIngesta(float cantidadMl, String horaManual){
        this.ingestaDiaria.add(new IngestaAgua(horaManual, cantidadMl));
    }
    //getters y setters
    public void establecerMeta(int nuevaMeta){
        RegistroAgua.metaDiaria = nuevaMeta;
    }

    public String getFecha() {
        return this.fecha;
    }
    public String getFechaHoy() {
        // Esto obtiene la fecha actual del sistema
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getMetaDiaria() {
        return RegistroAgua.metaDiaria;
    }

    public ArrayList<IngestaAgua> getHistorialTomas() {
        return this.ingestaDiaria;
    }
    public float getIngestaAcumulada() {
        float acumulado = 0;
        for (IngestaAgua toma : ingestaDiaria) {
            acumulado += toma.getCantidadMl();
        }
        return acumulado;
    }

    public int getPorcentajeProgreso() {
        if (RegistroAgua.metaDiaria == 0) return 0;
        return (int) ((getIngestaAcumulada() * 100.0f) / RegistroAgua.metaDiaria);
    }
}
