package espol.poo.proyectopoo.modelo;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroAgua {
    private ArrayList<IngestaAgua> ingestaDiaria = new ArrayList<>();
    static int metaDiaria=2500;
    private String fecha;
    public RegistroAgua(){
        this.ingestaDiaria = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fecha = LocalDate.now().format(dtf);
    }
    public RegistroAgua(String fecha, float ml){
        this.ingestaDiaria = new ArrayList();
        this.fecha = fecha;
        registrarIngesta(ml);
    }
    public void registrarIngesta(float cantidadMl){
       DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
       String horaActual = LocalTime.now().format(tf);
       this.ingestaDiaria.add(new IngestaAgua(horaActual, cantidadMl));
    }

    public void establecerMeta(int nuevaMeta){
        RegistroAgua.metaDiaria = nuevaMeta;
    }

    public String getFecha() {
        return this.fecha;
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
