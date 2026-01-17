package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
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
public class ListaActividades extends AppCompatActivity {
    AdaptadorItemActividad adaptador;

    RecyclerView rv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        rv1 = findViewById(R.id.rvItemAc);
        adaptador = new AdaptadorItemActividad(this, Actividad.getActividades());
        rv1.setAdapter(adaptador);
        rv1.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("DEBUG","onResume ejecutado");
        Log.d("DEBUG","Avance de actividad uno: " + ((ActividadAcademica) Actividad.getActividades().get(0)).getAvance() + "%");
        adaptador.notifyDataSetChanged();
    }

}