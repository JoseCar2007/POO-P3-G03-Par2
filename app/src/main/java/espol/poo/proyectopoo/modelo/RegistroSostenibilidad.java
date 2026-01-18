package espol.poo.proyectopoo.modelo;

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
    private int[] contadoresSemana = {4, 3, 5, 2};
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
        accionesDiarias.clear();
        for (int i : indicesSeleccionados) {
            if (i >= 0 && i < 4) {
                String indecin = textosAcciones[i];
                AccionSostenible nuevaAccion = new AccionSostenible(fechaHoy, indecin);
                accionesDiarias.add(nuevaAccion);
                if (contadoresSemana[i] < 7) {
                    contadoresSemana[i]++;
                }
            }
    } 
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
}
