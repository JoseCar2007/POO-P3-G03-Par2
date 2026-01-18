package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.proyectopoo.MainActivity;
import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

public class JuegoMemoriaActivity extends AppCompatActivity {

    private JuegoMemoria juego;
    private Button[] botones = new Button[16];
    private TextView tvInfo;

    // Variables de control del juego
    private int primeraSeleccion = -1; // -1 significa que no hay carta seleccionada
    private boolean bloqueo = false;   // Para impedir clicks mientras el sistema "piensa"
    private int intentos = 0;
    private int paresEncontrados = 0;

    // Palabras relacionadas a Sostenibilidad (como pide el PDF)
    // En JuegoMemoriaActivity.java
    private String[] palabras = {
            "☀️", "💧", "🌬️", "♻️",      // Solar, Agua, Aire, Reciclaje
            "🍾", "🔩", "🥤", "📄"       // Vidrio, Metal, Plástico, Papel
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memoria);

        tvInfo = findViewById(R.id.tvInfo);
        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Inicializar los botones del XML en el arreglo
        // Los IDs son b0, b1, ... b15
        for (int i = 0; i < 16; i++) {
            String idID = "b" + i;
            int resID = getResources().getIdentifier(idID, "id", getPackageName());
            botones[i] = findViewById(resID);

            // Asignar el evento Click a cada botón
            final int index = i + 1; // El modelo usa indices 1-16
            botones[i].setOnClickListener(v -> manejarClick(index));
        }

        btnReiniciar.setOnClickListener(v -> iniciarJuego());

        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);

            // Esto es una buena práctica: Limpia las pantallas anteriores
            // para que si le das "Atrás" en el menú, no te regrese al juego.
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });

        iniciarJuego();
    }

    private void iniciarJuego() {
        juego = new JuegoMemoria(palabras);
        primeraSeleccion = -1;
        bloqueo = false;
        intentos = 0;
        paresEncontrados = 0;
        actualizarTextoInfo();

        // Resetear visualmente todos los botones
        for (Button btn : botones) {
            btn.setText("?");
            btn.setEnabled(true);
            btn.setAlpha(1.0f); // Opacidad completa
        }
    }

    private void manejarClick(int numeroCarta) {
        // 1. Si el sistema está ocupado (esperando timer), no hacer nada
        if (bloqueo) return;

        // 2. Si la carta ya está emparejada o es la misma que ya toqué, no hacer nada
        if (juego.estaEmparejada(numeroCarta)) return;
        if (primeraSeleccion == numeroCarta) return;

        // 3. Mostrar el contenido de la carta
        int indexArray = numeroCarta - 1;
        botones[indexArray].setText(juego.verValor(numeroCarta));

        // LÓGICA DE PARES
        if (primeraSeleccion == -1) {
            // Es la primera carta que levanto en este turno
            primeraSeleccion = numeroCarta;
        } else {
            // Es la segunda carta
            intentos++;
            actualizarTextoInfo();
            bloqueo = true; // Bloqueamos para que no toque una 3ra carta rapido

            if (juego.verValor(primeraSeleccion).equals(juego.verValor(numeroCarta))) {
                // --- ¡COINCIDENCIA! ---
                juego.marcarEmparejadas(primeraSeleccion, numeroCarta);
                paresEncontrados++;
                actualizarTextoInfo();

                Toast.makeText(this, "¡Bien hecho!", Toast.LENGTH_SHORT).show();

                // Desbloqueamos inmediatamente, no hay que ocultar nada
                primeraSeleccion = -1;
                bloqueo = false;

                // Verificar si ganó
                if (paresEncontrados == 8) {
                    Toast.makeText(this, "¡Juego Terminado! Felicidades.", Toast.LENGTH_LONG).show();
                }

            } else {
                // --- FALLO ---
                // Esperamos 1 segundo (1000ms) y luego las ocultamos
                new Handler().postDelayed(() -> {
                    // Ocultar texto visualmente
                    botones[primeraSeleccion - 1].setText("?");
                    botones[numeroCarta - 1].setText("?");

                    // Resetear variables
                    primeraSeleccion = -1;
                    bloqueo = false;
                }, 500);
            }
        }
    }

    private void actualizarTextoInfo() {
        tvInfo.setText("Intentos: " + intentos + " | Pares: " + paresEncontrados + "/8");
    }
}