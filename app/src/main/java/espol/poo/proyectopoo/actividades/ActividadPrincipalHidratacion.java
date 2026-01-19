package espol.poo.proyectopoo.actividades;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.DatePickerDialog;
import android.widget.EditText;
import java.util.Calendar;
import androidx.recyclerview.widget.RecyclerView;
import espol.poo.proyectopoo.R;

import espol.poo.proyectopoo.modelo.RegistroAgua;
public class ActividadPrincipalHidratacion extends AppCompatActivity {
    private TextView txtMeta, txtTotal, txtPorcentaje;
    private ProgressBar progressBarCircular;
    private RecyclerView recyclerView;
    private IngestaAdapter adapter;
    private RegistroAgua modeloAgua;
    private TextView tvFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_hidratacion);

        modeloAgua = new RegistroAgua();
        modeloAgua.cargarDesdeArchivo(this);
        tvFecha = findViewById(R.id.txtFecha);
        tvFecha.setText(modeloAgua.getFechaHoy());
        tvFecha.setOnClickListener(v -> {
            mostrarCalendario(tvFecha); //
        });
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
            intent.putExtra("FECHA_PARA_GUARDAR", tvFecha.getText().toString());
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
        String fechaSeleccionada = tvFecha.getText().toString();
        // Actualizar Textos
        // El modelo necesita saber qué día filtrar
        float total = modeloAgua.getIngestaAcumulada(fechaSeleccionada);
        int porcentaje = modeloAgua.getPorcentajeProgreso(fechaSeleccionada);
        txtMeta.setText(modeloAgua.getMetaDiaria() + " ml");
        txtTotal.setText(total + " ml");
        txtPorcentaje.setText(porcentaje + "%");

        // Actualizar la Ruedita
        progressBarCircular.setMax(modeloAgua.getMetaDiaria());
        progressBarCircular.setProgress((int) modeloAgua.getIngestaAcumulada(tvFecha.getText().toString()));

        // Actualizar la Lista (RecyclerView)
        adapter = new IngestaAdapter(modeloAgua.getHistorialTomas(fechaSeleccionada));
        recyclerView.setAdapter(adapter);
    }
    private void mostrarCalendario(TextView etFecha) {
        // Obtener fecha actual para mostrarla seleccionada al abrir
        final Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        // Crear el diálogo
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Formato debe ser idéntico al de getFechaHoy: dd/MM/yyyy
                    String fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, (month + 1), year);
                    etFecha.setText(fechaSeleccionada);
                    // 7. IMPORTANTE: ACTUALIZAR LA PANTALLA AL CAMBIAR LA FECHA
                    actualizarPantalla(); // <--- CAMBIO: Para ver los datos de ese día
                },
                anio, mes, dia
        );

        datePicker.show();
    }
}
