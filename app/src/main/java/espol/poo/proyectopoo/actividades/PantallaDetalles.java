package espol.poo.proyectopoo.actividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.ActividadPersonal;

public class PantallaDetalles extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles);
        LayoutInflater i = LayoutInflater.from(this);
        ViewGroup parent = findViewById(R.id.linearPDetalles);

        //Obtener Extras y verficar si es una actividad academica o personal
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Actividad ac = (Actividad) extras.getSerializable("ObjetoActividad");
            ((TextView) findViewById(R.id.tituloDetalles)).setText("DETALLES DE LA ACTIVIDAD (ID"+ac.getId()+")");
            if(ac instanceof ActividadAcademica){
                View tarjetaDetalles = i.inflate(R.layout.item_detalles_texto_academica, parent, true);
                setDetallesAcademica(tarjetaDetalles, (ActividadAcademica) ac);
                //Verificar si la actividad tiene registros de tecnicas de estudio para colocar el historial
                if(!((ActividadAcademica) ac).getTecnicas().isEmpty()){
                    View tarjetaHistorialTiempo = i.inflate(R.layout.item_historial_gestion_tiempo, parent, true);
                    setDetallesTecnicas(tarjetaHistorialTiempo, (ActividadAcademica) ac);
                }
            }
            else if(ac instanceof ActividadPersonal){
                View tarjetaDetalles = i.inflate(R.layout.item_detalles_texto_personal, parent, true);
                setDetallesPersonal(tarjetaDetalles, (ActividadPersonal) ac);
            }
        }

    }
    public void volverListado(View v){
        finish();
    }

    //Colocar todos los Views de una actividad academica
    private void setDetallesAcademica(View tarjetaDetalles, ActividadAcademica ac){
        ((TextView) tarjetaDetalles.findViewById(R.id.infoNombre)).setText(ac.getNombre());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoTipo)).setText(ac.getTipo().toLowerCase());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoAsignatura)).setText(((ActividadAcademica) ac).getAsignatura());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoPrioridad)).setText(ac.getPrioridad());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoEstado)).setText(((ActividadAcademica) ac).getEstado());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoFecha)).setText(ac.getFecha());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoTiempo)).setText(ac.getTiempoEstimado() + " horas");
        ((TextView) tarjetaDetalles.findViewById(R.id.infoAvance)).setText(ac.getAvance() + "%");
        ((TextView) tarjetaDetalles.findViewById(R.id.infoDesc)).setText(ac.getDescripcion());
    }
    //Colocar todos los Views de una actividad personal
    private void setDetallesPersonal(View tarjetaDetalles, ActividadPersonal ac){
        ((TextView) tarjetaDetalles.findViewById(R.id.infoNombre)).setText(ac.getNombre());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoTipo)).setText(ac.getTipo().toLowerCase());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoPrioridad)).setText(ac.getPrioridad());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoFecha)).setText(ac.getFecha());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoTiempo)).setText(ac.getTiempoEstimado() + " horas");
        ((TextView) tarjetaDetalles.findViewById(R.id.infoAvance)).setText(ac.getAvance() + "%");
        ((TextView) tarjetaDetalles.findViewById(R.id.infoLugar)).setText(ac.getLugar());
        ((TextView) tarjetaDetalles.findViewById(R.id.infoDesc)).setText(ac.getDescripcion());
    }
    //Colocar historial de gestion de tiempo
    private void setDetallesTecnicas(View tarjetaHistorialTiempo, ActividadAcademica ac){
        RecyclerView rv = tarjetaHistorialTiempo.findViewById(R.id.rvHGT);
        AdaptadorRegistroTecnicaEnfoque adaptador = new AdaptadorRegistroTecnicaEnfoque(this, ac.getTecnicas());
        rv.setAdapter(adaptador);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}