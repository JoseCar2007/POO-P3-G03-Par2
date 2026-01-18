package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.JuegoMemoria;

public class JuegoMemoriaInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_juego_memoria_inicio);

        findViewById(R.id.btnIniciarJuego).setOnClickListener(v -> {
            startActivity(new Intent(this, JuegoMemoriaActivity.class));
        });
    }
}