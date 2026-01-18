package espol.poo.proyectopoo.actividades;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

public class JuegoMemoriaActivity extends AppCompatActivity {

    private JuegoMemoria juego;
    private Button[] botones = new Button[16];

    private int primeraSeleccion = -1;

    private String[] palabras = {
            "RECIC", "SOLAR", "AGUA", "AIRE",
            "VIDRIO", "METAL", "PLAS", "PAPEL"
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_juego_memoria);

        juego = new JuegoMemoria(palabras);

        GridLayout grid = findViewById(R.id.gridMemoria);

        for (int i = 0; i < 16; i++) {
            Button btn = new Button(this);
            btn.setText("?");
            int index = i + 1; // 👈 tu modelo usa 1–16

            btn.setOnClickListener(v -> manejarClick(index));

            botones[i] = btn;
            grid.addView(btn);
        }
    }

    private void manejarClick(int numeroCarta) {

        if (juego.estaEmparejada(numeroCarta)) return;

        botones[numeroCarta - 1].setText(juego.verValor(numeroCarta));

        if (primeraSeleccion == -1) {
            primeraSeleccion = numeroCarta;
        } else {

            if (juego.verValor(primeraSeleccion)
                    .equals(juego.verValor(numeroCarta))) {

                juego.marcarEmparejadas(primeraSeleccion, numeroCarta);
                Toast.makeText(this, "¡Par encontrado!", Toast.LENGTH_SHORT).show();

            } else {
                new Handler().postDelayed(() -> {
                    botones[primeraSeleccion - 1].setText("?");
                    botones[numeroCarta - 1].setText("?");
                }, 1000);
            }

            primeraSeleccion = -1;
        }
    }
}