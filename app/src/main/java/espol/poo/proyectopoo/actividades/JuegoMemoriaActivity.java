package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import espol.poo.proyectopoo.MainActivity;
import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

// Esta actividad se inicia después de clickar el botón "Iniciar Juego" en la pantalla del layout de JuegoMemoriaInicioActivity.xml
public class JuegoMemoriaActivity extends AppCompatActivity {

    private JuegoMemoria juego;
    private final Button[] botones = new Button[16];
    private TextView tvInfo;

    private GridLayout gridRespuestas;

    // Variables de control del juego
    private int primeraSeleccion = -1; // -1 significa que no hay carta seleccionada
    private boolean bloqueo = false;   // Para impedir clicks mientras el sistema piensa
    private int intentos = 0;
    private int paresEncontrados = 0;

    private final String[] palabras = {
            "☀️", "💧", "🌬️", "♻️",      // Sol, Agua, Aire, Reciclaje
            "🍾", "🔩", "🥤", "📄"       // Vidrio, Metal, Plástico, Papel
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memoria);

        // Obtener referencias a los elementos del XML
        tvInfo = findViewById(R.id.tvInfo);
        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        Button btnVolver = findViewById(R.id.btnVolver);

        Button btnTrampa = findViewById(R.id.btnTrampa);
        gridRespuestas = findViewById(R.id.gridRespuestas);

        // Inicializar los botones del XML en el arreglo
        // Los IDs son b0, b1, ... b15
        int[] idsBotones = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3,
                R.id.b4, R.id.b5, R.id.b6, R.id.b7,
                R.id.b8, R.id.b9, R.id.b10, R.id.b11,
                R.id.b12, R.id.b13, R.id.b14, R.id.b15
        };

        for (int i = 0; i < idsBotones.length; i++) {
            // Asignamos el botón usando el ID del array
            botones[i] = findViewById(idsBotones[i]);

            // Mantenemos la lógica de índices (Modelo usa 1-16, Array usa 0-15)
            final int index = i + 1;
            botones[i].setOnClickListener(v -> manejarClick(index));
        }

        btnReiniciar.setOnClickListener(v -> iniciarJuego());

        btnTrampa.setOnClickListener(v -> mostrarRespuestas());

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
        if (gridRespuestas != null) {
            gridRespuestas.removeAllViews();
            gridRespuestas.setVisibility(View.GONE);
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
            bloqueo = true;

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
                // Esperamos medio segundo (500ms) y luego las ocultamos
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
        // getString toma la plantilla y reemplaza %1$d por 'intentos' y %2$d por 'paresEncontrados'
        String mensaje = getString(R.string.texto_stats, intentos, paresEncontrados);
        tvInfo.setText(mensaje);
    }
    private void mostrarRespuestas() {
        // Si ya está visible, no hacemos nada
        if (gridRespuestas.getVisibility() == View.VISIBLE) return;

        gridRespuestas.removeAllViews();

        for (int i = 1; i <= 16; i++) {
            TextView tvMini = new TextView(this);

            String emoji = juego.verValor(i);
            tvMini.setText(emoji);

            tvMini.setTextSize(20);
            tvMini.setGravity(android.view.Gravity.CENTER);
            tvMini.setBackgroundResource(R.drawable.fondo_carta); // Usamos el mismo fondo verde
            tvMini.setTextColor(ContextCompat.getColor(this, R.color.white));

            // Definimos el tamaño del cuadrito (40dp x 40dp aprox)
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;  // Ancho en pixeles
            params.height = 100; // Alto en pixeles
            params.setMargins(8, 8, 8, 8); // Separación entre cuadritos
            tvMini.setLayoutParams(params);

            // Agregamos al mini grid
            gridRespuestas.addView(tvMini);
        }

        // Hacemos visible el grid
        gridRespuestas.setVisibility(View.VISIBLE);
    }
}
