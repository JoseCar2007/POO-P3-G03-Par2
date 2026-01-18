package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.ActividadPersonal;
import espol.poo.proyectopoo.modelo.tipoActividad;
import espol.poo.proyectopoo.modelo.TecnicasEnfoque;
import java.util.ArrayList;
import java.util.Collections;

public class ListaActividades extends AppCompatActivity {

    AdaptadorItemActividad adaptador;
    Spinner filtroOrden;
    String[] filtros = {"Avance", "Nombre", "Fecha"};
    RecyclerView rv1;
    /**
     * Metodo de ciclo de vida llamado al crear la actividad.
     * Inicializa las vistas, configura el RecyclerView y gestiona el Spinner de ordenamiento.
     * * @param savedInstanceState Si la actividad se está recreando a partir de un estado anterior,
     * este es el estado.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);

        // Configuración del RecyclerView y su adaptador
        rv1 = findViewById(R.id.rvItemAc);
        filtroOrden = findViewById(R.id.orden);
        adaptador = new AdaptadorItemActividad(this, Actividad.getActividades());
        rv1.setAdapter(adaptador);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        // Configuración del botón para ir a la pantalla de crear actividad
        ((Button) findViewById(R.id.btnAgrearAc)).setOnClickListener(v -> {
            Intent inte = new Intent(this, CrearActividad.class);
            startActivity(inte);
        });

        // Configuración del Spinner para los filtros
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filtros);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtroOrden.setAdapter(adapter);

        // Listener para detectar cambios en la selección del Spinner
        filtroOrden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = parent.getItemAtPosition(position).toString();
                Actividad.setFiltroOrdenamiento(seleccion);
                Collections.sort(Actividad.getActividades());
                adaptador.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.btnRegresarMenu).setOnClickListener(v->finish());

    }
    /**
     * Metodo de ciclo de vida llamado cuando la actividad vuelve a primer plano.
     * Se utiliza para actualizar los datos de la lista por si hubo cambios en otras pantallas.
     */
    @Override
    public void onResume(){
        super.onResume();
        Log.d("DEBUG","onResume ejecutado");
        Log.d("DEBUG","Avance de actividad uno: " + ((ActividadAcademica) Actividad.getActividades().get(0)).getAvance() + "%");
        adaptador.notifyDataSetChanged();
    }

}