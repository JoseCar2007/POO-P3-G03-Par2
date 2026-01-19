package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import espol.poo.proyectopoo.MainActivity;
import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

public class JuegoMemoriaActivity extends AppCompatActivity {

    private JuegoMemoria juego;
    /* Usamos ImageButton en lugar de Button para que el icono se vea sobre el fondo verde */
    private final ImageButton[] botones = new ImageButton[16];
    private TextView tvInfo;
    private GridLayout gridRespuestas;

    private int primeraSeleccion = -1;
    private boolean bloqueo = false;
    private int intentos = 0;
    private int paresEncontrados = 0;

    /* CAMBIO: Array de IDs de tus Drawables (Imágenes reales)
       Asegúrate de haber creado estos iconos en res/drawable con color BLANCO */
    private final int[] imagenes = {
            R.drawable.ic_sol,      R.drawable.ic_agua,
            R.drawable.ic_aire,     R.drawable.ic_reciclaje,
            R.drawable.ic_vidrio,   R.drawable.ic_metal,
            R.drawable.ic_plastico, R.drawable.ic_papel
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memoria);

        tvInfo = findViewById(R.id.tvInfo);
        gridRespuestas = findViewById(R.id.gridRespuestas);
        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        Button btnVolver = findViewById(R.id.btnVolver);
        Button btnTrampa = findViewById(R.id.btnTrampa);
        Button btnIrInicio = findViewById(R.id.btnIrInicio);

        /* Array de IDs para evitar el warning de getIdentifier (Buena Práctica) */
        int[] idsBotones = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3,
                R.id.b4, R.id.b5, R.id.b6, R.id.b7,
                R.id.b8, R.id.b9, R.id.b10, R.id.b11,
                R.id.b12, R.id.b13, R.id.b14, R.id.b15
        };

        for (int i = 0; i < idsBotones.length; i++) {
            // Casteamos a ImageButton
            botones[i] = findViewById(idsBotones[i]);
            final int index = i + 1;
            botones[i].setOnClickListener(v -> manejarClick(index));
        }

        btnReiniciar.setOnClickListener(v -> iniciarJuego());

        // Listener del botón trampa
        btnTrampa.setOnClickListener(v -> mostrarRespuestas());

        /* CORRECCIÓN RÚBRICA (2 PUNTOS):
           Se debe proveer un medio para volver a la pantalla principal de INICIO DEL JUEGO */
        btnIrInicio.setOnClickListener(v -> {
            Intent i = new Intent(this, JuegoMemoriaInicioActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });

        /* Botón para volver al MENÚ PRINCIPAL de la App (Navegación General) */
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class); // <--- Va al MainActivity
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });

        iniciarJuego();
    }

    private void iniciarJuego() {
        juego = new JuegoMemoria(imagenes); // Pasamos los IDs de imágenes

        primeraSeleccion = -1;
        bloqueo = false;
        intentos = 0;
        paresEncontrados = 0;
        actualizarTextoInfo();

        // Limpiar trampa
        if (gridRespuestas != null) {
            gridRespuestas.removeAllViews();
            gridRespuestas.setVisibility(View.GONE);
        }

        // Resetear botones
        for (ImageButton btn : botones) {
            /* IMPORTANTE: */
            btn.setImageResource(0); // 1. Quitamos el icono (Sol/Agua)
            btn.setBackgroundResource(R.drawable.fondo_carta); // 2. Ponemos el fondo verde
            btn.setEnabled(true);
        }
    }

    private void manejarClick(int numeroCarta) {
        if (bloqueo) return;
        if (juego.estaEmparejada(numeroCarta)) return;
        if (primeraSeleccion == numeroCarta) return;

        int indexArray = numeroCarta - 1;

        /*
           Visualiza tablero con imágenes escondidas y destapadas.
           Usamos setImageResource para poner el icono encima del fondo verde. */
        botones[indexArray].setImageResource(juego.verImagen(numeroCarta));

        if (primeraSeleccion == -1) {
            primeraSeleccion = numeroCarta;
        } else {
            intentos++;
            actualizarTextoInfo();
            bloqueo = true;

            // Comparamos los IDs de las imágenes
            if (juego.verImagen(primeraSeleccion) == juego.verImagen(numeroCarta)) {

                juego.marcarEmparejadas(primeraSeleccion, numeroCarta);
                paresEncontrados++;
                actualizarTextoInfo();
                Toast.makeText(this, "¡Par encontrado!", Toast.LENGTH_SHORT).show();

                primeraSeleccion = -1;
                bloqueo = false;

                if (paresEncontrados == 8) {
                    Toast.makeText(this, "¡Juego Terminado!", Toast.LENGTH_LONG).show();
                }

            } else {
                new Handler().postDelayed(() -> {
                    /* Ocultar volviendo a poner la imagen en 0 (null)
                       El fondo verde se mantiene solo */
                    botones[primeraSeleccion - 1].setImageResource(0);
                    botones[numeroCarta - 1].setImageResource(0);

                    primeraSeleccion = -1;
                    bloqueo = false;
                }, 500);
            }
        }
    }

    /* Muestra las imágenes de las cartas seleccionadas con el botón Ver Respuestas */
    private void mostrarRespuestas() {
        if (gridRespuestas.getVisibility() == View.VISIBLE) return;
        gridRespuestas.removeAllViews();

        for (int i = 1; i <= 16; i++) {
            ImageView imgMini = new ImageView(this);
            int idImagen = juego.verImagen(i);
            imgMini.setImageResource(idImagen);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(8, 8, 8, 8);
            imgMini.setLayoutParams(params);

            /* Usamos el mismo drawable que las cartas para que tenga bordes redondeados */
            imgMini.setBackgroundResource(R.drawable.fondo_carta);
            imgMini.setPadding(10, 10, 10, 10); // Padding para que el icono no toque el borde

            gridRespuestas.addView(imgMini);
        }
        gridRespuestas.setVisibility(View.VISIBLE);
    }

    private void actualizarTextoInfo() {
        /* Usamos placeholders %d para evitar warnings de concatenación */
        String mensaje = getString(R.string.texto_stats, intentos, paresEncontrados);
        tvInfo.setText(mensaje);
    }
}