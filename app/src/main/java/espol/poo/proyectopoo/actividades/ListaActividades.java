package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.os.Bundle;
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
    ArrayList<Actividad> lactividades = new ArrayList<>();
    int[] lid = {1,2,3};
    String[] lnombre = {"Actividad 1", "Actividad 2", "Actividad 3"};

    String[] lfecha = {"2026-01-01", "2026-01-02", "2026-01-03"};
    String[] lprioridad = {"Alta", "Media", "Baja"};
    int[] lavance = {10, 20, 30};

    RecyclerView rv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        rv1 = findViewById(R.id.rvItemAc);
        setData();
        AdaptadorItemActividad adaptador = new AdaptadorItemActividad(this, lactividades);
        rv1.setAdapter(adaptador);
        rv1.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setData(){
        for(int i=0; i<3; i++){
            Actividad a = new ActividadAcademica(lnombre[i], lfecha[i], 30 + i, "Tarea de id: " + i, lprioridad[i], "POO", lavance[i], tipoActividad.TAREA, "En curso");
            lactividades.add(a);
        }
        lactividades.add(new ActividadPersonal("Ir al doctor", "2026-01-01", 2, "Tarea de ejemplo", "Alta", 0, "Omni hospital", tipoActividad.PERSONAL));
        lactividades.add(new ActividadAcademica("Examen Quimica", "2026-01-01", 2, "Repasar capitulos 3 y 4", "Alta", "Quimica", 0, tipoActividad.PROYECTO, "En curso"));
        for(int i=0; i<4;i++){
            ((ActividadAcademica) lactividades.get(4)).registrarTecnicaEnfoque(new TecnicasEnfoque("Pomodoro", 25, 5, 4));
        }
        ((ActividadAcademica) lactividades.get(4)).registrarTecnicaEnfoque(new TecnicasEnfoque("DeepWork", 90, 0, 1));
    }


}