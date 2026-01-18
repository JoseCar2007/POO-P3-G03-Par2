package espol.poo.proyectopoo.actividades;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import espol.poo.proyectopoo.R;

import espol.poo.proyectopoo.modelo.RegistroAgua;
public class ActividadPrincipalHidratacion extends AppCompatActivity {
    private TextView txtMeta, txtTotal, txtPorcentaje;
    private ProgressBar progressBarCircular;
    private RecyclerView recyclerView;
    private IngestaAdapter adapter;
    private RegistroAgua modeloAgua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_hidratacion);

        modeloAgua = new RegistroAgua();

        txtMeta = findViewById(R.id.txtMeta);
        txtTotal = findViewById(R.id.txtTotal);
        txtPorcentaje = findViewById(R.id.txtPorcentaje);
        progressBarCircular = findViewById(R.id.progressBarCircular);
        recyclerView = findViewById(R.id.recyclerViewHistorial);

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Configurar los Botones
        Button btnMeta = findViewById(R.id.btnMeta);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        // Botón "Establecer Meta" -> Abre MetaActivity
        btnMeta.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadPrincipalHidratacion.this, MetaActivity.class);
            startActivity(intent);
        });

        // Botón "Registrar" -> Abre RegistrarActivityTomaAgua
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadPrincipalHidratacion.this, RegistrarActivityTomaAgua.class);
            startActivity(intent);
        });

        actualizarPantalla();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarPantalla();
    }

    // Método para refrescar textos, barra y lista
    private void actualizarPantalla() {
        // Actualizar Textos
        txtMeta.setText(modeloAgua.getMetaDiaria() + " ml");
        txtTotal.setText(modeloAgua.getIngestaAcumulada() + " ml");
        txtPorcentaje.setText(modeloAgua.getPorcentajeProgreso() + "%");

        // Actualizar la Ruedita
        progressBarCircular.setMax(modeloAgua.getMetaDiaria());
        progressBarCircular.setProgress((int) modeloAgua.getIngestaAcumulada());

        // Actualizar la Lista (RecyclerView)
        adapter = new IngestaAdapter(modeloAgua.getHistorialTomas());
        recyclerView.setAdapter(adapter);
    }
}
