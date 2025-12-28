package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;

import java.util.ArrayList;
public class AdaptadorItemActividad extends RecyclerView.Adapter<AdaptadorItemActividad.ItemViewHolder>{
    private ArrayList<Actividad> listaActividades;
    Context context;
    public AdaptadorItemActividad(Context context, ArrayList<Actividad> listaActividades){
        this.listaActividades = listaActividades;
        this.context = context;
    }
    @NonNull
    @Override
    public AdaptadorItemActividad.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_ac_academica, parent, false);
        return new AdaptadorItemActividad.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorItemActividad.ItemViewHolder holder, int position) {
        holder.id.setText(String.valueOf(listaActividades.get(position).getId()));
        holder.nombre.setText(listaActividades.get(position).getNombre());
        holder.fecha.setText(listaActividades.get(position).getFecha());
        holder.prioridad.setText(listaActividades.get(position).getPrioridad());
        holder.avance.setText(listaActividades.get(position).getAvance() + "%");
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView id, nombre, fecha, prioridad, avance;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.itId);
            nombre = itemView.findViewById(R.id.itNombre);
            fecha = itemView.findViewById(R.id.itFecha);
            prioridad = itemView.findViewById(R.id.itPrioridad);
            avance = itemView.findViewById(R.id.itAvance);
        }
    }
}
