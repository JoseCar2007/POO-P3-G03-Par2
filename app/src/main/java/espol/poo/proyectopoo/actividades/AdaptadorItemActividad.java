package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.ActividadPersonal;

import java.util.ArrayList;
public class AdaptadorItemActividad extends RecyclerView.Adapter<AdaptadorItemActividad.ItemViewHolder>{
    private final int TYPE_ACADEMICA = 2;
    private final int TYPE_PERSONAL = 1;
    private ArrayList<Actividad> listaActividades;
    Context context;
    public AdaptadorItemActividad(Context context, ArrayList<Actividad> listaActividades){
        this.listaActividades = listaActividades;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (listaActividades.get(position) instanceof ActividadAcademica) {
            return TYPE_ACADEMICA;
        }
        return TYPE_PERSONAL;
    }
    @NonNull
    @Override
    public AdaptadorItemActividad.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if(viewType == TYPE_ACADEMICA){
             view = inflater.inflate(R.layout.item_ac_academica, parent, false);
             return new AdaptadorItemActividad.AcademicaViewHolder(view, context);
        }
        else{
            Log.d("DEBUG: ", "Tipo de actividad personal (en desarrollo)");
            view = inflater.inflate(R.layout.item_ac_personal, parent, false);
        }
        return new AdaptadorItemActividad.ItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorItemActividad.ItemViewHolder holder, int position) {
        holder.id.setText(String.valueOf(listaActividades.get(position).getId()));
        holder.nombre.setText(listaActividades.get(position).getNombre());
        holder.fecha.setText(listaActividades.get(position).getFecha());
        holder.prioridad.setText(listaActividades.get(position).getPrioridad());
        holder.avance.setText(listaActividades.get(position).getAvance() + "%");
        holder.ac = listaActividades.get(position);
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView id, nombre, fecha, prioridad, avance;
        Actividad ac;
        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            id = itemView.findViewById(R.id.itId);
            nombre = itemView.findViewById(R.id.itNombre);
            fecha = itemView.findViewById(R.id.itFecha);
            prioridad = itemView.findViewById(R.id.itPrioridad);
            avance = itemView.findViewById(R.id.itAvance);
            itemView.findViewById(R.id.btnDetalles).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DEBUG: ", "Detalles de actividad con id " + ac.getId());
                    Intent intento = new Intent(context, PantallaDetalles.class);
                    intento.putExtra("ObjetoActividad: ", ac);
                    context.startActivity(intento);
                }
            });
            itemView.findViewById(R.id.btnAvance).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DEBUG: ", "Avance de actividad con id " + ac.getId() + ": " + ac.getAvance() + "%");
                }
            });
            itemView.findViewById(R.id.btnEliminar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DEBUG: ", "Eliminacion de actividad con id " + ac.getId());
                }
            });
        }
    }
    public static class AcademicaViewHolder extends ItemViewHolder{
        public AcademicaViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context);
            itemView.findViewById(R.id.btnPomodoro).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, PomodoroActivity.class);
                    intento.putExtra("ObjetoActividad",  ac);
                    context.startActivity(intento);
                }
            });
            itemView.findViewById(R.id.btnDeepWork).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, DeepWorkActivity.class);
                    intento.putExtra("ObjetoActividad", ac);
                    context.startActivity(intento);
                }
            });
        }
    }


}
