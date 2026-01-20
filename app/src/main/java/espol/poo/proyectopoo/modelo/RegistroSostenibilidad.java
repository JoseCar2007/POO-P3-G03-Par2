package espol.poo.proyectopoo.modelo;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class RegistroSostenibilidad {
    /**
     * @param accionesDiarias: Lista diaria de acciones sostenibles
     *                        realizadas por el usuario
     * @param contadoresSemana: Contadores semanales de cada acción
     *                         sostenible
     * @param textosAcciones: Textos de cada acción sostenible
     */
    private static ArrayList<AccionSostenible> accionesDiarias= new ArrayList<>();
    private static String fechaUltimoRegistro = "";
    // Esto recuerda true/false para las 4 acciones DE HOY
    private static boolean[] accionesHoy = {false, false, false, false};
    private static int[] contadoresSemana = {4, 3, 5, 2};
    public RegistroSostenibilidad() {
        // El constructor ya no reinicia la lista si ya existe
        if (accionesDiarias == null) {
            accionesDiarias = new ArrayList<>();
        }
    }
    private String[] textosAcciones = {
        "Usé transporte público, bicicleta o caminé",
        "No realicé impresiones",
        "No utilicé envases descartables",
        "Separé y reciclé materiales"
    };

    /**
     * Metodo para registrar las acciones sostenibles realizadas
     * por el usurio en el registro diario
     * @param indicesSeleccionados: Lista de indices para seleccionar la
     *                            accion sostenible realizada
     */
    public void registrarAcciones(int[] indicesSeleccionados){
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Si es un día nuevo, reiniciamos la memoria de hoy
        if (!fechaHoy.equals(fechaUltimoRegistro)) {
            fechaUltimoRegistro = fechaHoy;
            accionesHoy = new boolean[]{false, false, false, false}; // Todo a false
            accionesDiarias.clear();
        }
        //Convertimos la selección actual a un array de booleanos temporal
        boolean[] nuevaSeleccion = {false, false, false, false};
        accionesDiarias.clear(); // Limpiamos para volver a llenar con lo actual

        for (int i : indicesSeleccionados) {
            if (i >= 0 && i < 4) {
                nuevaSeleccion[i] = true;
                // Agregamos a la lista visual para que getPuntosHoy() funcione
                accionesDiarias.add(new AccionSostenible(fechaHoy, textosAcciones[i]));
            }
        }

        for (int i = 0; i < 4; i++) {
            // CASO A: Antes NO estaba marcado, y AHORA SÍ -> Sumamos (+1)
            if (!accionesHoy[i] && nuevaSeleccion[i]) {
                if (contadoresSemana[i] < 7) {
                    contadoresSemana[i]++;
                }
            }
            // CASO B: Antes SÍ estaba marcado, y AHORA NO (lo desmarcó) -> Restamos (-1)
            else if (accionesHoy[i] && !nuevaSeleccion[i]) {
                if (contadoresSemana[i] > 0) {
                    contadoresSemana[i]--;
                }
            }
            // CASO C: Si estaba marcado y sigue marcado -> No hacemos nada (+0)
        }

        // Actualizamos la memoria de hoy
        accionesHoy = nuevaSeleccion;
    }
    /**
     * NUEVO MÉTODO Sirve para que cuando se abra la pantalla, los cuadritos aparezcan
     * ya marcados si ya se guardo ese dia.
     */
    public ArrayList<Integer> getIndicesYaMarcadosHoy() {
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ArrayList<Integer> indices = new ArrayList<>();

        // Si cambió el día, no devolvemos nada (todo desmarcado)
        if (!fechaHoy.equals(fechaUltimoRegistro)) {
            return indices;
        }

        for (int i = 0; i < accionesHoy.length; i++) {
            if (accionesHoy[i]) {
                indices.add(i);
            }
        }
        return indices;
    }
    public int getPuntosHoy() {
        return accionesDiarias.size(); 
    }
    //getters que dependen de la cantidad de puntos para mostrar mensaje al usuario
    public String getMensajeDiario() {
        int puntos = getPuntosHoy();
        if (puntos == 4) return "¡Impresionante! Eres un héroe ecológico.";
        if (puntos == 3) return "¡Excelente contribución al planeta hoy!";
        if (puntos >= 1) return "Buen esfuerzo, intenta realizar más acciones mañana.";
        return "No registraste acciones. ¡El planeta te necesita!";
    }
    public String getCalificacionTexto(int indice) {
        int dias = contadoresSemana[indice];
        
        // Caso especial Transporte (Indice 0)
        if (indice == 0 && dias >= 5) return "¡Gran Movilidad!";
        if (indice == 2 && dias >= 5) return "¡Semana limpia, felicidades!";
        if (dias == 7) return "Excelente";
        if (dias >= 5) return "Muy bien";
        if (dias == 4) return "Necesita mejorar";
        return "Crítico";
    }

    /**
     * Metodo para generar el resumen de las acciones sostenibles
     * realizadas por el usuario en la semana actual junto con los
     * mensajes correspondientes dependendiendo de la cantidad de acciones
     * realizadas
     * @return
     */
    public String generarResumenSemanal() {
        int maxDias = 0;
        for (int contador : contadoresSemana) {
            if (contador > maxDias) maxDias = contador;
        }
        int porcentaje = (maxDias * 100) / 7;
        int indicePeor = 0;
        int menorValor = 100;
        for (int i = 0; i < 4; i++) {
            if (contadoresSemana[i] < menorValor) {
                menorValor = contadoresSemana[i];
                indicePeor = i;
            }
        }
        String tip = "";
        switch (indicePeor) {
            case 0: tip = "Tip: Camina tramos cortos en lugar de usar auto para mejorar tu movilidad."; break;
            case 1: tip = "Tip: Usa formatos digitales y evita imprimir correos innecesarios."; break;
            case 2: tip = "Tip: Para aumentar tu puntaje de 'Envases', ten siempre tu botella reutilizable a la mano."; break;
            case 3: tip = "Tip: Coloca tachos diferenciados en tu cuarto para facilitar el reciclaje."; break;
            default: tip = "Sigue así, vas muy bien."; break;
        }

        return "Días con al menos 1 acción de sostenibilidad: " + maxDias + " de 7 (" + porcentaje + "%)\n" +
               "\n**Tip Ecológico de la Semana:** " + tip;
    }
    public ArrayList<AccionSostenible> getListaAccionesHoy() {
        return accionesDiarias;
    }
    public String getNombreAccionFija(int indice) {
        return textosAcciones[indice];
    }
    public int getContadorSemana(int indice) {
        return contadoresSemana[indice];
    }
    public String getFecha() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public String getRangoFechas() {
        LocalDate hoy = LocalDate.now();
        LocalDate haceUnaSemana = hoy.minusDays(6); // Restamos 6 días
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return "(" + haceUnaSemana.format(dtf) + " - " + hoy.format(dtf) + ")";
    }
    public int getDiasCumplidos() {
        int maxDias = 0;
        for (int contador : contadoresSemana) {
            if (contador > maxDias) {
                maxDias = contador;
            }
        }
        return maxDias; 
    }
    //Para el Adapter, si se agrega otra actividad ya se veria en este
    public int getCantidadAcciones() {
        return textosAcciones.length;
    }
    public String[] getTodasLasAcciones() {
        return textosAcciones;
    }
    //Metodos para guardar datos con la serializacion
    public static void guardarDatos(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("sostenibilidad_full.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Ahora podemos guardar la lista de objetos directamente porque son Serializables
            oos.writeObject(accionesDiarias);
            oos.writeObject(fechaUltimoRegistro);
            oos.writeObject(accionesHoy);
            oos.writeObject(contadoresSemana);

            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Cargar los datos
    public static void cargarDatos(Context context) {
        try {
            FileInputStream fis = context.openFileInput("sostenibilidad_full.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Leemos en el mismo orden que guardamos
            accionesDiarias = (ArrayList<AccionSostenible>) ois.readObject();
            fechaUltimoRegistro = (String) ois.readObject();
            accionesHoy = (boolean[]) ois.readObject();
            contadoresSemana = (int[]) ois.readObject();

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // Primera ejecución: no hay archivo, usamos los valores por defecto
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
