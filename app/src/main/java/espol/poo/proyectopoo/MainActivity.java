package espol.poo.proyectopoo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

// Importaciones de tus actividades (asegúrate de que existan)
import espol.poo.proyectopoo.actividades.JuegoMemoriaInicioActivity;
import espol.poo.proyectopoo.actividades.ListaActividades;
import espol.poo.proyectopoo.modelo.Actividad;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los botones
    Button btnActividades, btnHidratacion, btnSostenibilidad, btnJuegoMemoria, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de datos
        Actividad.setData();

        // Enlace de los componentes visuales con las variables
        btnActividades = findViewById(R.id.btnActividades);
        btnHidratacion = findViewById(R.id.btnHidratacion);
        btnSostenibilidad = findViewById(R.id.btnSostenibilidad);
        btnJuegoMemoria = findViewById(R.id.btnJuegoMemoria);
        btnSalir = findViewById(R.id.btnSalir);

        btnActividades.setOnClickListener(v -> {

            Intent i = new Intent(this, ListaActividades.class);
            startActivity(i);
        });

        btnHidratacion.setOnClickListener(v -> {
            // TODO - Descomentar cuando esté lista HidratacionActivity
            // Intent i = new Intent(this, HidratacionActivity.class);
            // startActivity(i);
        });

        btnSostenibilidad.setOnClickListener(v -> {
            // TODO - Descomentar cuando esté lista SostenibilidadActivity
            // Intent i = new Intent(this, SostenibilidadActivity.class);
            // startActivity(i);
        });


        btnJuegoMemoria.setOnClickListener(v -> {
            Intent i = new Intent(this, JuegoMemoriaInicioActivity.class);
            startActivity(i);
        });

        // --- BOTÓN SALIR ---
        btnSalir.setOnClickListener(v -> finish());
    }
}