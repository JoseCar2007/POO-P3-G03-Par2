package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import espol.poo.proyectopoo.MainActivity;
import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

public class JuegoMemoriaActivity extends AppCompatActivity {

    private JuegoMemoria juego;
    private final Button[] botones = new Button[16];
    private TextView tvInfo;
    private GridLayout gridRespuestas;

    private int primeraSeleccion = -1;
    private boolean bloqueo = false;
    private int intentos = 0;
    private int paresEncontrados = 0;

    // CAMBIO: Array de IDs de tus Drawables (Imágenes reales)
    // Asegúrate de haber creado estos iconos en res/drawable
    private final int[] imagenes = {
            R.drawable.ic_sol,      R.drawable.ic_agua,
            R.drawable.ic_aire,     R.drawable.ic_reciclaje, // O el nombre que les hayas puesto
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

        // Array de IDs para evitar el warning de getIdentifier
        int[] idsBotones = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3,
                R.id.b4, R.id.b5, R.id.b6, R.id.b7,
                R.id.b8, R.id.b9, R.id.b10, R.id.b11,
                R.id.b12, R.id.b13, R.id.b14, R.id.b15
        };

        for (int i = 0; i < idsBotones.length; i++) {
            botones[i] = findViewById(idsBotones[i]);
            final int index = i + 1;
            botones[i].setOnClickListener(v -> manejarClick(index));
        }

        btnReiniciar.setOnClickListener(v -> iniciarJuego());

        // Listener del botón trampa
        btnTrampa.setOnClickListener(v -> mostrarRespuestas());

        // Botón volver al Inicio del juego
        btnIrInicio.setOnClickListener(v -> {
            Intent i = new Intent(this, JuegoMemoriaInicioActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });

        // Botón volver a la pantalla del menú principal
        // Botón volver a la pantalla del menú principal (MainActivity)
        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class); // <--- CAMBIAR A MainActivity
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
        for (Button btn : botones) {
            btn.setText(""); // Sin texto porque ahora usaremos imágenes
            // Ponemos el fondo verde (carta tapada)
            btn.setBackgroundResource(R.drawable.fondo_carta);
            btn.setEnabled(true);
        }
    }

    private void manejarClick(int numeroCarta) {
        if (bloqueo) return;
        if (juego.estaEmparejada(numeroCarta)) return;
        if (primeraSeleccion == numeroCarta) return;

        int indexArray = numeroCarta - 1;

        // --- CORRECCIÓN RÚBRICA (8 PUNTOS) ---
        // Mostrar la IMAGEN (Drawable) en lugar de texto
        botones[indexArray].setBackgroundResource(juego.verImagen(numeroCarta));

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
                    // Ocultar volviendo a poner el fondo verde
                    botones[primeraSeleccion - 1].setBackgroundResource(R.drawable.fondo_carta);
                    botones[numeroCarta - 1].setBackgroundResource(R.drawable.fondo_carta);

                    primeraSeleccion = -1;
                    bloqueo = false;
                }, 1000);
            }
        }
    }

    // --- TRAMPA ACTUALIZADA PARA IMÁGENES ---
    private void mostrarRespuestas() {
        if (gridRespuestas.getVisibility() == View.VISIBLE) return;
        gridRespuestas.removeAllViews();

        for (int i = 1; i <= 16; i++) {
            // Ahora creamos ImageView en lugar de TextView
            ImageView imgMini = new ImageView(this);

            int idImagen = juego.verImagen(i);
            imgMini.setImageResource(idImagen);

            // Ajuste de tamaño
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(8, 8, 8, 8);
            imgMini.setLayoutParams(params);

            // Fondo verde o transparente para que se vea bien el icono
            imgMini.setBackgroundColor(ContextCompat.getColor(this, R.color.verde_oscuro));
            gridRespuestas.addView(imgMini);
        }
        gridRespuestas.setVisibility(View.VISIBLE);
    }

    private void actualizarTextoInfo() {
        String mensaje = getString(R.string.texto_stats, intentos, paresEncontrados);
        tvInfo.setText(mensaje);
    }
}