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

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnActividades, btnTecnicas, btnHidratacion, btnJuegoMemoria, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //

        btnActividades = findViewById(R.id.btnActividades);
        btnTecnicas = findViewById(R.id.btnTecnicas);
        btnHidratacion = findViewById(R.id.btnHidratacion);
        btnJuegoMemoria = findViewById(R.id.btnJuegoMemoria);
        btnSalir = findViewById(R.id.btnSalir);

        btnActividades.setOnClickListener(v -> {
            // startActivity(new Intent(this, ActividadesActivity.class));
        });

        btnTecnicas.setOnClickListener(v -> {
            // startActivity(new Intent(this, TecnicasActivity.class));
        });

        btnHidratacion.setOnClickListener(v -> {
            // startActivity(new Intent(this, HidratacionActivity.class));
        });

        btnJuegoMemoria.setOnClickListener(v -> {
            Intent i = new Intent(this, JuegoMemoriaActivity.class);
            startActivity(i);
        });

        btnSalir.setOnClickListener(v -> finish());
    }
}