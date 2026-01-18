package espol.poo.proyectopoo.actividades;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import espol.poo.proyectopoo.modelo.RegistroSostenibilidad;
import espol.poo.proyectopoo.modelo.AccionSostenible;
import espol.poo.proyectopoo.actividades.SelectorAdapter;
import espol.poo.proyectopoo.R;
public class RegistrarSostenibilidadActivity extends AppCompatActivity {
    private RegistroSostenibilidad modelo;
    private SelectorAdapter adapter; // <--- Variable nueva

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sostenibilidad);

        modelo = new RegistroSostenibilidad();

        TextView tvFecha = findViewById(R.id.tvFechaRegistro);
        Button btnGuardar = findViewById(R.id.btnGuardarSost);
        Button btnCancelar = findViewById(R.id.btnCancelarSost);

        RecyclerView recyclerView = findViewById(R.id.recyclerSeleccion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Crear Adaptador con la lista COMPLETA de acciones (dinámico)
        // Necesitamos un getter en el modelo para obtener el String[] completo,
        // OJO: Si no lo tienes, puedes crearlo temporalmente así:
        String[] acciones = {
                modelo.getNombreAccionFija(0),
                modelo.getNombreAccionFija(1),
                modelo.getNombreAccionFija(2),
                modelo.getNombreAccionFija(3)
        };

        adapter = new SelectorAdapter(acciones);
        recyclerView.setAdapter(adapter);

        tvFecha.setText("(" + modelo.getFecha() + ")");

        // 3. Guardar
        btnGuardar.setOnClickListener(v -> {
            // ¡MAGIA! El adaptador nos da la lista lista de índices
            int[] seleccionados = adapter.obtenerIndicesSeleccionados();

            if (seleccionados.length == 0) {
                Toast.makeText(this, "Selecciona al menos una acción", Toast.LENGTH_SHORT).show();
            } else {
                modelo.registrarAcciones(seleccionados);
                Toast.makeText(this, modelo.getMensajeDiario(), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}


