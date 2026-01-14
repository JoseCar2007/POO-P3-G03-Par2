package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Actividad ac = listaActividades.get(position);
        holder.id.setText(String.valueOf(ac.getId()));
        holder.nombre.setText(ac.getNombre());
        holder.fecha.setText(ac.getFecha());
        holder.prioridad.setText(ac.getPrioridad());
        holder.avance.setText(ac.getAvance() + "%");
        holder.ac = listaActividades.get(position);
        holder.btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(context, PantallaDetalles.class);
                intento.putExtra("ObjetoActividad: ", ac);
                context.startActivity(intento);
            }
        });
        holder.btnAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(context, RegistrarAvance.class);
                intento.putExtra("ObjetoActividad", ac);
                context.startActivity(intento);
                notifyItemChanged(position);
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG: ", "Eliminacion de actividad con id " + ac.getId());
            }
        });
        if(holder instanceof AcademicaViewHolder){
            AcademicaViewHolder acHolder = (AcademicaViewHolder) holder;
            acHolder.btnDeepWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, DeepWorkActivity.class);
                    intento.putExtra("ObjetoActividad", ac);
                    context.startActivity(intento);
                }
            });
            acHolder.btnPomodoro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, PomodoroActivity.class);
                    intento.putExtra("ObjetoActividad",  ac);
                    context.startActivity(intento);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView id, nombre, fecha, prioridad, avance;
        Actividad ac;
        Button btnAvance, btnEliminar, btnDetalles;
        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            id = itemView.findViewById(R.id.itId);
            nombre = itemView.findViewById(R.id.itNombre);
            fecha = itemView.findViewById(R.id.itFecha);
            prioridad = itemView.findViewById(R.id.itPrioridad);
            avance = itemView.findViewById(R.id.itAvance);
            btnAvance = itemView.findViewById(R.id.btnAvance);
            btnDetalles = itemView.findViewById(R.id.btnDetalles);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);

        }
    }
    public static class AcademicaViewHolder extends ItemViewHolder{
        Button btnPomodoro, btnDeepWork;
        public AcademicaViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context);
            btnPomodoro = itemView.findViewById(R.id.btnPomodoro);
            btnDeepWork = itemView.findViewById(R.id.btnDeepWork);
        }
    }


}
