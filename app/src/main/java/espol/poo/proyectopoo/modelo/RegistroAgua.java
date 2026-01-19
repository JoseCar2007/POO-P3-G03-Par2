package espol.poo.proyectopoo.modelo;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroAgua {
    /**
     * @param ingestaDiaria: lista de ingresos diarios de agua
     * @param metaDiaria: meta diaria de agua
     */
    private static ArrayList<TomaAgua> historialGlobal = new ArrayList<>();
    static int metaDiaria=2500;
    private static final String NOMBRE_ARCHIVO = "historial_agua.bin";
    private String fecha;
    // --- CLASE INTERNA (SERIALIZABLE) ---
    // 'implements Serializable' es la clave para que Java pueda guardarlo en archivo
    public static class TomaAgua implements Serializable {
        public String fecha;
        public String hora;
        public int cantidadMl;

        public TomaAgua(String fecha, int cantidadMl) {
            this.fecha = fecha;
            this.cantidadMl = cantidadMl;
            this.hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        @Override
        public String toString() {
            return cantidadMl + " ml (" + hora + ")";
        }
        public float getCantidadMl() { return (float) cantidadMl; }
    }
    public RegistroAgua(){
    }

    // Registra ingesta guardando la FECHA y escribiendo en el ARCHIVO.
    public void registrarIngesta(String fecha, float cantidadMl, Context context){
        TomaAgua nuevaToma = new TomaAgua(fecha, (int) cantidadMl);
        historialGlobal.add(nuevaToma);
        guardarEnArchivo(context);
    }
    // Se obtiene la lista especifica del dia a buacar
    public ArrayList<TomaAgua> getHistorialTomas(String fechaBuscada) {
        ArrayList<TomaAgua> filtrado = new ArrayList<>();
        // Recorremos todo el historial
        for (TomaAgua toma : historialGlobal) {
            if (toma.fecha.equals(fechaBuscada)) {
                filtrado.add(toma);
            }
        }
        return filtrado;
    }
    //El total solo de la fecha indicada
    public float getIngestaAcumulada(String fecha) {
        float acumulado = 0;
        for (TomaAgua toma : getHistorialTomas(fecha)) {
            acumulado += toma.getCantidadMl();
        }
        return acumulado;
    }
    //El porcentaje solo de la fecha indicada
    public int getPorcentajeProgreso(String fecha) {
        if (metaDiaria == 0) return 0;
        int porcentaje = (int) ((getIngestaAcumulada(fecha) * 100.0f) / metaDiaria);
        // Para que no se salga de la gráfica si te pasas del 100%
        return Math.min(porcentaje, 100);
    }
    //Se guarda en el archivo
    public void guardarEnArchivo(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(historialGlobal);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cargarDesdeArchivo(Context context) {
        try {
            FileInputStream fis = context.openFileInput(NOMBRE_ARCHIVO);
            ObjectInputStream in = new ObjectInputStream(fis);
            historialGlobal = (ArrayList<TomaAgua>) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            // Es normal la primera vez, no hacemos nada
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //getters y setters
    public void establecerMeta(int nuevaMeta){
        metaDiaria = nuevaMeta;
    }
    public String getFechaHoy() {
        // Esto obtiene la fecha actual del sistema
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getMetaDiaria() {
        return metaDiaria;
    }



}
