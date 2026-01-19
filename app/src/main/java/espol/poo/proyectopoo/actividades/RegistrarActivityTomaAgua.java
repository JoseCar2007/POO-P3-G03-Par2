package espol.poo.proyectopoo.actividades;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;
import espol.poo.proyectopoo.modelo.RegistroAgua;
import espol.poo.proyectopoo.R;

public class RegistrarActivityTomaAgua extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_toma_agua);

        EditText inputCantidad = findViewById(R.id.inputCantidadAgua);
        EditText inputHora = findViewById(R.id.inputHora);
        Button btnGuardar = findViewById(R.id.btnGuardarIngesta);
        Button btnCancelar = findViewById(R.id.btnCancelarIngesta);

        RegistroAgua modelo = new RegistroAgua();

        // --- LÓGICA DEL RELOJ
        inputHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener hora actual para que el reloj empiece ahí
                Calendar calendario = Calendar.getInstance();
                int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
                int minutoActual = calendario.get(Calendar.MINUTE);

                TimePickerDialog reloj = new TimePickerDialog(RegistrarActivityTomaAgua.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Formatear la hora (ej: 09:05)
                                String horaFormateada = String.format(Locale.getDefault(),
                                        "%02d:%02d", hourOfDay, minute);
                                inputHora.setText(horaFormateada);
                            }
                        }, horaActual, minutoActual, false);
                reloj.show();
            }
        });

        // --- BOTÓN GUARDAR ---
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidadStr = inputCantidad.getText().toString();
                String horaStr = inputHora.getText().toString();

                if (cantidadStr.isEmpty() || horaStr.isEmpty()) {
                    Toast.makeText(RegistrarActivityTomaAgua.this, "Completa todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    //Convertir cantidad a numero
                    float cantidad = Float.parseFloat(cantidadStr);
                    // Intentamos recuperar la fecha que nos enviaron
                    String fechaRecibida = getIntent().getStringExtra("FECHA_PARA_GUARDAR");

                    // (Seguridad) Si por alguna razón llegó vacía, usamos la de hoy como plan B
                    if (fechaRecibida == null) {
                        fechaRecibida = modelo.getFechaHoy();
                    }
                    // 2. Guardamos usando ESA fecha recibida.
                    modelo.registrarIngesta(fechaRecibida, cantidad, RegistrarActivityTomaAgua.this);

                    finish();
                }
            }
        });

        // --- BOTÓN CANCELAR ---
        btnCancelar.setOnClickListener(v -> finish());
    }
}
