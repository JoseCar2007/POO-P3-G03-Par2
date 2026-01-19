package espol.poo.proyectopoo.actividades;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import espol.poo.proyectopoo.modelo.RegistroSostenibilidad;
import espol.poo.proyectopoo.R;
public class RegistrarSostenibilidadActivity extends AppCompatActivity {
    private RegistroSostenibilidad modelo;
    private SelectorAdapter adapter;

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


        String[] acciones = modelo.getTodasLasAcciones();

        adapter = new SelectorAdapter(acciones);


        tvFecha.setText("(" + modelo.getFecha() + ")");
        // Preguntamos al modelo qué casillas ya tienen visto hoy
        ArrayList<Integer> marcadosHoy = modelo.getIndicesYaMarcadosHoy();

        // Se las pasamos al adaptador para que las pinte de azul
        adapter.setEstadosIniciales(marcadosHoy);
        recyclerView.setAdapter(adapter);
        // Guardar
        btnGuardar.setOnClickListener(v -> {
            int[] seleccionados = adapter.obtenerIndicesSeleccionados();
            modelo.registrarAcciones(seleccionados);
            Toast.makeText(this, modelo.getMensajeDiario(), Toast.LENGTH_LONG).show();
            finish();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}


