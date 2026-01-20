package espol.poo.proyectopoo.actividades;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import espol.poo.proyectopoo.modelo.RegistroSostenibilidad;
import espol.poo.proyectopoo.R;

public class ActividadSostenibilidad extends AppCompatActivity {
    private RegistroSostenibilidad modelo;
    private SostenibilidadAdapter adapter;
    private TextView tvFechas, tvAnalisisCompleto;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sostenibilidad);
        RegistroSostenibilidad.cargarDatos(this);

        modelo = new RegistroSostenibilidad();

        // Conectar Vistas
        tvFechas = findViewById(R.id.tvRangoFechas);
        tvAnalisisCompleto = findViewById(R.id.tvAnalisisCompleto); // Solo uno ahora
        recyclerView = findViewById(R.id.recyclerSostenibilidad);
        Button btnRegistrar = findViewById(R.id.btnIrRegistro);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Botón para ir a registrar
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadSostenibilidad.this, RegistrarSostenibilidadActivity.class);
            startActivity(intent);
        });

        actualizarPantalla();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarPantalla();
    }

    private void actualizarPantalla() {
        // 1. Poner fechas
        tvFechas.setText(modelo.getRangoFechas());

        // 2. USAR TU MÉTODO ORIGINAL
        // Esto pondrá el texto: "Días con al menos... (X%) \n Tip: ..."
        tvAnalisisCompleto.setText(modelo.generarResumenSemanal());

        // 3. Actualizar la lista de arriba
        adapter = new SostenibilidadAdapter(modelo);
        recyclerView.setAdapter(adapter);
    }
}


