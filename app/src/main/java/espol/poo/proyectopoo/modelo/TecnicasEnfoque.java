package espol.poo.proyectopoo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class TecnicasEnfoque implements Serializable {
    private int duracionTrabajo;
    private int duracionDescanso;
    private int ciclos;
    private String fechaSesion;
    private String tecnica;

    public TecnicasEnfoque(){
        establecerFecha();
    }

    public TecnicasEnfoque(String t, int dt, int dd, int c){
        tecnica = t;
        duracionTrabajo = dt;
        duracionDescanso = dd;
        ciclos = c; 
        establecerFecha();
    }

    private void establecerFecha(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");  //Ya definido dia-mes-año
        fechaSesion = currentTime.format(formatter);
    }

    public String[] devolverInfo(){
        String[] retornable = {fechaSesion, tecnica, String.valueOf(duracionTrabajo)};
        return retornable;
    }
    public String getFecha(){
        return fechaSesion;
    }
    public String getTecnica(){
        return tecnica;
    }
    public int getDuracion(){
        return duracionTrabajo;
    }

    // -------------------
    // INICIAR POMODORO
    // -------------------

    public void iniciarPomodoro(Scanner sc, ActividadAcademica actividad){
        tecnica = "Pomodoro";
        duracionTrabajo = 25;
        duracionDescanso = 5;
        ciclos = 4; 

        // Registrar técnica si es ActividadAcademica
        if (actividad instanceof ActividadAcademica) {
            ((ActividadAcademica) actividad).registrarTecnicaEnfoque(this);
        }

        System.out.println("\n--- INICIAR POMODORO ---");

        // 1) mostrar tabla y pedir selección
        if(actividad == null){
            System.out.println("Saliendo de Pomodoro...");
            return;
        }

        String nombreActividad = actividad.getNombre(); // Asumiendo el nombre en la posición 2
        
        for(int i = 1; i <= ciclos; i++){
            // --- INICIO TRABAJO ---
            System.out.println("\n>>> INICIANDO TRABAJO EN '" + nombreActividad + "' <<<");
            System.out.println("Técnica: " + tecnica + " | Ciclo: " + i + "/" + ciclos);
            
            simularTiempo(sc, duracionTrabajo, "Trabajo");

            // -------- Aquí actualizamos el avance de la actividad -------------
            int avanceActual = actividad.getAvance();
            int nuevoAvance = avanceActual + duracionTrabajo;
            actividad.setAvance(nuevoAvance);

            System.out.println("\n--- TIEMPO DE TRABAJO TERMINADO ---");
            System.out.println("Avance actualizado de la actividad: " + nuevoAvance);

            
            // 2) Registro del tiempo de trabajo y avance
            System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
            System.out.println("Sesión registrada. (Avance de la actividad ID " + actividad.getId() + " actualizado en base al tiempo).");
            // Se registra la sesión y el avance en la actividad


            // Si es el último ciclo, no hay descanso
            if(i < ciclos){
                // --- INICIO DESCANSO ---
                System.out.println("Ahora toma un DESCANSO: " + String.format("%02d", duracionDescanso) + ":00 minutos restantes");
                System.out.print("Presione [ENTER] para iniciar el descanso...");
                sc.nextLine();

                simularTiempo(sc, duracionDescanso, "Descanso");
                
                System.out.println("\n--- ¡TIEMPO DE DESCANSO TERMINADO! ---");
            } else {
                System.out.println("¡Pomodoro completado!");
            }
        }
    }

    public void iniciarDeepWork(Scanner sc){
        // Simulación para Deep Work (Opción 2)
        this.tecnica = "Deep Work";
        this.duracionTrabajo = 90;
        this.duracionDescanso = 0; // No hay descanso intermedio

        System.out.println("\n--- INICIAR DEEP WORK ---");
        System.out.println("Duración: 90 minutos de trabajo ininterrumpido.");
        sc.nextLine();

        simularTiempo(sc, duracionTrabajo, "Trabajo"); // simula el periodo
    }

    // -------------------
    // SIMULAR TIEMPO (espera por ENTER)
    // -------------------
    // Muestra el tiempo restante y espera que el usuario presione ENTER para "finalizar" el periodo.
    private void simularTiempo(Scanner sc, int minutos, String tipo){
        // Mostrar minutos en formato mm:00
        System.out.println("Tiempo de " + tipo.toLowerCase() + ": " + String.format("%02d", minutos) + ":00 minutos restantes");
        System.out.println("[Simulación] Presione [ENTER] para finalizar el periodo de " + tipo + "...");
        sc.nextLine(); // esperar ENTER
    }

    // Método para iniciar descanso si lo quieres usar por separado (opcional)
    private void iniciarDescanso(Scanner sc, int duracionDescanso){
        System.out.println("Ahora toma un DESCANSO: " + String.format("%02d", duracionDescanso) + ":00 minutos restantes");
        System.out.print("Presione [ENTER] para iniciar el descanso...");
        sc.nextLine();
        simularTiempo(sc, duracionDescanso, "Descanso");
        System.out.println("\n--- ¡TIEMPO DE DESCANSO TERMINADO! ---");
    }
}
