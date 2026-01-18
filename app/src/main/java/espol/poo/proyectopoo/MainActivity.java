package espol.poo.proyectopoo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.actividades.JuegoMemoriaInicioActivity;
import espol.poo.proyectopoo.actividades.JuegoMemoriaActivity;
import espol.poo.proyectopoo.actividades.ListaActividades;
import espol.poo.proyectopoo.modelo.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnActividades, btnHidratacion, btnJuegoMemoria, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //
        Actividad.setData();
        btnActividades = findViewById(R.id.btnActividades);
        btnHidratacion = findViewById(R.id.btnHidratacion);
        btnJuegoMemoria = findViewById(R.id.btnJuegoMemoria);
        btnSalir = findViewById(R.id.btnSalir);

        btnActividades.setOnClickListener(v -> {
            Intent i = new Intent(this, ListaActividades.class);
            startActivity(i);
        });


        btnHidratacion.setOnClickListener(v -> {
            // startActivity(new Intent(this, HidratacionActivity.class));
        });

        btnJuegoMemoria.setOnClickListener(v -> {
            Intent i = new Intent(this, JuegoMemoriaInicioActivity.class);
            startActivity(i);
        });

        btnSalir.setOnClickListener(v -> finish());
    }
}