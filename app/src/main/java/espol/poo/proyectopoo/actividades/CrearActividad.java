package espol.poo.proyectopoo.actividades;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.ActividadPersonal;
import espol.poo.proyectopoo.modelo.tipoActividad;

public class CrearActividad extends AppCompatActivity {
    EditText etNombre, etFecha, etTiempo, etDescripcion, etAsignatura, etLugar;
    Spinner spinnerCategoria, spinnerPrioridad, spinnerTipo;
    List<String> categorias = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_actividad);
        Log.d("DEBUG", "Iniciar Crear actividad");
        LayoutInflater inflador = LayoutInflater.from(this);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        String[] categorias = {"Academico", "Personal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        etTiempo = findViewById(R.id.etTiempo);
        spinnerPrioridad = findViewById(R.id.spinnerPrioridad);
        String[] prioridades = {"Alta", "Media", "Baja"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prioridades);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridad.setAdapter(adapter2);
        configurarDatePicker();
        LinearLayout layoutDinamico = (LinearLayout) findViewById(R.id.dynamicContainer);
        Log.d("DEBUG", "Creando actividad");
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                layoutDinamico.removeAllViews();
                String seleccion = parent.getItemAtPosition(position).toString();
                if(seleccion.equals("Academico")){
                    inflador.inflate(R.layout.campos_actividad_academica, layoutDinamico, true);
                    spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
                    String tipos[] = {"PROYECTO", "TAREA", "EXAMEN"};
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(CrearActividad.this, android.R.layout.simple_spinner_item, tipos);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTipo.setAdapter(adapter3);
                    etAsignatura = (EditText) findViewById(R.id.etAsignatura);
                    Log.d("DEBUG", "Academico");
                }
                else if(seleccion.equals("Personal")){
                    inflador.inflate(R.layout.campos_actividad_personal, layoutDinamico, true);
                    etLugar = (EditText) findViewById(R.id.etLugar);
                    Log.d("DEBUG", "Personal");
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String fecha = etFecha.getText().toString();
            String desc = etDescripcion.getText().toString();
            String prioridad = spinnerPrioridad.getSelectedItem().toString();
            int tiempo = Integer.parseInt(etTiempo.getText().toString());


            if(nombre.isEmpty() || fecha.isEmpty() || desc.isEmpty() || prioridad.isEmpty() || tiempo == 0){
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            }
            else{
                if(etLugar != null){
                    String lugar = etLugar.getText().toString();
                    Actividad.añadirActividad(new ActividadPersonal(nombre, fecha, tiempo, desc, prioridad, 0, lugar, tipoActividad.PERSONAL));
                    Toast.makeText(this, "Personal: Actividad añadida presione cancelar para salir", Toast.LENGTH_SHORT).show();
                }
                else if(etAsignatura != null && spinnerTipo != null) {
                    String asignatura = etAsignatura.getText().toString();
                    String tipo = spinnerTipo.getSelectedItem().toString();
                    Actividad.añadirActividad(new ActividadAcademica(nombre, fecha, tiempo, desc, prioridad, asignatura, 0, tipoActividad.valueOf(tipo), "En curso" ));
                    Toast.makeText(this, tipo + ": Actividad añadida, presione cancelar para salir", Toast.LENGTH_SHORT).show();
                }
            }

        });
        findViewById(R.id.btnCancelar).setOnClickListener(v -> finish());
    }
    private void configurarDatePicker() {
        etFecha = findViewById(R.id.etFecha);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha actual para que el calendario abra en el día de hoy
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Crear el diálogo
                DatePickerDialog datePickerDialog = new DatePickerDialog(CrearActividad.this,
                        (view, yearSelected, monthOfYear, dayOfMonth) -> {
                            // Formatear la fecha (Sumamos +1 al mes porque Enero es 0)
                            String fechaFormateada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + yearSelected;
                            etFecha.setText(fechaFormateada);
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
    }
}